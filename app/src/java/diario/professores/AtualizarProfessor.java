package diario.professores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.ConnectionFactory;
import utils.Hasher;
import utils.Headers;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

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

		Headers.XMLHeaders(requisicao, resposta);

		DiarioAutenticador autenticador = new DiarioAutenticador(requisicao, resposta);
		if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
			resposta.setStatus(403);
			return;
		}

		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getDiario()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			Validacao.validarParametros(requisicao.getParameterMap());
			Validacao.validarDepartamento(requisicao.getParameter("id-depto"), conexao);

			String statement = "UPDATE `professores` SET "
					+ "`id-depto` = ?, `nome` = ?, `senha` = ?, "
					+ "`email` = ?, `titulacao` = ? WHERE `id` = ?";
			PreparedStatement ps = conexao.prepareStatement(statement);

			ps.setInt(1, Integer.parseInt(requisicao.getParameter("id-depto")));
			ps.setString(2, requisicao.getParameter("nome"));
			ps.setString(3, Hasher.hash(requisicao.getParameter("senha")));
			ps.setString(4, requisicao.getParameter("email"));
			ps.setString(5, requisicao.getParameter("titulacao"));
			ps.setInt(6, Integer.parseInt(requisicao.getParameter("id")));
			ps.execute();
			conexao.close();

			saida.println("<sucesso>");
			saida.println("  <mensagem>Registro alterado com sucesso</mensagem>");
			saida.println("</sucesso>");

		} catch (ExcecaoParametrosIncorretos e) {
			resposta.setStatus(400);
			saida.println("<erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</erro>");
		} catch (Exception e) {
			resposta.setStatus(500);
			saida.println("<erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</erro>");
		}

	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado a atualizar registros da tabela 'professores'";
	}

}
