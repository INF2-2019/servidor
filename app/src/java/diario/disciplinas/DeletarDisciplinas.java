package diario.disciplinas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import diario.disciplinas.repository.DisciplinaRepository;
import utils.ConnectionFactory;
import utils.Headers;
import diario.disciplinas.views.RenderException;
import diario.disciplinas.views.View;
import diario.disciplinas.views.SucessoView;
import diario.disciplinas.views.ErroView;
import diario.disciplinas.views.ExcecaoConteudoVinculado;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

@WebServlet(name = "DeletarDisciplinas", urlPatterns = {"/diario/disciplinas/deletar"})
public class DeletarDisciplinas extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(request, response);
		Connection conexao = ConnectionFactory.getDiario();

		PrintWriter out = response.getWriter();
		DiarioAutenticador autenticador = new DiarioAutenticador(request, response);

		if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
			response.setStatus(403);
			View erroView = new ErroView(new Exception("O usuario não tem permisão para essa operação"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		if (conexao == null) {
			View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		DisciplinaRepository disciplinaRep = new DisciplinaRepository(conexao);

		String id = request.getParameter("id");
		try {
			disciplinaRep.deletar(id);
			View sucessoView = new SucessoView("Deletado com sucesso.");
			sucessoView.render(out);
		} catch (NumberFormatException excecaoFormatoErrado) {
			response.setStatus(400);
			System.err.println("Número inteiro inválido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());

			View erroView = new ErroView(excecaoFormatoErrado);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (SQLException excecaoSQL) {
			response.setStatus(400);
			System.err.println("Busca SQL inválida. Erro: " + excecaoSQL.toString());

			View erroView = new ErroView(excecaoSQL);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (RenderException e) {
			throw new ServletException(e);
		} catch (ExcecaoConteudoVinculado ex) {
			response.setStatus(400);
			View erroView = new ErroView(ex);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		}
	}
}
