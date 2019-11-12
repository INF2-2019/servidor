package biblioteca.admin.controllers;

import biblioteca.admin.models.Admin;
import biblioteca.admin.repositories.AdminNotFoundException;
import biblioteca.admin.repositories.AdminRepository;
import biblioteca.admin.views.ErroView;
import biblioteca.admin.views.RenderException;
import biblioteca.admin.views.SucessoView;
import utils.ConnectionFactory;
import utils.Hasher;
import utils.Headers;
import utils.autenticador.BibliotecaAutenticador;
import utils.autenticador.BibliotecaCargos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "LoginAdminBiblioteca", urlPatterns = "/biblioteca/admin/login")
public class LoginAdminController extends HttpServlet {

	private final static String SEM_LOGIN = "O campo login é obrigatório!";
	private final static String SEM_SENHA = "O campo senha é obrigatório!";
	private final static String CREDENCIAIS = "Credenciais inválidas!";
	private final static String INTERNO = "Um erro interno ocorreu!";
	private final static String LOGADO = "Logado com sucesso!";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(request, response);
		PrintWriter out = response.getWriter();

		String login, senha;
		boolean manter;
		login = request.getParameter("login");
		senha = request.getParameter("senha");
		manter = request.getParameter("manter") != null;

		try {
			if (login == null) {
				new ErroView(SEM_LOGIN).render(out);
				return;
			}

			if (senha == null) {
				new ErroView(SEM_SENHA).render(out);
				return;
			}

			try {
				Connection connection = ConnectionFactory.getBiblioteca();
				if (connection == null) {
					throw new SQLException();
				}

				AdminRepository rep = new AdminRepository(connection);
				Admin original = rep.getAdminFromUsuario(login);

				// Login inválido
				if (!Hasher.validar(senha, original.getHashSenha())) {
					throw new AdminNotFoundException();
				}

				BibliotecaAutenticador autenticador = new BibliotecaAutenticador(request, response);

				// Adicionar manter logado
				autenticador.logar(original.getId(), BibliotecaCargos.ADMIN, manter);

				response.setStatus(200);
				new SucessoView(LOGADO).render(out);
				return;
			} catch (SQLException | InvalidKeySpecException | NoSuchAlgorithmException ex) {
				response.setStatus(500);
				new ErroView(INTERNO).render(out);
				return;
			} catch (AdminNotFoundException ex) {
				response.setStatus(403);
				new ErroView(CREDENCIAIS).render(out);
				return;
			}
		} catch (RenderException ex) {
			response.setStatus(500);
			throw new ServletException(ex);
		}
	}
}
