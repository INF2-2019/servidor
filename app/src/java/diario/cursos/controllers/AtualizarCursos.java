package diario.cursos.controllers;

import diario.cursos.repository.CursoRepository;
import diario.cursos.view.ErroView;
import diario.cursos.view.RenderException;
import diario.cursos.view.SucessoView;
import diario.cursos.view.View;
import utils.ConnectionFactory;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name = "AtualizarCursos", urlPatterns = "/diario/cursos/atualizar")
public class AtualizarCursos extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(response);

		DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
		if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
			response.setStatus(403);
			return;
		}

		Connection conexao = ConnectionFactory.getDiario();

		PrintWriter out = response.getWriter();

		// checa se a conexao foi feita
		if (conexao == null) {
			System.err.println("Falha ao conectar ao bd");
			View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		CursoRepository cursoRep = new CursoRepository(conexao);

		Map<String, String> parametros = definirParametros(request);
		// Testa se todos parâmetros necessários foram inseridos
		if (! request.getParameterMap().containsKey("id")) {
			response.setStatus(400);
			View erroView = new ErroView(new Exception("Um ID deve ser passado para a operação de atualização."));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		try {
			cursoRep.atualizar(parametros);
			View sucessoView = new SucessoView("Atualizado com sucesso.");
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
		} catch (NullPointerException e) {
			response.setStatus(400);
			System.err.println("Id inválido. Erro: "+e.toString());

			View erroView = new ErroView(e);
			try {
				erroView.render(out);
			} catch (RenderException ex) {
				throw new ServletException(ex);
			}
		} catch (RenderException e) {
			throw new ServletException(e);
		}
	}

	public Map<String, String> definirParametros(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();

		dados.put("id", req.getParameter("id"));

		if (req.getParameterMap().containsKey("departamento")) {
			dados.put("id-depto", req.getParameter("departamento"));
		}

		if (req.getParameterMap().containsKey("nome")) {
			dados.put("nome", req.getParameter("nome"));
		}

		if (req.getParameterMap().containsKey("horas")) {
			dados.put("horas-total", req.getParameter("horas"));
		}

		if (req.getParameterMap().containsKey("modalidade")) {
			dados.put("modalidade", req.getParameter("modalidade"));
		}

		return dados;
	}

}
