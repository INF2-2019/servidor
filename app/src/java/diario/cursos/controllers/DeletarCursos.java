package diario.cursos.controllers;

import diario.cursos.repository.CursoRepository;
import diario.cursos.view.*;
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

@WebServlet(name = "DeletarCursos", urlPatterns = "/diario/cursos/deletar")
public class DeletarCursos extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(response);

		DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
		if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
			response.setStatus(403);
			return;
		}

		Connection conexao = ConnectionFactory.getDiario();

		PrintWriter out = response.getWriter();

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

		String idParam = request.getParameter("id");

		try {
			boolean deletado = cursoRep.deletar(idParam);
			View resultado;
			if (deletado)
				resultado = new SucessoView("Deletado com sucesso.");
			else
				resultado = new ErroView(new Exception("Id inválido."));
			resultado.render(out);
		} catch(ExcecaoTurmaVinculada excecao) {
			response.setStatus(400);
			System.err.println("Turma vinculada ao curso, impossível deletar.");

			View erro = new ErroView(excecao);
			try{
				erro.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
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
	}
}
