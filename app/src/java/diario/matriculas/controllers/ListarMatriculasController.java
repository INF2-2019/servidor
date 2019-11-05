package diario.matriculas.controllers;

import diario.admin.views.ErroView;
import diario.admin.views.RenderException;
import diario.matriculas.models.Matricula;
import diario.matriculas.repositories.MatriculaRepository;
import diario.matriculas.views.ListaMatriculasView;
import utils.ConnectionFactory;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ListarMatriculasController", urlPatterns = "/diario/matriculas/listar")
public class ListarMatriculasController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			DiarioAutenticador diarioAutenticador = new DiarioAutenticador(request, response);
			if (diarioAutenticador.cargoLogado() != DiarioCargos.CONVIDADO) {
				response.setStatus(403);
				ErroView view = new ErroView("Você não permissão para acessar essa página!");
				view.render(out);
				return;
			}

			try (Connection connection = ConnectionFactory.getDiario()) {
				if (connection == null) {
					throw new SQLException("Falha ao conectar ao banco de dados!");
				}

				Map<String, Object> filters = new HashMap<>();

				if (request.getParameter("id") != null) {
					filters.put("id", Integer.parseInt(request.getParameter("id")));
				}

				if (request.getParameter("idAlunos") != null) {
					filters.put("idAlunos", Long.parseLong(request.getParameter("idAlunos")));
				}

				if (request.getParameter("idDisciplinas") != null) {
					filters.put("idDisciplinas", Integer.parseInt(request.getParameter("idDisciplinas")));
				}

				if (request.getParameter("ano") != null) {
					filters.put("ano", Integer.parseInt(request.getParameter("ano")));
				}

				if (request.getParameter("ativo") != null) {
					filters.put("ativo", Boolean.parseBoolean(request.getParameter("ativo")));
				}

				MatriculaRepository repository = new MatriculaRepository(connection);
				List<Matricula> matriculas = repository.getFilteredMatriculas(filters);

				ListaMatriculasView view = new ListaMatriculasView(matriculas);
				view.render(out);
			} catch (SQLException ex) {
				response.setStatus(500);
				ErroView view = new ErroView("Um erro interno aconteceu!", ex.getMessage());
				view.render(out);
			} catch (NumberFormatException ex) {
				response.setStatus(422);
				ErroView view = new ErroView("Algum parâmetro tem tipo inválido!", ex.getMessage());
				view.render(out);
			}

		} catch (RenderException ex) {
			throw new ServletException(ex);
		}
	}
}
