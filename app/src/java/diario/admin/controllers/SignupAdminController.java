package diario.admin.controllers;

import diario.admin.models.Admin;
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

@WebServlet(name = "SignupAdminController", urlPatterns = "/diario/admin/cadastrar")
public class SignupAdminController extends HttpServlet {
	private final static String SEM_NOME = "O parâmetro nome é obrigatório!";
	private final static String SEM_USUARIO = "O parâmetro usuário é obrigatório!";
	private final static String SEM_EMAIL = "O parâmetro email é obrigatório!";
	private final static String SEM_SENHA = "O parâmetro senha é obrigatório!";
	private final static String INTERNAL = "Uma falha interna ocorreu!";
	private final static String SUCCESS = "Administrador adicionado com sucesso!";
	private final static String SOMENTE_ADMIN = "Você não tem permissão para acessar essa rota!";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(request, response);
		PrintWriter out = response.getWriter();
		DiarioAutenticador autenticador = new DiarioAutenticador(request, response);

		String
			nomeParam = request.getParameter("nome"),
			usuarioParam = request.getParameter("usuario"),
			emailParam = request.getParameter("email"),
			senhaParam = request.getParameter("senha");

		try {
			if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
				response.setStatus(403);
				new ErroView(SOMENTE_ADMIN).render(out);
				return;
			}

			if (nomeParam == null) {
				response.setStatus(422);
				new ErroView(SEM_NOME).render(out);
				return;
			}
			if (usuarioParam == null) {
				response.setStatus(422);
				new ErroView(SEM_USUARIO).render(out);
				return;
			}
			if (emailParam == null) {
				response.setStatus(422);
				new ErroView(SEM_EMAIL).render(out);
				return;
			}
			if (senhaParam == null) {
				response.setStatus(422);
				new ErroView(SEM_SENHA).render(out);
				return;
			}

			try (Connection db = ConnectionFactory.getDiario()) {
				if (db == null) {
					throw new SQLException("Null database connection");
				}

				Admin novoAdmin = new Admin(nomeParam, usuarioParam, emailParam, Hasher.hash(senhaParam));
				AdminRepository repository = new AdminRepository(db);
				repository.insertAdmin(novoAdmin);

				SucessoView view = new SucessoView(SUCCESS);
				view.addParameter("id", Integer.toString(novoAdmin.getId()));
				view.render(out);
				return;
			} catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
				System.err.println(ex.getMessage());
				response.setStatus(500);
				new ErroView(INTERNAL, ex.getMessage()).render(out);
				return;
			}

		} catch (RenderException ex) {
			response.setStatus(500);
			throw new ServletException(ex);
		}
	}
}
