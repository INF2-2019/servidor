package diario.professores;

import utils.ConnectionFactory;
import utils.Hasher;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AtualizarProfessores", urlPatterns = "/diario/professores/atualizar")
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
		resposta.addHeader("Content-Type", "application/xml; charset=utf-8");

		PrintWriter saida = resposta.getWriter();
		saida.println("<root>");
		try (Connection conexao = ConnectionFactory.getDiario()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			validarParametros(requisicao.getParameterMap());
			InsercaoProfessor.validarDepartamento(requisicao.getParameter("id-depto"), conexao);

			String statement = "UPDATE `professores` SET "
					+ "`id-depto` = ?, `nome` = ?, `senha` = ?, "
					+ "`email` = ?, `titulacao` = ? WHERE `id` = ?";
			PreparedStatement ps = conexao.prepareStatement(statement);

			ps.setInt(1, Integer.parseInt(requisicao.getParameter("id-depto")));
			ps.setString(2, requisicao.getParameter("nome"));
			ps.setString(3, Hasher.hash(requisicao.getParameter("senha")));
			ps.setString(4, requisicao.getParameter("email"));
			ps.setString(5, requisicao.getParameter("titulacao"));
			ps.setInt(6, Integer.parseInt(requisicao.getParameter("id")));
			ps.execute();
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

	private void validarParametros(Map<String, String[]> parametros) throws ExcecaoParametrosIncorretos {
		if (parametros.size() < 6) {
			throw new ExcecaoParametrosIncorretos("Parâmetros insuficientes");
		}
		if (parametros.size() > 6) {
			throw new ExcecaoParametrosIncorretos("Parâmetros em excesso");
		}

		for (Map.Entry<String, String[]> iterador : parametros.entrySet()) {
			boolean valido = false;
			for (String param : params) {
				if (iterador.getKey().equals(param)) {
					valido = true;
				}
			}
			if (!valido) {
				throw new ExcecaoParametrosIncorretos("Parâmetro desconhecido: " + iterador.getKey());
			}
		}
	}

}
