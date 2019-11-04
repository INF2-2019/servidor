package biblioteca.emprestimos;

import biblioteca.emprestimos.model.EmprestimoModel;
import biblioteca.emprestimos.repository.EmprestimoRepository;
import biblioteca.emprestimos.views.AlunoException;
import biblioteca.emprestimos.views.SucessoView;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;
import utils.Headers;
import java.util.SortedMap;
import biblioteca.emprestimos.views.View;
import biblioteca.emprestimos.views.ErroView;
import biblioteca.emprestimos.views.RenderException;
import java.text.ParseException;
import utils.autenticador.BibliotecaAutenticador;
import utils.autenticador.BibliotecaCargos;

@WebServlet(name = "AtualizarEmprestimos", urlPatterns = {"/biblioteca/emprestimos/atualizar"})
public class AtualizarEmprestimos extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Headers.XMLHeaders(res);
		Connection con = ConnectionFactory.getBiblioteca();
		PrintWriter out = res.getWriter();

		BibliotecaAutenticador autenticador = new BibliotecaAutenticador(req, res);

		if ((autenticador.cargoLogado() != BibliotecaCargos.ADMIN) || (autenticador.cargoLogado() != BibliotecaCargos.OPERADOR)) {
			res.setStatus(403);
			View erroView = new ErroView(new Exception("O usuario não tem permisão para essa operação"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		if (con == null) {
			res.setStatus(500);
			View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		EmprestimoRepository disciplinaRep = new EmprestimoRepository(con);
		String id = req.getParameter("id");

		try {
			SortedMap<String, String> filtros = EmprestimoModel.definirMap(req);
			disciplinaRep.atualizar(filtros, id);
			View sucessoView = new SucessoView("Atualizado com sucesso.");
			sucessoView.render(out);
		} catch (NumberFormatException excecaoFormatoErrado) {
			res.setStatus(400);
			View erroView = new ErroView(new Exception("Número inteiro inválido para o parâmetro."));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (SQLException excecaoSQL) {
			res.setStatus(500);
			View erroView = new ErroView(new Exception("Falha ao realizar a pesquisa no banco de dados."));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (RenderException e) {
			throw new ServletException(e);
		} catch (ParseException ex) {
			res.setStatus(500);
			View erroView = new ErroView(new Exception("Alguma informação fornecida está desformatada."));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (AlunoException ex) {
			res.setStatus(400);
			View erroView = new ErroView(ex);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		}

	}

}
