package diario.professores;

import utils.ConnectionFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeletarProfessores", urlPatterns = "/diario/professores/deletar")
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
		resposta.addHeader("Content-Type", "application/xml; charset=utf-8");

		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getDiario()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			String id = requisicao.getParameter("id");
			if (id == null) {
				throw new ExcecaoParametrosIncorretos("O parâmetro 'id' é obrigatório para deletar registros");
			}

			PreparedStatement ps = conexao.prepareStatement("DELETE FROM `professores` WHERE `id` = ?");
			ps.setInt(1, Integer.parseInt(id));
			ps.execute();
			ps.close();
			conexao.close();

			saida.println("<sucesso>");
			saida.println("  <mensagem>Registro deletado com sucesso</mensagem>");
			saida.println("</sucesso>");

		} catch (Exception e) {

			saida.println("<erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</erro>");

		}

	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado para deletar registros na tablea 'professores'";
	}

}
