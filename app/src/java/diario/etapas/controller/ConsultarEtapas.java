package diario.etapas.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import diario.etapas.model.EtapasModel;
import diario.etapas.repository.EtapasRepository;
import utils.ConnectionFactory;
import utils.Headers;
import diario.etapas.RenderException;
import diario.etapas.view.View;
import diario.etapas.view.EtapasConsultaView;
import diario.etapas.view.ErroView;

@WebServlet(name = "ConsultarEtapas", urlPatterns = "/diario/etapas/consultar")
public class ConsultarEtapas extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Headers.XMLHeaders(response);
	Connection conexao = ConnectionFactory.getDiario();

	PrintWriter out = response.getWriter();

	if (conexao == null) {
	    System.err.println("Falha ao conectar ao bd"); // Adicionar XML de erro
	    return;
	}

	EtapasRepository etapasRep = new EtapasRepository(conexao);

	Set<EtapasModel> resultado;
	Map<String, String> filtros = definirFiltros(request); // criando um Map para armazenar os filtros de maneira pratica

	try {
	    resultado = etapasRep.consultar(filtros); // Executa consulta
	    View etapasConsultaView = new EtapasConsultaView(resultado);
	    etapasConsultaView.render(out);
	} catch (NumberFormatException excecaoFormatoErrado) {
	    response.setStatus(400);
	    System.err.println("Número inteiro inválido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());

	    ErroView erroView = new ErroView(excecaoFormatoErrado);
	    try {
		erroView.render(out);
	    } catch (RenderException e) {
		response.setStatus(500);
		throw new ServletException(e);
	    }
	} catch (SQLException excecaoSQL) {
	    response.setStatus(400);
	    System.err.println("Busca SQL inválida. Erro: " + excecaoSQL.toString());

	    ErroView erroView = new ErroView(excecaoSQL);
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
	if (req.getParameter("ano") != null) {
	    dados.put("ano", req.getParameter("ano"));
	}

	if (req.getParameter("valor") != null) {
	    dados.put("valor", req.getParameter("valor"));
	}

	return dados;
    }
}
