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
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import utils.ConnectionFactory;
import utils.Headers;

/**
 *
 * @author User
 */
@WebServlet(name = "ConsultarCampi", urlPatterns = {"/diario/campi/consultar"})
public class ConsultarPorId extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String xml = "";
		Headers.XMLHeaders(response);
		String id = request.getParameter("id");

		Connection conexao = ConnectionFactory.getDiario();
		try {

			PreparedStatement ps = conexao.prepareStatement("SELECT * FROM `campi` WHERE `id` = ?");
			int idParsed = Integer.parseUnsignedInt(id);
			ps.setInt(1, idParsed);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
			xml += viewConsulta.XMLCampi(rs.getInt("id"), rs.getString("nome"),  rs.getString("cidade"),  rs.getString("uf"));
			}
			out.println(xml);
			conexao.close();
		} catch(SQLException ex) {
			out.println("<erro>Falha ao consultar campis do banco de dados</erro>");
		}
		
    }

}
