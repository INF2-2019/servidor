package diario.campi.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import diario.campi.repository.*;
import diario.cursos.view.*;
import java.io.PrintWriter;
import utils.ConnectionFactory;
import utils.Headers;


/**
 *
 * @author User
 */
@WebServlet(name = "InserirCampi", urlPatterns = {"/diario/campi/inserir"})
public class InserirCampi extends HttpServlet {

     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getDiario();
		CampiRepository rep = new CampiRepository(conexao);
		PrintWriter out = response.getWriter();
		Headers.XMLHeaders(response);

		if (!rep.checarAutorizacaoADM(request, response)) {
			
			String nome = request.getParameter("nome");
			String cidade = request.getParameter("cidade");
			String uf = request.getParameter("uf");

			try {
				boolean sucesso = rep.inserirCampi(nome, cidade, uf);
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
			}
		} else {
			response.setStatus(401);
			out.println("<erro><mensagem>Voce nao tem permissao para fazer isso</mensagem></erro>");
		}

	}

}
