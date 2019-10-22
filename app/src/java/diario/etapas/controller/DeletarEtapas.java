package diario.etapas.controller;

import diario.etapas.repository.EtapasRepository;
import utils.ConnectionFactory;
import utils.Headers;
import diario.etapas.RenderException;
import diario.etapas.view.View;
import diario.etapas.view.ErroFormatView;
import diario.etapas.view.ErroSqlView;
import diario.etapas.view.SucessoView;

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

    // método doGet será alterado para doPost quando for terminado o front-end
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Headers.XMLHeaders(response);
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
