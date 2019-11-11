package biblioteca.reservas;

import biblioteca.reservas.repository.ReservaRepository;
import biblioteca.reservas.views.ErroView;
import biblioteca.reservas.views.RenderException;
import biblioteca.reservas.views.SucessoView;
import biblioteca.reservas.views.View;
import utils.ConnectionFactory;
import utils.Headers;
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

@WebServlet(name = "DeletarReservas", urlPatterns = {"/biblioteca/reservas/deletar"})
public class DeletarReservas extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(request, response);
		Connection conexao = ConnectionFactory.getBiblioteca();

		PrintWriter out = response.getWriter();
		BibliotecaAutenticador autenticador = new BibliotecaAutenticador(request, response);

		if (autenticador.cargoLogado() != BibliotecaCargos.ADMIN) {
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
			System.err.println("Falha ao conectar ao bd"); // Adicionar XML de erro
			return;
		}

		ReservaRepository reservaRep = new ReservaRepository(conexao);

		String id = request.getParameter("id");
		try {
			reservaRep.deletar(id);
			View sucessoView = new SucessoView("Deletado com sucesso.");
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

		}
	}
}
