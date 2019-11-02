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
import utils.Hasher;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

@WebServlet(name = "InserirProfessor", urlPatterns = "/diario/professores/inserir")
/**
 * <h1> Servlet de Inserção de Professor</h1>
 * Servlet para a requisição de indlusão de registro na tabela 'professores'
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class InsercaoProfessor extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws IOException {

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.addHeader("Content-Type", "application/xml; charset=utf-8");

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

			PreparedStatement ps = conexao.prepareStatement("INSERT INTO `professores` VALUES (?, ?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.parseInt(requisicao.getParameter("id")));
			ps.setInt(2, Integer.parseInt(requisicao.getParameter("id-depto")));
			ps.setString(3, requisicao.getParameter("nome"));
			ps.setString(4, Hasher.hash(requisicao.getParameter("senha")));
			ps.setString(5, requisicao.getParameter("email"));
			ps.setString(6, requisicao.getParameter("titulacao"));

			ps.execute();
			ps.close();
			conexao.close();

			saida.println("<sucesso>");
			saida.println("  <mensagem>Professor cadastrado com sucesso</mensagem>");
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
		return "Servlet dedicado à inserção de registro de professores no BD";
	}

}
