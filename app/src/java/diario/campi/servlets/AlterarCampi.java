package diario.campi.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import diario.campi.repository.*;
import diario.cursos.view.*;
import java.io.PrintWriter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import utils.ConnectionFactory;
import utils.Headers;

/**
 *
 * @author User
 */
@WebServlet(name = "AlterarCampi", urlPatterns = {"/diario/campi/alterar"})
public class AlterarCampi extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getDiario();
		CampiRepository rep = new CampiRepository(conexao);
		PrintWriter out = response.getWriter();
		Headers.XMLHeaders(response);
		
		if (!rep.checarAutorizacaoADM(request, response)) {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String cidade = request.getParameter("cidade");
			String uf = request.getParameter("uf");
			try {
				rep.alterarCampi(id,nome,cidade,uf);
				View sucessoView = new SucessoView("Atualizado com sucesso.");
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
			} catch (TransformerException | ParserConfigurationException ex) {
				Logger.getLogger(AlterarCampi.class.getName()).log(Level.SEVERE, null, ex);
			}
		
		} else {
			response.setStatus(401);
			out.println("<erro><mensagem>Voce nao tem permissao para fazer isso</mensagem></erro>");
		} 

	}

}
