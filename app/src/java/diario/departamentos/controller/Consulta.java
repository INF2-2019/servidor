package diario.departamentos.controller;

import diario.departamentos.model.Departamento;
import diario.departamentos.view.View;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;
import utils.Headers;

@WebServlet(name = "ConsultaDepartamentos", urlPatterns = "/diario/departamentos/consulta")
public class Consulta extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Headers.XMLHeaders(response);

		try(PrintWriter out = response.getWriter()) {
			Connection con = ConnectionFactory.getDiario();
			if(con != null)  {
				try {
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM `departamentos`");

					List<Departamento> deptos = new LinkedList();

					while(rs.next()) {
						deptos.add(new Departamento(rs.getInt("id"), rs.getInt("id-campi"), rs.getString("nome")));
					}

					out.println(View.XMLConsulta(deptos));

					stmt.close();
					con.close();
				} catch(SQLException ex) {
					out.println("<erro>Falha ao consultar departamentos do banco de dados</erro>");
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
		return "Servlet que consulta departamentos.";
	}

}
