package biblioteca.reservas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import biblioteca.reservas.repository.ReservaRepository;
import biblioteca.reservas.views.AlunoException;
import utils.ConnectionFactory;
import utils.Headers;

import biblioteca.reservas.views.View;
import biblioteca.reservas.views.SucessoView;
import biblioteca.reservas.views.ErroView;
import biblioteca.reservas.views.ExcecaoreservaExistente;
import biblioteca.reservas.views.RenderException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.autenticador.BibliotecaAutenticador;
import utils.autenticador.BibliotecaCargos;

@WebServlet(name = "InserirReservas", urlPatterns = {"/biblioteca/reservas/inserir"})
public class InserirReservas extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Connection conexao = ConnectionFactory.getBiblioteca();
		ReservaRepository ReservaRep = new ReservaRepository(conexao);
		PrintWriter out = response.getWriter();
		Headers.XMLHeaders(response);
		BibliotecaAutenticador autenticador = new BibliotecaAutenticador(request, response);
		if (autenticador.cargoLogado() == BibliotecaCargos.CONVIDADO) {
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
		Map<String, String> dados = definirMap(request);

		try {
			ReservaRep.inserir(dados);
			View sucessoView = new SucessoView("Inserido com sucesso.");
			sucessoView.render(out);
		} catch (NumberFormatException excecaoFormatoErrado) {
			response.setStatus(422);
			System.err.println("Número inteiro inválido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());

			View erroView = new ErroView(excecaoFormatoErrado);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (SQLException excecaoSQL) {
			response.setStatus(500);
			System.err.println("Busca SQL inválida. Erro: " + excecaoSQL.toString());

			View erroView = new ErroView(excecaoSQL);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (RenderException e) {
			throw new ServletException(e);
		} catch (ParseException ex) {
			response.setStatus(400);
			View erroView = new ErroView(ex);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (ExcecaoreservaExistente ex) {
			response.setStatus(403);
			View erroView = new ErroView(ex);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (AlunoException ex) {
			response.setStatus(403);
			System.err.println("Erro ao inserir aluno. Erro: " + ex.toString());

			View erroView = new ErroView(ex);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		}
	}

	public Map<String, String> definirMap(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();

		if (req.getParameter("id-alunos") != null) {
			dados.put("id-alunos", req.getParameter("id-alunos"));
		}

		if (req.getParameter("id-acervo") != null) {
			dados.put("id-acervo", req.getParameter("id-acervo"));
		}

		if (req.getParameter("tempo-espera") != null) {
			dados.put("tempo-espera", req.getParameter("tempo-espera"));
		}
		if (req.getParameter("data-reserva") != null) {
			dados.put("data-reserva", req.getParameter("data-reserva"));
		}
		if (req.getParameter("emprestou") != null) {
			dados.put("emprestou", req.getParameter("emprestou"));
		}

		return dados;
	}

}
