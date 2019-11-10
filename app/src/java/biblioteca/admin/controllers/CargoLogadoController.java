package biblioteca.admin.controllers;

import diario.admin.views.RenderException;
import diario.admin.views.SucessoView;
import utils.autenticador.BibliotecaAutenticador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CargoLogadoControllerBiblioteca", urlPatterns = "/biblioteca/cargo")
public class CargoLogadoController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		BibliotecaAutenticador autenticador = new BibliotecaAutenticador(request, response);
		try {
			SucessoView sucessoView = new SucessoView("Cargo consultado com sucesso!");
			sucessoView.addParameter("cargo", autenticador.cargoLogado().toString());
			Object id = autenticador.idLogado();
			if (id == null) {
				id = "";
			}
			sucessoView.addParameter("id", id.toString());
			sucessoView.render(out);
		} catch (RenderException ex) {
			throw new ServletException(ex);
		}
	}
}
