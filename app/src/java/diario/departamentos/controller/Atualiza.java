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

@WebServlet(name = "AtualizaDepartamentos", urlPatterns = "/diario/departamentos/atualiza")
public class Atualiza extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Headers.XMLHeaders(response);

		try(PrintWriter out = response.getWriter()) {
			Connection con = ConnectionFactory.getDiario();
			if(con != null) {
				try {
					PreparedStatement prst = con.prepareStatement(
							"UPDATE `departamentos` SET `id-campi` = ?, `nome` = ? WHERE `id` = ?");
					prst.setInt(1, Integer.parseInt(request.getParameter("id-campi")));
					prst.setString(2, request.getParameter("nome"));
					prst.setInt(3, Integer.parseInt(request.getParameter("id")));
					prst.execute();
					prst.close();
					con.close();
				} catch(SQLException ex) {
					out.println("<erro>Falha ao atualizar departamento no banco de dados</erro>");
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
		return "Servlet que atualiza departamento.";
	}

}
