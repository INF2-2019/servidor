package diario.professores;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.ConnectionFactory;
import utils.Hasher;
import utils.Headers;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;
import diario.professores.services.ExcecaoNaoAutorizado;
import diario.professores.services.ExcecaoParametrosIncorretos;

@WebServlet(name = "AtualizarSenha", urlPatterns = {"/diario/professores/atualizar-senha"})
public class AtualizarSenha extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getDiario()) {

			DiarioAutenticador aut = new DiarioAutenticador(requisicao, resposta);
			if (aut.cargoLogado() == DiarioCargos.CONVIDADO) {
				throw new ExcecaoNaoAutorizado("Você deve estar logado para alterar sua senha");
			}
			Integer id = (Integer) aut.idLogado();

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			String antigaSenha = requisicao.getParameter("antigaSenha"),
					novaSenha = requisicao.getParameter("novaSenha");

			if (!Hasher.validar(antigaSenha, getSenha(id, conexao))) {
				throw new ExcecaoNaoAutorizado("Senha incorreta");
			}

			atualizarSenha(id, novaSenha, conexao);
			saida.println("<sucesso><mensagem>Senha alterada com sucesso!</mensagem></sucesso>");

		} catch (ExcecaoNaoAutorizado e) {
			resposta.setStatus(403);
			saida.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		} catch (Exception e) {
			resposta.setStatus(500);
			saida.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		}
	}

	public static void atualizarSenha(int id, String novaSenha, Connection con)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		PreparedStatement ps = con.prepareStatement("UPDATE `professores` SET `senha` = ? WHERE `id` = ?");
		ps.setString(1, Hasher.hash(novaSenha));
		ps.setInt(2, id);
		ps.executeUpdate();
	}

	private String getSenha(int id, Connection con) throws SQLException, ExcecaoParametrosIncorretos {
		PreparedStatement ps = con.prepareStatement("SELECT `senha` FROM `professores` WHERE `id` = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (!rs.first()) {
			throw new ExcecaoParametrosIncorretos("Professor logado não existe");
		}
		String senha = rs.getString(1);
		rs.close();
		return senha;
	}

}
