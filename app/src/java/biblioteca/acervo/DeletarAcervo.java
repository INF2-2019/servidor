package biblioteca.acervo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.ConnectionFactory;

@WebServlet(name = "DeletarAcervo", urlPatterns = "/biblioteca/acervo/deletar")
/**
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class DeletarAcervo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws IOException {

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.addHeader("Content-Type", "application/xml; charset=utf-8");

		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getBiblioteca()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			String id = requisicao.getParameter("id"), msg = "Registro deletado com sucesso";
			if (id == null) {
				throw new ExcecaoParametrosIncorretos("O parâmetro 'id' é obrigatório para deletar registros");
			}

			if (haEmprestimos(id, conexao)) {
				throw new ExcecaoParametrosIncorretos("Este item não pode ser deletado porque está emprestado");
			}
			HashMap<String, String> alunos = alunosQueReservaram(id, conexao);
			for (Map.Entry<String, String> it : alunos.entrySet()) {
				EmailHelper.enviarEmail(it.getValue(), it.getKey());
			}

			PreparedStatement ps1 = conexao.prepareStatement("SELECT `tipo` FROM `acervo` WHERE `id` = ?");
			ps1.setInt(1, Integer.parseInt(id));
			ResultSet resultado = ps1.executeQuery();
			if (resultado.first()) {
				String tipo = resultado.getString(1).toLowerCase();
				PreparedStatement ps2 = conexao.prepareStatement(
						String.format("DELETE FROM `%s` WHERE `id-acervo` = ?", tipo));
				ps2.setInt(1, Integer.parseInt(id));
				ps2.execute();
				ps2.close();
			} else {
				msg = "Nenhum registro alterado";
			}

			PreparedStatement ps3 = conexao.prepareStatement("DELETE FROM `acervo` WHERE `id` = ?");
			ps3.setInt(1, Integer.parseInt(id));
			ps3.execute();
			ps1.close();
			ps3.close();
			conexao.close();

			saida.println("<sucesso>");
			saida.println("  <mensagem>" + msg + "</mensagem>");
			saida.println("</sucesso>");

		} catch (Exception e) {

			saida.println("<erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</erro>");

		}

	}

	private boolean haEmprestimos(String id, Connection con)
			throws SQLException, NumberFormatException {

		PreparedStatement ps = con.prepareStatement("SELECT * FROM `emprestimos` WHERE `id-acervo` = ?");
		ps.setInt(1, Integer.parseInt(id));
		ResultSet resultado = ps.executeQuery();
		return resultado.first();

	}

	private HashMap alunosQueReservaram(String id, Connection con)
			throws SQLException, NumberFormatException {

		HashMap<String, String> alunos = new HashMap<>();

		PreparedStatement ps = con.prepareStatement("SELECT `id-aluno` FROM `reservas` WHERE `id-acervo` = ?");
		ps.setInt(1, Integer.parseInt(id));
		ResultSet emprestimos = ps.executeQuery();
		while (emprestimos.next()) {
			PreparedStatement ps2 = con.prepareStatement("SELECT `nome`, `email` FROM `alunos` WHERE `id` = ?");
			ps2.setInt(1, emprestimos.getInt(1));
			ResultSet temp = ps2.executeQuery();
			temp.first();
			alunos.put(temp.getString(1), temp.getString(2));
		}
		return alunos;

	}

}
