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


@WebServlet(name = "DeletarAlunos", urlPatterns = {"/diario/alunos/deletar"})
public class DeletarAlunos extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getDiario();
		AlunosRepository rep = new AlunosRepository(conexao);
		PrintWriter out = response.getWriter();

		String id = request.getParameter("id");

		if (rep.checarAutorizacaoADM(request, response)) {

			try {
				String result = rep.deletarAlunos(id);
				if ("sucesso".equals(result)) {
					View sucessoView = new SucessoView("Deletado com sucesso.");
					sucessoView.render(out);
				} else if ("mat".equals(result)) {
					response.setStatus(409);
					out.println("<erro><mensagem>Existe uma matricula registrada na identificacao deste aluno, delete-a antes de deletar o aluno</mensagem></erro>");
				} else {
					out.println("<erro><mensagem>Nao foi possivel deletar o aluno</mensagem></erro>");
				}
			} catch (NumberFormatException excecaoFormatoErrado) {
				response.setStatus(422);
				System.err.println("Numero inteiro invalido para o parametro. Erro: " + excecaoFormatoErrado.toString());

				View erroView = new ErroView(excecaoFormatoErrado);
				try {
					erroView.render(out);
				} catch (RenderException e) {
					throw new ServletException(e);
				}

			} catch (RenderException e) {
				throw new ServletException(e);
			} catch (SQLException excecaoSQL) {
				response.setStatus(500);
				System.err.println("Busca SQL invalida. Erro: " + excecaoSQL.toString());

				View erroView = new ErroView(excecaoSQL);
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
