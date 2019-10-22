package diario.professores;

import utils.ConnectionFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Hasher;

@WebServlet(name = "InserirProfessores", urlPatterns = "/diario/professores/inserir")
/**
 * <h1> Servlet de Inserção de Professor</h1>
 * Servlet para a requisição de indlusão de registro na tabela 'professores'
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class InsercaoProfessor extends HttpServlet {

	private static final String[] params = {"id", "id-depto", "nome", "senha", "email", "titulacao"};

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws IOException {

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.addHeader("Content-Type", "application/xml; charset=utf-8");
		PrintWriter saida = resposta.getWriter();
		saida.println("<root>");
		try (Connection conexao = ConnectionFactory.getDiario()) {

			// Validação dos parâmetros
			for (String param : params) {
				if (requisicao.getParameter(param) == null) {
					throw new ExcecaoParametrosIncorretos("Todos os parâmetros devem ser preenchidos na inserção de um registro");
				}
			}
			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			PreparedStatement ps = conexao.prepareStatement("INSERT INTO `professores` VALUES (?, ?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.parseInt(requisicao.getParameter("id")));
			ps.setInt(2, Integer.parseInt(requisicao.getParameter("id-depto")));
			ps.setString(3, requisicao.getParameter("nome"));
			ps.setString(4, /*Hasher.hash(*/ requisicao.getParameter("senha"/*)*/));
			ps.setString(5, requisicao.getParameter("email"));
			ps.setString(6, requisicao.getParameter("titulacao"));

			ps.execute();
			ps.close();
			conexao.close();

			saida.println("<info>");
			saida.println("  <erro>false</erro>");
			saida.println("	 <mensagem>Registro inserido com sucesso</mensagem>");
			saida.println("</info>");

		} catch (Exception e) {
			saida.println("<info>");
			saida.println("  <erro>true</erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</info>");
		} finally {
			saida.println("</root>");
		}

	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado à inserção de registro de professores no BD";
	}

}
