package diario.etapas.controller;

import diario.etapas.RenderException;
import diario.etapas.repository.EtapasRepository;
import diario.etapas.view.ErroFormatView;
import diario.etapas.view.ErroSqlView;
import diario.etapas.view.SucessoView;
import diario.etapas.view.View;
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
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name = "InserirEtapas", urlPatterns = "/diario/etapas/inserir")
public class InserirEtapas extends HttpServlet {

	// método doGet será alterado para doPost quando for terminado o front-end
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(request, response);

		DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
		if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
			response.setStatus(403);
			return;
		}

		Connection conexao = ConnectionFactory.getDiario();

		EtapasRepository etapasRep = new EtapasRepository(conexao);

		PrintWriter out = response.getWriter();

		if (conexao == null) {
			System.err.println("Falha ao conectar ao bd"); // Adicionar XML de erro
			return;
		}

		Map<String, String> dados = definirFiltros(request);

		//inserindo dados
		try {
			if (etapasRep.inserir(dados)) {
				System.out.println("Inserido com sucesso!");
				View sucessoView = new SucessoView("Inserido com sucesso.");
				sucessoView.render(out);
			} else {
				System.out.println("Não foi inserido.");
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
		} catch (RenderException ex) {
			throw new ServletException(ex);
		}
	}

	public Map<String, String> definirFiltros(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();

		// definir os valores do map condicionalmente, conforme a requisição
		if (req.getParameter("ano") != null  && !req.getParameter("ano").equals("")) {
			dados.put("ano", req.getParameter("ano"));
		} else {
			dados.put("ano", Integer.toString(LocalDate.now().getYear()));
		}

		if (req.getParameter("valor") != null  && !req.getParameter("valor").equals("")) {
			dados.put("valor", req.getParameter("valor"));
		}

		return dados;
	}
}
