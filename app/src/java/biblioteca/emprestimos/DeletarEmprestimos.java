package biblioteca.emprestimos;

import biblioteca.emprestimos.repository.EmprestimoRepository;
import biblioteca.emprestimos.views.ErroView;
import biblioteca.emprestimos.views.RenderException;
import biblioteca.emprestimos.views.SucessoView;
import biblioteca.emprestimos.views.View;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "DeletarEmprestimos", urlPatterns = {"/biblioteca/emprestimos/deletar"})
public class DeletarEmprestimos extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conexao = ConnectionFactory.getBiblioteca();
		BibliotecaAutenticador autenticador = new BibliotecaAutenticador(request, response);
		PrintWriter out = response.getWriter();
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
			System.err.println("Falha ao conectar ao bd");
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
			emprestimoRep.deletar(id);
			View sucessoView = new SucessoView("Deletado com sucesso.");
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
			response.setStatus(500);
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
			Logger.getLogger(DevolverEmprestimos.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
