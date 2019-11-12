package biblioteca.emprestimos;

import biblioteca.emprestimos.model.EmprestimoModel;
import biblioteca.emprestimos.repository.EmprestimoRepository;
import biblioteca.emprestimos.views.*;
import utils.ConnectionFactory;
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
import java.text.ParseException;
import java.util.Map;

@WebServlet(name = "InserirEmprestimos", urlPatterns = {"/biblioteca/emprestimos/inserir"})
public class InserirEmprestimos extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Connection conexao = ConnectionFactory.getBiblioteca();
		EmprestimoRepository emprestimoRep = new EmprestimoRepository(conexao);

		PrintWriter out = response.getWriter();

		BibliotecaAutenticador autenticador = new BibliotecaAutenticador(request, response);

		if ((autenticador.cargoLogado() != BibliotecaCargos.ADMIN) && (autenticador.cargoLogado() != BibliotecaCargos.OPERADOR)) {
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
			View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		try {
			Map<String, String> dados = EmprestimoModel.definirMap(request);
			emprestimoRep.inserir(dados);
			View sucessoView = new SucessoView("Inserido com sucesso.");
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
		} catch (InacessivelException e) {
			response.setStatus(400);
			System.err.println("Problema ao pegar o livro. Erro: " + e.toString());

			View erroView = new ErroView(e);
			try {
				erroView.render(out);
			} catch (RenderException i) {
				throw new ServletException(i);
			}
		} catch (AlunoException ex) {
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
