package diario.departamentos.controller;

import diario.departamentos.model.Departamento;
import diario.departamentos.repository.DepartamentoInexistenteException;
import diario.departamentos.repository.DepartamentoRepository;
import diario.departamentos.view.DepartamentosView;
import diario.departamentos.view.ErroView;
import diario.departamentos.view.RenderException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

		try (PrintWriter out = response.getWriter()) {

			Exception excecao = null;

			try {
				List<Departamento> deptos;
				if (request.getParameter("id") == null) {
					deptos = DepartamentoRepository.consulta();
				} else {
					deptos = new LinkedList();
					int id;
					try {
						id = Integer.parseInt(request.getParameter("id"));
					} catch (NumberFormatException ex) {
						throw new ParametrosIncorretosException("O Id deve ser um inteiro");
					}
					deptos.add(DepartamentoRepository.consulta(id));
				}

				DepartamentosView departamentosView = new DepartamentosView(deptos);
				departamentosView.render(out);
			} catch (ParametrosIncorretosException ex) {
				response.setStatus(422);
				excecao = ex;
			} catch (DepartamentoInexistenteException ex) {
				response.setStatus(400);
				excecao = ex;
			} catch (SQLException ex) {
				response.setStatus(500);
				excecao = ex;
			} catch (RenderException ex) {
				throw new ServletException();
			} finally {
				if (excecao != null) {
					ErroView erroView = new ErroView(excecao);
					try {
						erroView.render(out);
					} catch (RenderException ex) {
						throw new ServletException();
					}
				}

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
