package diario.alunos.servlets;

import diario.alunos.repository.AlunosRepository;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import utils.ConnectionFactory;
import utils.Headers;


@WebServlet(name = "ConsultarAlunos", urlPatterns = {"/diario/alunos/consultar"})
public class ConsultarPorId extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getDiario();
		PrintWriter out = response.getWriter();
		String xml = "";
		Headers.XMLHeaders(response);
		String id = request.getParameter("id");
		AlunosRepository rep = new AlunosRepository(conexao);
		try {
			xml = rep.consultarPorId(id);
			out.println(xml);
			conexao.close();
		} catch (SQLException ex) {
			response.setStatus(500);
			out.println("<erro><mensagem>Falha ao consultar alunos do banco de dados</mensagem></erro>");
		}

	}

}
