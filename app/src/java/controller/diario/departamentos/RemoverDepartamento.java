package controller.diario.departamentos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository.diario.DepartamentoRepository;

@WebServlet(name = "RemoverDepartamento", urlPatterns = "/diario/departamentos/RemoverDepartamento")
public class RemoverDepartamento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/xml;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");

		try(PrintWriter out = response.getWriter()) {

			try {
				int id = Integer.parseInt(request.getParameter("id"));
				DepartamentoRepository.remove(id);
			} catch(SQLException | NumberFormatException ex) {
				out.println("<erro>" + ex + "</erro>");
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
        return "Servlet que remove departamento";
    }
}