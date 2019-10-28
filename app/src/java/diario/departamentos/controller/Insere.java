package diario.departamentos.controller;

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
import utils.ConnectionFactory;
import utils.Headers;

@WebServlet(name = "InsereDepartamentos", urlPatterns = "/diario/departamentos/insere")
public class Insere extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Headers.XMLHeaders(response);

		try(PrintWriter out = response.getWriter()) {
			Connection con = ConnectionFactory.getDiario();
			if(con != null) {
				try {
					PreparedStatement prst = con.prepareStatement(
							"INSERT INTO `departamentos` (`id-campi`, `nome`) VALUES (?, ?)");
					prst.setInt(1, Integer.parseInt(request.getParameter("id-campi")));
					prst.setString(2, request.getParameter("nome"));
					prst.execute();
					prst.close();
					con.close();
				} catch(SQLException ex) {
					out.println("<erro>Falha ao inserir departamento no banco de dados</erro>");
				} catch(NumberFormatException ex) {
					out.println("<erro>Falha ao receber parâmetros</erro>");
				}
			} else out.println("<erro>Falha ao conectar ao banco de dados</erro>");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Servlet que insere departamento.";
	}

}
