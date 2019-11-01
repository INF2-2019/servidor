package diario.professores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.ConnectionFactory;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

@WebServlet(name = "ConsultarProfessores", urlPatterns = "/diario/professores/consultar")
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

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.addHeader("Content-Type", "application/xml; charset=utf-8");

		DiarioAutenticador autenticador = new DiarioAutenticador(requisicao, resposta);
		if (autenticador.cargoLogado() == DiarioCargos.CONVIDADO) {
			resposta.setStatus(403);
			return;
		}

		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getDiario()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}
			
			ResultSet rs;
			if(requisicao.getParameter("id") == null) {
				String sqlQuery = "SELECT * FROM `professores`";
				rs = conexao.createStatement().executeQuery(sqlQuery);
			} else {
				String sqlQuery = "SELECT * FROM `professores` WHERE `id` = ?";
				PreparedStatement ps = conexao.prepareStatement(sqlQuery);
				ps.setInt(1, Integer.parseInt(requisicao.getParameter("id")));
				rs = ps.executeQuery();
			}

			saida.println("<professores>");
			while (rs.next()) {
				saida.println("<professor>");
				for (String param : params) {
					if (!param.equals("senha")) {
						saida.println("<" + param + ">" + rs.getString(param) + "</" + param + ">");
					}
				}
				saida.println("</professor>");
			}
			saida.println("</professores>");

		} catch (Exception e) {

			saida.println("<erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</erro>");

		}
	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado a retornar os registros da tabela 'professores'";
	}

}
