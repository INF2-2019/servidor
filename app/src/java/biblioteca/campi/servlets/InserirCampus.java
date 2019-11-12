package biblioteca.campi.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import biblioteca.campi.repository.*;
import diario.cursos.view.*;
import java.io.PrintWriter;
import utils.ConnectionFactory;
import utils.Headers;

/**
 *
 * @author User
 */
@WebServlet(name = "InserirCampus", urlPatterns = {"/biblioteca/campi/inserir"})
public class InserirCampus extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getBiblioteca();
		CampiRepository rep = new CampiRepository(conexao);
		PrintWriter out = response.getWriter();
		Headers.XMLHeaders(request, response);

		if (rep.checarAutorizacaoADM(request, response)) {

			String nome = request.getParameter("nome");
			String cidade = request.getParameter("cidade");
			String uf = request.getParameter("uf");

			try {
				boolean sucesso = rep.inserirCampi(nome, cidade, uf);
				View sucessoView = new SucessoView("Inserido com sucesso.");
				sucessoView.render(out);
			} catch (NumberFormatException excecaoFormatoErrado) {
				response.setStatus(422);
				View erroView = new ErroView(excecaoFormatoErrado);
				try {
					erroView.render(out);
				} catch (RenderException e) {
					throw new ServletException(e);
				}
			} catch (SQLException excecaoSQL) {
				response.setStatus(500);
				View erroView = new ErroView(excecaoSQL);
				try {
					erroView.render(out);
				} catch (RenderException e) {
					throw new ServletException(e);
				}
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} else {
			response.setStatus(403);
			out.println("<erro><mensagem>Voce nao tem permissao para fazer isso</mensagem></erro>");
		}

	}

}
