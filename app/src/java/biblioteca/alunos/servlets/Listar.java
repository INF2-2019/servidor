package biblioteca.alunos.servlets;

import biblioteca.alunos.repository.AlunosRepository;
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


@WebServlet(name = "ListarAluno", urlPatterns = {"/biblioteca/alunos/listar"})
public class Listar extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getBiblioteca();
		AlunosRepository rep = new AlunosRepository(conexao);
		PrintWriter out = response.getWriter();
		String xml = "";


		try {
			xml = rep.listarAlunos();
			out.println(xml);
			conexao.close();
		} catch (SQLException ex) {
			response.setStatus(500);
			out.println("<erro><mensagem>Falha ao listar alunos do banco de dados</mensagem></erro>");
		}

	}

}
