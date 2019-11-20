package diario.etapas.controller;

import diario.etapas.RenderException;
import diario.etapas.repository.EtapasRepository;
import diario.etapas.view.ErroFormatView;
import diario.etapas.view.ErroSqlView;
import diario.etapas.view.SucessoView;
import diario.etapas.view.View;
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

@WebServlet(name = "Deletar", urlPatterns = "/diario/etapas/deletar")
public class DeletarEtapas extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
		if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
			response.setStatus(403);
			return;
		}

		Connection conexao = ConnectionFactory.getDiario();

		PrintWriter out = response.getWriter();

		if (conexao == null) {
			System.err.println("Falha ao conectar ao bd"); // Adicionar XML de erro
			return;
		}

		EtapasRepository etapasRep = new EtapasRepository(conexao);

		String idParam = request.getParameter("id");

		try {
			boolean sucesso = etapasRep.deletar(idParam);
			if (sucesso) {
				View sucessoView = new SucessoView("Deletado com sucesso!");
				sucessoView.render(out);
			}
		} catch (NumberFormatException excecaoFormatoErrado) {
			response.setStatus(400);
			System.err.println("Número inteiro inválido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());

			View erroView = new ErroFormatView(excecaoFormatoErrado);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (SQLException excecaoSQL) {
			response.setStatus(400);
			System.err.println("Busca SQL inválida. Erro: " + excecaoSQL.toString());

			View erroView = new ErroSqlView(excecaoSQL);
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
