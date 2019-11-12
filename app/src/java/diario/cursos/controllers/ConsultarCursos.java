package diario.cursos.controllers;

import diario.cursos.models.CursoModel;
import diario.cursos.repository.CursoRepository;
import utils.ConnectionFactory;
import utils.Headers;
import diario.cursos.view.RenderException;
import diario.cursos.view.View;
import diario.cursos.view.CursoConsultaView;
import diario.cursos.view.ErroView;
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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "ConsultarCursos", urlPatterns = "/diario/cursos/consultar")
public class ConsultarCursos extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(request, response);

		DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
		if (autenticador.cargoLogado() == DiarioCargos.CONVIDADO) {
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

		Set<CursoModel> resultado;

		try {
			if (request.getParameterMap().containsKey("id")) {
				resultado = new HashSet<>();
				resultado.add(cursoRep.consultarId(request.getParameter("id")));
			} else {
				resultado = cursoRep.consultar(definirFiltros(request));
			}
			View cursoConsultaView = new CursoConsultaView(resultado);
			cursoConsultaView.render(out);
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
		}

		try {
			conexao.close();
		} catch (SQLException erro) {
			System.err.println("Erro ao fechar banco de dados. Erro: " + erro.toString());
		}

	}

	public Map<String, String> definirFiltros(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();

		// definir os valores do map condicionalmente, conforme a requisição
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
