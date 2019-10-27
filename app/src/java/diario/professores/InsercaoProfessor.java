package diario.professores;

import utils.ConnectionFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Hasher;

@WebServlet(name = "InserirProfessores", urlPatterns = "/diario/professores/inserir")
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
			throws IOException {

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.addHeader("Content-Type", "application/xml; charset=utf-8");
		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getDiario()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			// Validação dos parâmetros
			for (String param : params) {
				if (requisicao.getParameter(param) == null) {
					throw new ExcecaoParametrosIncorretos("Todos os parâmetros devem ser preenchidos na inserção de um registro");
				}
			}

			validarDepartamento(requisicao.getParameter("id-depto"), conexao);

			PreparedStatement ps = conexao.prepareStatement("INSERT INTO `professores` VALUES (?, ?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.parseInt(requisicao.getParameter("id")));
			ps.setInt(2, Integer.parseInt(requisicao.getParameter("id-depto")));
			ps.setString(3, requisicao.getParameter("nome"));
			ps.setString(4, Hasher.hash(requisicao.getParameter("senha")));
			ps.setString(5, requisicao.getParameter("email"));
			ps.setString(6, requisicao.getParameter("titulacao"));

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
		return "Servlet dedicado à inserção de registro de professores no BD";
	}

	public static void validarDepartamento(String depto, Connection con)
			throws SQLException, ExcecaoParametrosIncorretos {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM `departamentos` WHERE id = ?");
		ps.setString(1, depto);
		ps.execute();
		ResultSet resultado = ps.getResultSet();

		if (!resultado.first()) {
			throw new ExcecaoParametrosIncorretos("Departamento informado não encontrado");
		}
	}

}
