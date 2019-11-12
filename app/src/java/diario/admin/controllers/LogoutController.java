package diario.admin.controllers;

import diario.admin.views.RenderException;
import diario.admin.views.SucessoView;
import utils.autenticador.DiarioAutenticador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LogoutDiarioController", urlPatterns = "/diario/admin/logout")
public class LogoutController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
		try {
			autenticador.encerrar();
			SucessoView sucessoView = new SucessoView("Deslogado com sucesso!");
			sucessoView.render(out);
		} catch (RenderException ex) {
			throw new ServletException(ex);
		}
	}
}
