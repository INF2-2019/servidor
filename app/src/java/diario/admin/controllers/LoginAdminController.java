package diario.admin.controllers;

import diario.admin.models.Admin;
import diario.admin.repositories.AdminNotFoundException;
import diario.admin.repositories.AdminRepository;
import diario.admin.views.ErroView;
import diario.admin.views.RenderException;
import diario.admin.views.SucessoView;
import utils.ConnectionFactory;
import utils.Hasher;
import utils.Headers;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

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

@WebServlet(name = "LoginAdmin", urlPatterns = "/diario/admin/login")
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
				Connection connection = ConnectionFactory.getDiario();
				if (connection == null) {
					throw new SQLException();
				}

				AdminRepository rep = new AdminRepository(connection);
				Admin original = rep.getAdminFromUsuario(login);

				// Login inválido
				if (!Hasher.validar(senha, original.getHashSenha())) {
					throw new AdminNotFoundException();
				}

				DiarioAutenticador autenticador = new DiarioAutenticador(request, response);

				// Adicionar manter logado
				autenticador.logar(original.getId(), DiarioCargos.ADMIN, manter);

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
