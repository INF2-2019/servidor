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
import utils.Headers;

@WebServlet(name = "RemoverDepartamento", urlPatterns = "/diario/departamentos/RemoverDepartamento")
public class RemoverDepartamento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Headers.XMLHeaders(response);

		try(PrintWriter out = response.getWriter()) {

			try {
				int id = Integer.parseInt(request.getParameter("id"));
				DepartamentoRepository.remove(id);
				out.println("<msg>Departamento removido com sucesso</msg>");
			} catch(SQLException ex) {
				out.println("<msg>Falha ao remover do banco de dados</msg>");
			} catch(NumberFormatException ex) {
				out.println("<msg>Falha ao formatar parâmetros</msg>");
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