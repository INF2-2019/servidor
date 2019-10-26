package diario.alunos.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import diario.alunos.repository.*;
import diario.cursos.view.ErroView;
import diario.cursos.view.RenderException;
import diario.cursos.view.SucessoView;
import diario.cursos.view.View;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.ConnectionFactory;
import utils.Headers;

/**
 *
 * @author User
 */
@WebServlet(name = "DeletarAlunos", urlPatterns = {"/diario/alunos/deletar"})
public class DeletarAlunos extends HttpServlet {

     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conexao = ConnectionFactory.getDiario();
        AlunosRepository rep = new AlunosRepository(conexao);
        PrintWriter out = response.getWriter();
        Headers.XMLHeaders(response);
        
       if (rep.checarAutorizacaoADM(request, response)) {
			try {
				String id = request.getParameter("id");
				PreparedStatement ps = conexao.prepareStatement("SELECT * FROM `matriculas` WHERE `id-alunos` = ?");
				int idParsed = Integer.parseUnsignedInt(id);
				ps.setInt(1, idParsed);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					response.setStatus(409);
					out.println("<erro><mensagem>Existe uma matricula registrada na identificacao deste aluno, delete-a antes de deletar o aluno</mensagem></erro>");
				} else {

					try{			
						boolean sucesso = rep.deletarAlunos(id);
						View sucessoView = new SucessoView("Deletado com sucesso.");
						sucessoView.render(out);
					} catch (NumberFormatException excecaoFormatoErrado) {
						response.setStatus(400);
						System.err.println("Numero inteiro invalido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());

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
				System.err.println("Busca SQL invalida. Erro: " + excecaoSQL.toString());

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