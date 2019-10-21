package diario.etapas.controller;

import diario.etapas.RenderException;
import diario.etapas.repository.EtapasRepository;
import diario.etapas.view.ErroView;
import diario.etapas.view.SucessoView;
import diario.etapas.view.View;
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

/**
 *
 * @author hiiam
 */
@WebServlet(name = "AlterarEtapas", urlPatterns = "/diario/etapas/alterar")
public class AtualizarEtapas extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Headers.XMLHeaders(response);
	Connection conexao = ConnectionFactory.getDiario();

	PrintWriter out = response.getWriter();

	if (conexao == null) {
	    System.err.println("Falha ao conectar ao bd"); // Adicionar XML de erro
	    return;
	}

	EtapasRepository rep = new EtapasRepository(conexao);

	Map<String, String> parametros = definirParametros(request);

	if (parametros == null) {
	    response.setStatus(400);
	    View erroView = new ErroView(new Exception("Parâmetros incorretos para a operação de atualização."));
	    try {
		erroView.render(out);
	    } catch (RenderException e) {
		throw new ServletException(e);
	    }
	}

	try {
	    rep.atualizar(parametros);
	    View sucessoView = new SucessoView("Atualizado com sucesso.");
	    sucessoView.render(out);
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

    public Map<String, String> definirParametros(HttpServletRequest req) {
	Map<String, String> dados = new LinkedHashMap<>();
	boolean temPeloMenosUm = false;

	if (req.getParameterMap().containsKey("id")) {
	    dados.put("id", req.getParameter("id"));
	} else {
	    return null;
	}

	if (req.getParameterMap().containsKey("ano")) {
	    dados.put("ano", req.getParameter("ano"));
	} else {
	    temPeloMenosUm = true;
	}

	if (req.getParameterMap().containsKey("valor")) {
	    dados.put("valor", req.getParameter("valor"));
	} else {
	    temPeloMenosUm = true;
	}

	if (!temPeloMenosUm) {
	    return null;
	}

	return dados;
    }

}
