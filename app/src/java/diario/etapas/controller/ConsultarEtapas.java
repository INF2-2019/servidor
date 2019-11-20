package diario.etapas.controller;

import diario.etapas.RenderException;
import diario.etapas.model.EtapasModel;
import diario.etapas.repository.EtapasRepository;
import diario.etapas.view.ErroFormatView;
import diario.etapas.view.ErroSqlView;
import diario.etapas.view.EtapasConsultaView;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "ConsultarEtapas", urlPatterns = "/diario/etapas/consultar")
public class ConsultarEtapas extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
		if (autenticador.cargoLogado() == DiarioCargos.CONVIDADO) {
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

		Set<EtapasModel> resultado;
		Map<String, String> filtros = definirFiltros(request); // criando um Map para armazenar os filtros de maneira pratica

		System.out.println(filtros.toString());

		try {
			resultado = etapasRep.consultar(filtros); // Executa consulta
			View etapasConsultaView = new EtapasConsultaView(resultado);
			etapasConsultaView.render(out);
		} catch (NumberFormatException excecaoFormatoErrado) {
			response.setStatus(400);
			System.err.println("Número inteiro inválido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());

			View erroView = new ErroFormatView(excecaoFormatoErrado);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				response.setStatus(500);
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

		try {
			conexao.close();
		} catch (SQLException erro) {
			System.err.println("Erro ao fechar banco de dados. Erro: " + erro.toString());
		}

	}

	public Map<String, String> definirFiltros(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();

		// definir os valores do map condicionalmente, conforme a requisição
		if (req.getParameter("id") != null && !req.getParameter("id").equals("")) {
			dados.put("id", req.getParameter("id"));
		}

		if (req.getParameter("ano") != null && !req.getParameter("ano").equals("")) {
			dados.put("ano", req.getParameter("ano"));
		}

		if (req.getParameter("valor") != null && !req.getParameter("valor").equals("")) {
			dados.put("valor", req.getParameter("valor"));
		}

		return dados;
	}
}
