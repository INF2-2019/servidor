package diario.professores;

import utils.ConnectionFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "consultar", urlPatterns = "/diario/professores/consultar")
/**
 * <h1>Servlet de Consulta de Professores</h1>
 * Servlet dedicado a retornar os registros da tabela 'professores'
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class ConsultarProfessor extends HttpServlet {

	private static final String[] params = {"id", "id-depto", "nome", "senha", "email", "titulacao"};

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws IOException {
		String sqlQuery = "abobora";
		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.setContentType("text/xml;charset=UTF-8");

		PrintWriter saida = resposta.getWriter();
		saida.println("<root>");
		try (Connection conexao = ConnectionFactory.getDiario()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			String condicao = requisicao.getParameter("condicao");
			sqlQuery = "SELECT * FROM `professores`" + (condicao == null ? "" : condicao);
			ResultSet rs = conexao.createStatement().executeQuery(sqlQuery);

			saida.println("<info>");
			saida.println("  <erro>false</erro>");
			saida.println("  <mensagem>Registros obtidos com sucesso</mensagem>");
			saida.println("</info>");

			saida.println("<professores>");
			while (rs.next()) {
				saida.println("  <professor>");
				for (String param : params) {
					saida.println("    <" + param + ">" + rs.getString(param) + "</" + param + ">");
				}
				saida.println("  </professor>");
			}
			saida.println("</professores>");

		} catch (Exception e) {

			saida.println("<info>");
			saida.println("  <erro>true</erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("  <mensagem>" + sqlQuery + "</mensagem>");
			saida.println("</info>");

		} finally {
			saida.println("</root>");
		}
	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado a retornar os registros da tabela 'professores'";
	}

}
