package diario.departamentos.controller;

import diario.departamentos.model.DepartamentoValidation;
import diario.departamentos.repository.DepartamentoRepository;
import diario.departamentos.view.ErroView;
import diario.departamentos.view.RenderException;
import diario.departamentos.view.SucessoView;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "InsereDepartamentos", urlPatterns = "/diario/departamentos/insere")
public class Insere extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		Exception excecao = null;

		try {

			DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
			if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
				throw new AutenticacaoException();
			}

			String idCampiStr = request.getParameter("id-campi");
			String nome = request.getParameter("nome");

			if (nome == null && idCampiStr == null) {
				throw new ParametrosIncorretosException("Informe o nome e o campus do departamento");
			} else if (idCampiStr == null) {
				throw new ParametrosIncorretosException("Informe o campus do departamento");
			}
			DepartamentoValidation.validaNome(nome);

			int idCampi;
			try {
				idCampi = Integer.parseInt(idCampiStr);
			} catch (NumberFormatException ex) {
				throw new ParametrosIncorretosException("Id do campus deve ser inteiro");
			}

			DepartamentoRepository.insere(idCampi, nome);

			SucessoView sucessoView = new SucessoView("Departamento inserido com sucesso");
			sucessoView.render(out);
		} catch (AutenticacaoException ex) {
			response.setStatus(403);
			excecao = ex;
		} catch (ParametrosIncorretosException ex) {
			response.setStatus(422);
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
		return "Servlet que insere departamento.";
	}

}
