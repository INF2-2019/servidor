package biblioteca.reservas;

import biblioteca.reservas.repository.ReservaRepository;
import biblioteca.reservas.views.*;
import utils.ConnectionFactory;
import utils.autenticador.BibliotecaAutenticador;
import utils.autenticador.BibliotecaCargos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name = "AtualizarReservas", urlPatterns = {"/biblioteca/reservas/atualizar"})
public class AtualizarReservas extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Connection con = ConnectionFactory.getBiblioteca();
		PrintWriter out = res.getWriter();
		BibliotecaAutenticador autenticador = new BibliotecaAutenticador(req, res);

		if (autenticador.cargoLogado() != BibliotecaCargos.ADMIN) {
			res.setStatus(403);
			View erroView = new ErroView(new Exception("O usuario não tem permisão para essa operação"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		if (con == null) {
			View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		ReservaRepository reservaaRep = new ReservaRepository(con);
		if (!req.getParameterMap().containsKey("id")) {
			res.setStatus(422);
			View erroView = new ErroView(new Exception("Um ID deve ser passado para a operação de atualização."));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}
		String id = req.getParameter("id");
		Map<String, String> filtros = definirMap(req);
		try {
			reservaaRep.atualizar(filtros, id);
			View sucessoView = new SucessoView("Atualizado com sucesso.");
			sucessoView.render(out);
		} catch (NumberFormatException excecaoFormatoErrado) {
			res.setStatus(400);
			System.err.println("Número inteiro inválido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());

			View erroView = new ErroView(excecaoFormatoErrado);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (SQLException excecaoSQL) {
			res.setStatus(500);
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
			res.setStatus(400);
			System.err.println("Algum dado está com seu tipo errado. Erro: " + ex.toString());

			View erroView = new ErroView(ex);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (AlunoException ex) {
			res.setStatus(404);
			System.err.println("O aluno não existe. Erro: " + ex.toString());

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
