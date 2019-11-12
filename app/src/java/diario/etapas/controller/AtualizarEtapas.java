package diario.etapas.controller;

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

import utils.ConnectionFactory;
import utils.Headers;
import diario.etapas.view.ErroFormatView;
import diario.etapas.view.ErroSqlView;
import diario.etapas.view.ErroSemParametroView;
import diario.etapas.view.SucessoView;
import diario.etapas.view.View;
import diario.etapas.RenderException;
import diario.etapas.repository.EtapasRepository;

import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

@WebServlet(name = "AtualizarEtapas", urlPatterns = "/diario/etapas/atualizar")
public class AtualizarEtapas extends HttpServlet {

	// método doGet será alterado para doPost quando for terminado o front-end
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(request, response);

		DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
		if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
			response.setStatus(403);
			return;
		}

		Connection conexao = ConnectionFactory.getDiario();

		PrintWriter out = response.getWriter();

		if (conexao == null) {
			System.err.println("Falha ao conectar ao bd"); // Adicionar XML de erro
			View erroView = new ErroFormatView(new Exception("Falha ao conectar ao banco de dados"));

			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		EtapasRepository rep = new EtapasRepository(conexao);

		Map<String, String> parametros = definirParametros(request);

		if (parametros == null) {
			response.setStatus(400);

			View erroView = new ErroSemParametroView(new Exception("Erro: algum parâmetro não foi inserido"));

			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}

		} else {
			try {
				rep.atualizar(parametros);
				View sucessoView = new SucessoView("Atualizado com sucesso.");
				sucessoView.render(out);
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

	public Map<String, String> definirParametros(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();
		boolean temPeloMenosUm = false; // variável que verifica se há um parâmetro além do ID

		if (req.getParameterMap().containsKey("id") && !req.getParameter("id").equals("")) {
			dados.put("id", req.getParameter("id"));
		} else {
			return null;
		}

		if (req.getParameterMap().containsKey("ano") && !req.getParameter("ano").equals("")) {
			dados.put("ano", req.getParameter("ano"));
			temPeloMenosUm = true;
		}

		if (req.getParameterMap().containsKey("valor") && !req.getParameter("valor").equals("")) {
			dados.put("valor", req.getParameter("valor"));
			temPeloMenosUm = true;
		}
		if (!temPeloMenosUm) {
			return null;
		}

		return dados;
	}

}
