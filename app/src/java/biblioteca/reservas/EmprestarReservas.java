package biblioteca.reservas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import biblioteca.reservas.repository.ReservaRepository;
import utils.ConnectionFactory;
import utils.Headers;
import biblioteca.reservas.views.RenderException;
import biblioteca.reservas.views.View;
import biblioteca.reservas.views.SucessoView;
import biblioteca.reservas.views.ErroView;
import biblioteca.reservas.views.ExcecaoEmprestimoCadastrado;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.autenticador.BibliotecaAutenticador;
import utils.autenticador.BibliotecaCargos;

@WebServlet(name = "EmprestarReservas", urlPatterns = {"/biblioteca/reservas/emprestar"})
public class EmprestarReservas extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Headers.XMLHeaders(response);
	Connection conexao = ConnectionFactory.getBiblioteca();

	PrintWriter out = response.getWriter();
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
	    System.err.println("Falha ao conectar ao bd"); // Adicionar XML de erro
	    return;
	}

	ReservaRepository reservaRep = new ReservaRepository(conexao);

	String id = request.getParameter("id");
	try {
	    reservaRep.emprestar(id);
	    View sucessoView = new SucessoView("Emprestado com sucesso.");
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

	} catch (ParseException ex) {
	    Logger.getLogger(EmprestarReservas.class.getName()).log(Level.SEVERE, null, ex);
	} catch (ExcecaoEmprestimoCadastrado ex) {
	    response.setStatus(400);
	    View erroView = new ErroView(ex);
	    try {
		erroView.render(out);
	    } catch (RenderException e) {
		throw new ServletException(e);
	    }
	}
    }
}
