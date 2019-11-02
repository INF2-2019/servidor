package biblioteca.emprestimos;

import biblioteca.emprestimos.model.EmprestimoModel;
import biblioteca.emprestimos.repository.EmprestimoRepository;
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
import java.util.TreeMap;
import biblioteca.emprestimos.views.View;
import biblioteca.emprestimos.views.ErroView;
import biblioteca.emprestimos.views.RenderException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AtualizarEmprestimos", urlPatterns = {"/biblioteca/emprestimos/atualizar"})
public class AtualizarEmprestimos extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Headers.XMLHeaders(res);
		Connection con = ConnectionFactory.getBiblioteca();
		PrintWriter out = res.getWriter();
		if (con == null) {
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
		SortedMap<String, String> filtros = EmprestimoModel.definirMap(req);
		try {
			disciplinaRep.atualizar(filtros, id);
			View sucessoView = new SucessoView("Atualizado com sucesso.");
			sucessoView.render(out);
		} catch (NumberFormatException excecaoFormatoErrado) {
			res.setStatus(400);
			System.err.println("Número inteiro inválido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());

			View erroView = new ErroView(excecaoFormatoErrado);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (SQLException excecaoSQL) {
			res.setStatus(400);
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
			Logger.getLogger(AtualizarEmprestimos.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
