package diario.campi.servlets;

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
		String xml;
		Headers.XMLHeaders(request, response);

		try {
			xml = rep.listarCampi();
			out.println(xml);
			conexao.close();
		} catch (SQLException ex) {
			response.setStatus(500);
			out.println("<erro><mensagem>Falha ao listar campis do banco de dados</mensagem></erro>");
		}

	}

}
