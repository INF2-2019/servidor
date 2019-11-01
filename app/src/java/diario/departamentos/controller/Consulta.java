package diario.departamentos.controller;

import diario.departamentos.model.Departamento;
import diario.departamentos.repository.DepartamentoRepository;
import diario.departamentos.view.ConsultaView;
import diario.departamentos.view.ErroView;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Headers;

@WebServlet(name = "ConsultaDepartamentos", urlPatterns = "/diario/departamentos/consulta")
public class Consulta extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Headers.XMLHeaders(response);

		try(PrintWriter out = response.getWriter()) {

			try {
				List<Departamento> deptos;
				if(request.getParameter("id") == null) {
					deptos = DepartamentoRepository.consulta();
				} else {
					deptos = new LinkedList();
					int id = Integer.parseInt(request.getParameter("id"));
					deptos.add(DepartamentoRepository.consulta(id));
				}

				out.println(ConsultaView.consulta(deptos, null));
			} catch(Exception ex) {
				out.println(ErroView.erro("Falha ao consultar departamentos", ex));
			}

		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Servlet que consulta departamentos.";
	}

}
