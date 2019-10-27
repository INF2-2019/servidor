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
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

@WebServlet(name = "InsereDepartamentos", urlPatterns = "/diario/departamentos/insere")
public class Insere extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Headers.XMLHeaders(response);

		try(PrintWriter out = response.getWriter()) {
			try {
				if(request.getParameter("id-campi") == null || request.getParameter("nome") == null)
					throw new Exception("Informe o id-campi e o nome do departamento");
				int idCampi = Integer.parseInt(request.getParameter("id-campi"));
				String nome = request.getParameter("nome");
				DepartamentoRepository.insere(idCampi, nome);
				out.println(SucessoView.sucesso("Departamento inserido com sucesso"));
			} catch(Exception ex) {
				out.println(ErroView.erro("Falha ao inserir departamento", ex));
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
		return "Servlet que insere departamento.";
	}

}
