package diario.matriculas.controllers;

import diario.admin.views.ErroView;
import diario.admin.views.RenderException;
import diario.admin.views.SucessoView;
import diario.matriculas.models.Matricula;
import diario.matriculas.repositories.MatriculaRepository;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "InsereMatriculaController", urlPatterns = "/diario/matriculas/inserir")

public class InsereMatriculaController extends HttpServlet {
	public final static Set<String> RQUIRED_PARAMS = new HashSet<>(Arrays.asList("idAlunos", "idDisciplinas", "ano"));

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {

			DiarioAutenticador diarioAutenticador = new DiarioAutenticador(request, response);
			if (diarioAutenticador.cargoLogado() != DiarioCargos.ADMIN) {
				response.setStatus(403);
				ErroView view = new ErroView("Você não permissão para acessar essa página!");
				view.render(out);
				return;
			}

			if (!request.getParameterMap().keySet().containsAll(RQUIRED_PARAMS)) {
				response.setStatus(422);
				ErroView view = new ErroView("Parâmetros insuficientes!");
				view.render(out);
				return;
			}

			try (Connection connection = ConnectionFactory.getDiario()) {
				if (connection == null) {
					throw new SQLException("Falha ao conectar ao banco de dados!");
				}

				Matricula matricula = new Matricula(
					Long.parseLong(request.getParameter("idAlunos")),
					Integer.parseInt(request.getParameter("idDisciplinas")),
					Integer.parseInt(request.getParameter("ano"))
				);

				MatriculaRepository repository = new MatriculaRepository(connection);
				repository.insertMatricula(matricula);
				SucessoView view = new SucessoView("Aluno matrículado com sucesso!");
				view.addParameter("id", matricula.getId().toString());
				response.setStatus(201); // Status code 201: Created
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
