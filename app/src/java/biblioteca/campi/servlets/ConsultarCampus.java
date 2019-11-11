package biblioteca.campi.servlets;

import biblioteca.campi.repository.CampiRepository;
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
@WebServlet(name = "ConsultarCampus", urlPatterns = {"/biblioteca/campi/consultar"})
public class ConsultarCampus extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String xml;
		Headers.XMLHeaders(response);
		String id = request.getParameter("id");
		Connection conexao = ConnectionFactory.getBiblioteca();
		CampiRepository rep = new CampiRepository(conexao);

		try {

			xml = rep.consultarPorId(id);
			out.println(xml);
			conexao.close();
		} catch (SQLException ex) {
			response.setStatus(500);
			out.println("<erro><mensagem>Falha ao consultar campis do banco de dados</mensagem></erro>");
		}

	}

}