package biblioteca.alunos.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import biblioteca.alunos.repository.*;
import diario.cursos.view.ErroView;
import diario.cursos.view.RenderException;
import diario.cursos.view.SucessoView;
import diario.cursos.view.View;
import utils.ConnectionFactory;
import utils.Headers;


@WebServlet(name = "Delet", urlPatterns = {"/biblioteca/alunos/deletar"})
public class Delet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getBiblioteca();
		AlunosRepository rep = new AlunosRepository(conexao);
		PrintWriter out = response.getWriter();
		Headers.XMLHeaders(response);
		String id = request.getParameter("id");

		if (rep.checarAutorizacaoADM(request, response) || rep.checarAutorizacaoOperador(request, response)) {

			try {
				String result = rep.deletarAlunos(id);
				if ("sucesso".equals(result)) {
					View sucessoView = new SucessoView("Deletado com sucesso.");
					sucessoView.render(out);
				} else if ("emprestimo".equals(result)) {
					response.setStatus(409);
					out.println("<erro><mensagem>Existe um empresetimo registrado na identificacao deste aluno, delete-o antes de deletar o aluno</mensagem></erro>");
				} else if ("reserva".equals(result)) {
					response.setStatus(409);
					out.println("<erro><mensagem>Existe uma reserva registrada na identificacao deste aluno, delete-a antes de deletar o aluno</mensagem></erro>");
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
