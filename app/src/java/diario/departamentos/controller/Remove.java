package diario.departamentos.controller;

import diario.departamentos.view.ErroView;
import diario.departamentos.view.SucessoView;
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

@WebServlet(name = "RemoveDepartamentos", urlPatterns = "/diario/departamentos/remove")
public class Remove extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Headers.XMLHeaders(response);

		try(PrintWriter out = response.getWriter()) {
			Connection con = ConnectionFactory.getDiario();
			if(con != null) {
				try {
					PreparedStatement prst = con.prepareStatement(
							"DELETE FROM `departamentos` WHERE `id` = ?");
					prst.setInt(1, Integer.parseInt(request.getParameter("id")));
					prst.execute();
					prst.close();
					con.close();
					out.println(SucessoView.sucesso("Departamento removido com sucesso"));
				} catch(SQLException ex) {
					out.println(ErroView.erro("Falha ao remover departamento", ex));
				} catch(NumberFormatException ex) {
					out.println(ErroView.erro("Falha ao receber parâmetros", ex));
				}
			} else out.println(ErroView.erro("Falha ao conectar ao banco de dados", new SQLException()));
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
		return "Servlet que remove departamento.";
	}

}
