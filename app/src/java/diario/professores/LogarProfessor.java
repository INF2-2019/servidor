package diario.professores;

import utils.Headers;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;
import utils.ConnectionFactory;
import utils.Hasher;
import diario.professores.services.ExcecaoNaoAutorizado;
import diario.professores.services.ExcecaoParametrosIncorretos;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <h1> Servlet de Log-in</h1>
 * Servlet destinado a log-in dos Professores
 *
 * @author Jonata Novais Cirqueira
 */
@WebServlet(name = "LogarProfessor", urlPatterns = "/diario/professores/logar")
public class LogarProfessor extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
		throws IOException {


		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getDiario()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			String siape, senha;
			boolean manter;
			siape = requisicao.getParameter("siape");
			senha = requisicao.getParameter("senha");
			manter = requisicao.getParameter("manter") != null;

			if (siape == null || siape.equals("")) {
				throw new ExcecaoParametrosIncorretos("'siape' é um campo obrigatório");
			}
			if (senha == null || senha.equals("")) {
				throw new ExcecaoParametrosIncorretos("'senha' é um campo obrigatório");
			}

			// Valida credenciais
			PreparedStatement ps = conexao.prepareStatement("SELECT `senha` FROM `professores` WHERE `id` = ?");
			ps.setInt(1, Integer.parseInt(siape));
			ResultSet professor = ps.executeQuery();
			if (!professor.first() || !Hasher.validar(senha, professor.getString("senha"))) {
				throw new ExcecaoNaoAutorizado("Credenciais inválidas");
			}

			DiarioAutenticador autenticador = new DiarioAutenticador(requisicao, resposta);
			autenticador.logar(Integer.parseInt(siape), DiarioCargos.PROFESSOR, manter);

			saida.println("<sucesso>");
			saida.println("  <mensagem>Logado com sucesso</mensagem>");
			saida.println("</sucesso>");

		} catch (ExcecaoParametrosIncorretos e) {
			resposta.setStatus(422);
			saida.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		} catch (ExcecaoNaoAutorizado e) {
			resposta.setStatus(403);
			saida.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		} catch (Exception e) {
			resposta.setStatus(500);
			saida.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		}

	}

	@Override
	public String getServletInfo() {
		return "Servlet destinado a log-in dos Professores";
	}

}
