package controller.diario.departamentos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.diario.departamentos.Departamento;
import repository.diario.DepartamentoRepository;
import view.diario.DepartamentoView;

@WebServlet(name = "ConsultarDepartamentos", urlPatterns = "/diario/departamentos/ConsultarDepartamentos")
public class ConsultarDepartamentos extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/xml;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");

		try(PrintWriter out = response.getWriter()) {

			try {
				List<Departamento> deptos = DepartamentoRepository.consulta();
				String xml = DepartamentoView.xmlDepartamentos(deptos);
				out.println(xml);
			} catch(SQLException ex) {
				System.err.println(ex);
			}

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