package diario.departamentos.controller;

import diario.departamentos.repository.DepartamentoRepository;
import diario.departamentos.view.ErroView;
import diario.departamentos.view.SucessoView;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Headers;

@WebServlet(name = "AtualizaDepartamentos", urlPatterns = "/diario/departamentos/atualiza")
public class Atualiza extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Headers.XMLHeaders(request, response);

		try(PrintWriter out = response.getWriter()) {
			try {
				if(request.getParameter("id") == null)
					throw new Exception("Informe o id do departamento");
				int id = Integer.parseInt(request.getParameter("id"));

				if(request.getParameter("id-campi") != null) {
					int idCampi = Integer.parseInt(request.getParameter("id-campi"));
					DepartamentoRepository.atualiza(id, idCampi);
				}
				if(request.getParameter("nome") != null) {
					String nome = request.getParameter("nome");
					DepartamentoRepository.atualiza(id, nome);
				}

				out.println(SucessoView.sucesso("Departamento atualizado com sucesso"));
			} catch(Exception ex) {
				out.println(ErroView.erro("Falha ao atualizar departamento", ex));
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
		return "Servlet que atualiza departamento.";
	}

}
