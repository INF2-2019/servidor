package diario.professores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;

@WebServlet(name = "inserir", urlPatterns = "/diario/professores/inserir")
/**
 * <h1> Servlet de Inserção de Professor</h1>
 * Servlet para a requisição de indlusão de registro na tabela 'professores'
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class InsercaoProfessor extends HttpServlet {

	private static final String[] params = {"id", "id-depto", "nome", "senha", "email", "titulacao"};

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		try {
			for (String param : params) {
				if (requisicao.getParameter(param) == null) {
					resposta.setStatus(400);
					return;
				}
			}
		} catch (IllegalStateException e) {
			System.err.print(e.getMessage());
		}

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.setContentType("text/xml;charset=UTF-8");

		PrintWriter saida = resposta.getWriter();
		saida.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?");
		try {

			String id = requisicao.getParameter(params[0]),
					idDepto = requisicao.getParameter(params[1]),
					nome = requisicao.getParameter(params[2]),
					senha = requisicao.getParameter(params[3]),
					email = requisicao.getParameter(params[4]),
					titulacao = requisicao.getParameter(params[5]).toUpperCase();

			String clausulaSql = "INSERT INTO `professores` VALUES ("
					+ id + ", " + idDepto + ", '" + nome + "', '" + senha + "', '"
					+ email + "', '" + titulacao + "')";

			ConnectionFactory.getDiario().createStatement().executeUpdate(clausulaSql);

			saida.println("<info>");
			saida.println("	 <mensagem>");
			saida.println("    Registro inserido com sucesso.");
			saida.println("  </mensagem>");
			saida.println("</info>");

		} catch (Exception e) {
			saida.println("<info>");
			saida.println("  <mensagem>");
			saida.println("    Houve um erro de execução.");
			saida.println("  </mensagem>");
			saida.println("  <erro>");
			saida.println(e.getMessage());
			saida.println("  </erro>");
			saida.println("</info>");
		}

	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado à inserção de registro de professores no BD";
	}

}
