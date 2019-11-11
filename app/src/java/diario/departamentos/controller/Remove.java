package diario.departamentos.controller;

import diario.departamentos.repository.DepartamentoInexistenteException;
import diario.departamentos.repository.DepartamentoRepository;
import diario.departamentos.view.ErroView;
import diario.departamentos.view.RenderException;
import diario.departamentos.view.SucessoView;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Headers;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

@WebServlet(name = "RemoveDepartamentos", urlPatterns = "/diario/departamentos/remove")
public class Remove extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Headers.XMLHeaders(response);
		PrintWriter out = response.getWriter();

		Exception excecao = null;

		try {

			DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
			if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
				throw new AutenticacaoException();
			}

			String idStr = request.getParameter("id");

			if (idStr == null) {
				throw new ParametrosIncorretosException("Informe o id do departamento");
			}

			int id;
			try {
				id = Integer.parseInt(idStr);
			} catch (NumberFormatException ex) {
				throw new ParametrosIncorretosException("Id do departamento deve ser inteiro");
			}

			DepartamentoRepository.remove(id);

			SucessoView sucessoView = new SucessoView("Departamento removido com sucesso");
			sucessoView.render(out);
		} catch (AutenticacaoException ex) {
			response.setStatus(403);
			excecao = ex;
		} catch (ParametrosIncorretosException ex) {
			response.setStatus(422);
			excecao = ex;
		} catch (DepartamentoInexistenteException ex) {
			response.setStatus(404);
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
