package diario.professores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.ConnectionFactory;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;
import diario.professores.services.ExcecaoNaoAutorizado;
import diario.professores.services.ExcecaoParametrosIncorretos;
import diario.professores.services.Validacao;

@WebServlet(name = "AtualizarProfessor", urlPatterns = "/diario/professores/atualizar")
/**
 * <h1>Servlet de Atualização de Professores</h1>
 * Servlet dedicado a atualizar registros da tabela 'professores'
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class AtualizarProfessor extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws IOException {

		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getDiario()) {

			int id = Integer.parseInt(requisicao.getParameter("id"));
			DiarioAutenticador autenticador = new DiarioAutenticador(requisicao, resposta);
			if (autenticador.cargoLogado() != DiarioCargos.ADMIN
					&& (Integer) autenticador.idLogado() != id) {
				throw new ExcecaoNaoAutorizado("Você não tem permissão para realizar esta operação");
			}

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			String senha = requisicao.getParameter("senha");
			boolean haSenha = senha != null && !senha.equals("");

			Validacao.validarParametros(requisicao.getParameterMap(), haSenha);
			Validacao.validarDepartamento(requisicao.getParameter("id-depto"), conexao);

			String statement = "UPDATE `professores` SET "
					+ "`id-depto` = ?, `nome` = ?, "
					+ "`email` = ?, `titulacao` = ? WHERE `id` = ?";
			PreparedStatement ps = conexao.prepareStatement(statement);

			ps.setInt(1, Integer.parseInt(requisicao.getParameter("id-depto")));
			ps.setString(2, requisicao.getParameter("nome"));
			ps.setString(3, requisicao.getParameter("email"));
			ps.setString(4, requisicao.getParameter("titulacao"));
			ps.setInt(5, id);
			ps.execute();
			ps.close();

			if (haSenha) {
				AtualizarSenha.atualizarSenha(id, senha, conexao);
			}

			conexao.close();

			saida.println("<sucesso>");
			saida.println("  <mensagem>Registro alterado com sucesso</mensagem>");
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
		return "Servlet dedicado a atualizar registros da tabela 'professores'";
	}

}
