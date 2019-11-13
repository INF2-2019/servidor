package biblioteca.campi.servlets;

import biblioteca.campi.repository.CampiRepository;
import diario.cursos.view.ErroView;
import diario.cursos.view.RenderException;
import diario.cursos.view.SucessoView;
import diario.cursos.view.View;
import utils.ConnectionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author User
 */
@WebServlet(name = "DeletarCampus", urlPatterns = {"/biblioteca/campi/deletar"})
public class DeletarCampus extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getBiblioteca();
		CampiRepository rep = new CampiRepository(conexao);
		PrintWriter out = response.getWriter();


		String id = request.getParameter("id");
		if (rep.checarAutorizacaoADM(request, response)) {
			try {
				String sucesso = rep.deletarCampi(id);
				if ("sucesso".equals(sucesso)) {
					View sucessoView = new SucessoView("Deletado com sucesso.");
					try {
						sucessoView.render(out);
					} catch (RenderException ex) {
						throw new ServletException(ex);
					}

				} else if ("acervo".equals(sucesso)) {
					response.setStatus(409);
					out.println("<erro><mensagem>Existe um acervo registrado neste campi, delete-o antes de deletar o campi</mensagem></erro>");
				} else {
					out.println("<erro><mensagem>Não foi possivel deletar o campi</mensagem></erro>");
				}
			} catch (NumberFormatException excecaoFormatoErrado) {
				response.setStatus(422);
				System.err.println("Número inteiro inválido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());
				View erroView = new ErroView(excecaoFormatoErrado);
				try {
					erroView.render(out);
				} catch (RenderException e) {
					throw new ServletException(e);
				}
			} catch (SQLException ex) {
				response.setStatus(500);
				View erroView = new ErroView(ex);
				try {
					erroView.render(out);
				} catch (RenderException e) {
					throw new ServletException(e);
				}
			}

		} else {
			response.setStatus(403);
			out.println("<erro><mensagem>Voce nao tem permissao para fazer isso</mensagem></erro>");
		}
	}

}
