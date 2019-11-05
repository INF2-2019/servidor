package biblioteca.emprestimos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import biblioteca.emprestimos.repository.EmprestimoRepository;
import utils.ConnectionFactory;
import utils.Headers;
import biblioteca.emprestimos.views.RenderException;
import biblioteca.emprestimos.views.View;
import biblioteca.emprestimos.views.SucessoView;
import biblioteca.emprestimos.views.ErroView;
import java.text.ParseException;
import utils.autenticador.BibliotecaAutenticador;
import utils.autenticador.BibliotecaCargos;

@WebServlet(name = "DevolverEmprestimos", urlPatterns = {"/biblioteca/emprestimos/devolver"})
public class DevolverEmprestimos extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(response);
		Connection conexao = ConnectionFactory.getBiblioteca();

		PrintWriter out = response.getWriter();

		BibliotecaAutenticador autenticador = new BibliotecaAutenticador(request, response);
		/*
		if ((autenticador.cargoLogado() != BibliotecaCargos.ADMIN) || (autenticador.cargoLogado() != BibliotecaCargos.OPERADOR)) {
			response.setStatus(403);
			View erroView = new ErroView(new Exception("O usuario não tem permisão para essa operação"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}
		*/
		if (conexao == null) {
			response.setStatus(500);
			View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		EmprestimoRepository emprestimoRep = new EmprestimoRepository(conexao);

		String id = request.getParameter("id");
		try {
			double multa = emprestimoRep.devolver(id);
			View sucessoView = new SucessoView("Devolvido com sucesso.", multa);

			sucessoView.render(out);
		} catch (NumberFormatException excecaoFormatoErrado) {
			response.setStatus(400);
			View erroView = new ErroView(new Exception("Número inteiro inválido para o parâmetro."));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (SQLException excecaoSQL) {
			response.setStatus(500);
			View erroView = new ErroView(new Exception("Falha ao realizar a pesquisa no banco de dados."));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (RenderException e) {
			throw new ServletException(e);
		} catch (ParseException ex) {
			response.setStatus(400);
			View erroView = new ErroView(new Exception("Alguma informação fornecida está desformatada."));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		}
	}

}
