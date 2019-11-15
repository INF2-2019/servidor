package biblioteca.alunos.servlets;

import biblioteca.alunos.repository.AlunosRepository;
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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import utils.Validators;

@WebServlet(name = "InserirAluno", urlPatterns = {"/biblioteca/alunos/inserir"})
public class Inserir extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getBiblioteca();
		AlunosRepository rep = new AlunosRepository(conexao);
		PrintWriter out = response.getWriter();
		Validators val = new Validators();

		if (rep.checarAutorizacaoADM(request, response) || rep.checarAutorizacaoOperador(request, response)) {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			String sexo = request.getParameter("sexo");
			String nascimento = request.getParameter("nascimento");
			String logradouro = request.getParameter("logradouro");
			String numero = request.getParameter("numero");
			String complemento = request.getParameter("complemento");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String cep = request.getParameter("cep");
			String uf = request.getParameter("uf");
			String foto = request.getParameter("foto");
			try {
				if (!val.isCPF(id)) {
					response.setStatus(422);
					out.println("<erro><mensagem>CPF inserido é inválido</mensagem></erro>");
				} else {
					boolean sucesso = rep.inserirAlunos(id, nome, email, senha, sexo, nascimento, logradouro, numero, complemento, bairro, cidade, cep, uf, foto);
					View sucessoView = new SucessoView("Inserido com sucesso.");
					sucessoView.render(out);
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
			} catch (NoSuchAlgorithmException | ParseException | InvalidKeySpecException ex) {
				response.setStatus(500);
				out.println("<erro><mensagem>Erro severo</mensagem></erro>");
			}
		} else {
			response.setStatus(403);
			out.println("<erro><mensagem>Voce nao tem permissao para fazer isso</mensagem></erro>");
		}

	}

}
