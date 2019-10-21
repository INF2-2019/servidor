package diario.professores;

import utils.ConnectionFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "deletar", urlPatterns = "/diario/professores/deletar")
/**
 * <h1>Servlet de Remoção de Professor</h1>
 * Servlet dedicado para deletar registros na tablea 'professores'
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class RemoverProfessor extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws IOException {

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.setContentType("text/xml;charset=UTF-8");

		PrintWriter saida = resposta.getWriter();
		saida.println("<root>");
		try (Connection conexao = ConnectionFactory.getDiario()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			String id = requisicao.getParameter("id");
			if (id == null) {
				throw new ExcecaoParametrosIncorretos("O parâmetro 'id' é obrigatório para deletar registros");
			}

			ResultSet rs = conexao.createStatement().executeQuery("SELECT * FROM `professores` WHERE `id` = " + id);
			if (!rs.first()) {
				throw new ExcecaoParametrosIncorretos("O registro a ser deletado não existe");
			}

			conexao.createStatement().executeUpdate("DELETE FROM `professores` WHERE `id` = " + id);
			conexao.close();

			saida.println("<info>");
			saida.println("  <erro>false</erro>");
			saida.println("  <mensagem>Registro deletado com sucesso</mensagem>");
			saida.println("</info>");

		} catch (Exception e) {

			saida.println("<info>");
			saida.println("  <erro>true</erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</info>");

		} finally {
			saida.println("</root>");
		}

	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado para deletar registros na tablea 'professores'";
	}

}
