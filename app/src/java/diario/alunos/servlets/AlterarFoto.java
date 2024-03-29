package diario.alunos.servlets;

import diario.alunos.repository.AlunosRepository;
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


@WebServlet(name = "AlterarFoto", urlPatterns = {"/diario/alunos/altfoto"})
public class AlterarFoto extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getDiario();
		AlunosRepository rep = new AlunosRepository(conexao);
		PrintWriter out = response.getWriter();

		String id = request.getParameter("id");
		String foto = request.getParameter("foto");

		if (rep.checarAutorizacaoAluno(request, response, id) || rep.checarAutorizacaoADM(request, response)) {

			try {
				if (rep.alterarFoto(id, foto)) {
					View sucessoView = new SucessoView("Foto atualizada com sucesso.");
					sucessoView.render(out);
				} else {
					out.println("<erro><mensagem>Não foi possível alterar a foto</mensagem></erro>");
				}
			} catch (SQLException excecaoSQL) {
				response.setStatus(500);
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
			response.setStatus(403);
			out.println("<erro><mensagem>Voce nao tem permissao para fazer isso</mensagem></erro>");
		}

	}

}
