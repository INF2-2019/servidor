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

		if (rep.checarAutorizacaoADM(request, response)) {
			try {
			String id = request.getParameter("id");
			PreparedStatement ps = conexao.prepareStatement("SELECT * FROM `departamentos` WHERE `id-campi` = ?");
			int idParsed = Integer.parseUnsignedInt(id);
			ps.setInt(1, idParsed);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				response.setStatus(409);
				out.println("<erro><mensagem>Existe um departamento registrado neste campi, delete-o antes de deletar o campi</mensagem></erro>");
			} else {
							
				try{			
					boolean sucesso = rep.deletarCampi(id);
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
				
				} catch (RenderException e) {
					throw new ServletException(e);
				}
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
			}
			
			
			
        } else {
			response.setStatus(401);
			out.println("<erro><mensagem>Voce nao tem permissao para fazer isso</mensagem></erro>");
		}       
    }

}
