package diario.campi.servlets;

import diario.campi.view.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import diario.campi.repository.*;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import utils.ConnectionFactory;
import utils.Headers;

/**
 *
 * @author User
 */
@WebServlet(name = "ListarCampi", urlPatterns = {"/diario/campi/listar"})
public class ListarCampi extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getDiario();
		CampiRepository rep = new CampiRepository(conexao);
		PrintWriter out = response.getWriter();
		String xml = "";
		Headers.XMLHeaders(response);

		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM `campi`");

			while(rs.next()) {
				xml += viewConsulta.XMLCampi(rs.getInt("id"), rs.getString("nome"), rs.getString("cidade"), rs.getString("uf"));
			}
			xml = viewConsulta.XMLConsulta(xml);
			out.println(xml);

			stmt.close();
			conexao.close();
		} catch(SQLException ex) {
			out.println("<erro>Falha ao listar campis do banco de dados</erro>");
		}
               
    }

}
