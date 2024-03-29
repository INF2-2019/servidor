package diario.professores;

import diario.professores.services.ExcecaoNaoAutorizado;
import diario.professores.services.ExcecaoParametrosIncorretos;
import utils.ConnectionFactory;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

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

@WebServlet(name = "ConsultarProfessores", urlPatterns = "/diario/professores/consultar")
/**
 * <h1>Servlet de Consulta de Professores</h1>
 * Servlet dedicado a retornar os registros da tabela 'professores'
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class ConsultarProfessor extends HttpServlet {

	private static final String[] PARAMS = {"id", "id-depto", "nome", "senha", "email", "titulacao"};

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
		throws IOException {

		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getDiario()) {

			DiarioAutenticador autenticador = new DiarioAutenticador(requisicao, resposta);
			if (autenticador.cargoLogado() == DiarioCargos.CONVIDADO) {
				throw new ExcecaoNaoAutorizado("Você não tem permissão para realizar esta operação");
			}

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			ResultSet rs;
			if (requisicao.getParameter("id") == null) {
				String sqlQuery = "SELECT * FROM `professores`";
				rs = conexao.createStatement().executeQuery(sqlQuery);
			} else {
				int tempId = Integer.parseInt(requisicao.getParameter("id"));
				if (tempId == -1) {
					tempId = (Integer) autenticador.idLogado();
				}
				String sqlQuery = "SELECT * FROM `professores` WHERE `id` = ?";
				PreparedStatement ps = conexao.prepareStatement(sqlQuery);
				ps.setInt(1, tempId);
				rs = ps.executeQuery();
				if (!rs.first()) {
					throw new ExcecaoParametrosIncorretos("Professor não existe");
				}
				rs.previous();
			}

			saida.println("<professores>");
			while (rs.next()) {
				saida.println("<professor>");
				for (String param : PARAMS) {
					if (!param.equals("senha")) {
						saida.println("<" + param + ">" + rs.getString(param) + "</" + param + ">");
					}
				}
				saida.println("</professor>");
			}
			saida.println("</professores>");

		} catch (ExcecaoNaoAutorizado e) {
			resposta.setStatus(403);
			saida.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		} catch (ExcecaoParametrosIncorretos e) {
			resposta.setStatus(422);
			saida.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		} catch (SQLException e) {
			resposta.setStatus(500);
			saida.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		}
	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado a retornar os registros da tabela 'professores'";
	}

}
