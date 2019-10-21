package diario.professores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;

@WebServlet(name = "atualizar", urlPatterns = "/diario/professores/atualizar")
/**
 * <h1>Servlet de Atualização de Professores</h1>
 * Servlet dedicado a atualizar registros da tabela 'professores'
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class AtualizarProfessor extends HttpServlet {

	private static final String[] params = {"id", "id-depto", "nome", "senha", "email", "titulacao"};

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

			// Alterando apenas parâmetros enviados
			int id = -1;
			Map<String, String[]> camposAtualizados = requisicao.getParameterMap();
			String sqlQuery = "UPDATE `professores` SET ";
			for (Map.Entry<String, String[]> iterador : camposAtualizados.entrySet()) {
				// Confere se é um parâmetro válido
				boolean valido = false;
				for (String param : params) {
					if (iterador.getKey().equals(param)) {
						valido = true;
					}
				}
				if (!valido) {
					throw new ExcecaoParametrosIncorretos("Parâmetro desconhecido: " + iterador.getKey());
				}

				// Não altera o id primário
				if (iterador.getKey().equals("id")) {
					id = Integer.parseInt(iterador.getValue()[0]);
					continue;
				}

				sqlQuery += "`" + iterador.getKey() + "` = '" + iterador.getValue()[0] + "',";
			}
			sqlQuery = sqlQuery.substring(0, sqlQuery.length() - 1); // Remove a última vírgula
			sqlQuery += "WHERE `id` = " + id + "";

			conexao.createStatement().executeUpdate(sqlQuery);
			conexao.close();

			saida.println("<info>");
			saida.println("  <erro>false</erro>");
			saida.println("  <mensagem>Registro alterado com sucesso</mensagem>");
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
		return "Servlet dedicado a atualizar registros da tabela 'professores'";
	}

}
