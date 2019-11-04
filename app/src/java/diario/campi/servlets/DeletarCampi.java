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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConnectionFactory;
import utils.Headers;

/**
 *
 * @author User
 */
@WebServlet(name = "DeletarCampi", urlPatterns = {"/diario/campi/deletar"})
public class DeletarCampi extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getDiario();
		CampiRepository rep = new CampiRepository(conexao);
		PrintWriter out = response.getWriter();

		Headers.XMLHeaders(response);
		String id = request.getParameter("id");
		if (rep.checarAutorizacaoADM(request, response)) {
			try{			
				String sucesso = rep.deletarCampi(id, request, response);
				if("sucesso".equals(sucesso)) {
					View sucessoView = new SucessoView("Deletado com sucesso.");
					try {
						sucessoView.render(out);
					} catch (RenderException ex) {
						throw new ServletException(ex);
					}
				} else {
					out.println(sucesso);
				}
			} catch (NumberFormatException excecaoFormatoErrado) {
				response.setStatus(400);
				System.err.println("Número inteiro inválido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());
				View erroView = new ErroView(excecaoFormatoErrado);
				try {
					erroView.render(out);
				} catch (RenderException e) {
					throw new ServletException(e);
				}
			} catch (SQLException ex) {
				View erroView = new ErroView(ex);
				try {
					erroView.render(out);
				} catch (RenderException e) {
					throw new ServletException(e);
				}
			}


			
			
		} else {
			response.setStatus(401);
			out.println("<erro><mensagem>Voce nao tem permissao para fazer isso</mensagem></erro>");
		}       
    }

}
