package biblioteca.emprestimos;

import biblioteca.emprestimos.repository.EmprestimoRepository;
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
//import biblioteca.emprestimos.repository.EmprestimoRepository;
import utils.ConnectionFactory;
import utils.Headers;
import biblioteca.emprestimos.views.RenderException;
import biblioteca.emprestimos.views.View;
import biblioteca.emprestimos.views.SucessoView;
import biblioteca.emprestimos.views.ErroView;
import biblioteca.emprestimos.views.InacessivelException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "InserirEmprestimos", urlPatterns = {"/biblioteca/emprestimos/inserir"})
public class InserirEmprestimos extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Connection conexao = ConnectionFactory.getBiblioteca();
		EmprestimoRepository emprestimoRep = new EmprestimoRepository(conexao);
		Headers.XMLHeaders(response);
		PrintWriter out = response.getWriter();

		if (conexao == null) {
			View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}
		Map<String, String> dados = definirMap(request);

		try {
			//System.out.println("AAAAAA");
			emprestimoRep.inserir(dados);
			View sucessoView = new SucessoView("Inserido com sucesso.");
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
			Logger.getLogger(InserirEmprestimos.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InacessivelException e) {
			response.setStatus(400);
			System.err.println("Problema ao pegar o livro. Erro: " + e.toString());

			View erroView = new ErroView(e);
			try {
				erroView.render(out);
			} catch (RenderException i) {
				throw new ServletException(i);
			}
		}
	}

	public Map<String, String> definirMap(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();

		//System.out.println(req.getParameter("id-alunos"));
		if (req.getParameter("id-alunos") != null) {
			dados.put("id-alunos", req.getParameter("id-alunos"));
		}

		if (req.getParameter("id-acervo") != null) {
			dados.put("id-acervo", req.getParameter("id-acervo"));
		}

		if (req.getParameter("data-emprestimo") != null) {
			dados.put("data-emprestimo", req.getParameter("data-emprestimo"));
		}

		if (req.getParameter("data-prev-devol") != null) {
			dados.put("data-prev-devol", req.getParameter("data-prev-devol"));
		}
		if (req.getParameter("data-devolucao") != null) {
			dados.put("data-devolucao", req.getParameter("data-devolucao"));
		}
		if (req.getParameter("multa") != null) {
			dados.put("multa", req.getParameter("multa"));
		}

		return dados;
	}

}
