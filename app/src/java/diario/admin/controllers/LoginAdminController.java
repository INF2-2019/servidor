package diario.admin.controllers;

import diario.admin.views.ErroView;
import diario.admin.views.RenderException;
import utils.Headers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginAdmin", urlPatterns = "/diario/admin/login")
public class LoginAdminController extends HttpServlet {

	private final static String SEM_LOGIN = "O campo login é obrigatório!";
	private final static String SEM_SENHA = "O campo senha é obrigatório!";
	private final static String CREDENCIAIS = "Credenciais inválidas";

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(response);
		PrintWriter out = response.getWriter();

  	String login, senha;
		login = request.getParameter("login");
		senha = request.getParameter("senha");

		try {
			if(login == null){
				new ErroView(SEM_LOGIN).render(out);
				return;
			}

			if(senha == null){
				new ErroView(SEM_SENHA).render(out);
				return;
			}



		}catch (RenderException ex){
			throw new ServletException(ex);
		}

  }
}
