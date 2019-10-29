package biblioteca.acervo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;

@WebServlet(name = "inserir", urlPatterns = {"/biblioteca/acervo/inserir"})
/**
 * <h1> Servlet de Inserção de Obras</h1>
 * Servlet para a requisição de inclusão de registro nas tabelas
 * 'acervo','livros','midias','periodicos' e 'academicos'
 *
 * @author Indra Matsiendra
 * @author Jonata Novais Cirqueira
 */
public class InserirAcervo extends HttpServlet {

	/*id-acervo nas 4 outras tabelas equivale ao 'id' em paramsAcervo*/
	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.setContentType("application/xml;charset=UTF-8");

		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getBiblioteca()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			Validacao.validarParametros(requisicao);
//			Validacao.validarIdCampi(Integer.parseInt(requisicao.getParameter("id-campi")), conexao);
			Validacao.validarIdObra(Integer.parseInt(requisicao.getParameter("id-obra")), conexao);

			String clausulaSql = "INSERT INTO `acervo` "
					+ "(`id-campi`, `nome`, `tipo`, `local`, `ano`, `editora`, `paginas`)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conexao.prepareStatement(clausulaSql);
			ps.setInt(1, Integer.parseInt(requisicao.getParameter("id-campi")));
			ps.setString(2, requisicao.getParameter("nome"));
			ps.setString(3, requisicao.getParameter("tipo").toUpperCase());
			ps.setString(4, requisicao.getParameter("local"));
			ps.setInt(5, Integer.parseInt(requisicao.getParameter("ano")));
			ps.setString(6, requisicao.getParameter("editora"));
			ps.setInt(7, Integer.parseInt(requisicao.getParameter("paginas")));
			ps.executeUpdate();
			ps.close();

			// Obtendo o `id` com característica auto-incremento
			ResultSet resultado = conexao.createStatement().executeQuery("SELECT LAST_INSERT_ID() AS last_id FROM `acervo`");
			resultado.first();
			int id = Integer.parseInt(resultado.getString("last_id"));

			switch (requisicao.getParameter("tipo").toLowerCase()) {
				case "academicos":
					inserirAcademico(id, requisicao, conexao);
					break;
				case "livros":
					inserirLivro(id, requisicao, conexao);
					break;
				case "midias":
					inserirMidia(id, requisicao, conexao);
					break;
				case "periodicos":
					inserirPeriodico(id, requisicao, conexao);
					break;
			}
			conexao.close();

			saida.println("<sucesso>");
			saida.println("	 <mensagem>Registro inserido com sucesso</mensagem>");
			saida.println("</sucesso>");

		} catch (Exception e) {
			saida.println("<erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</erro>");
		}
	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado à inserção de registro de obras no BD";
	}

	private void inserirAcademico(int idAcervo, HttpServletRequest requisicao, Connection conexao)
			throws SQLException, NumberFormatException {
		PreparedStatement ps = conexao.prepareStatement("INSERT INTO `academicos` VALUES (?, ?, ?)");
		ps.setInt(1, Integer.parseInt(requisicao.getParameter("id-obra")));
		ps.setInt(2, idAcervo);
		ps.setString(3, requisicao.getParameter("programa"));
		ps.execute();
		ps.close();
	}

	private void inserirLivro(int idAcervo, HttpServletRequest requisicao, Connection conexao)
			throws SQLException, NumberFormatException {
		PreparedStatement ps = conexao.prepareStatement("INSERT INTO `livros` VALUES (?, ?, ?, ?)");
		ps.setInt(1, Integer.parseInt(requisicao.getParameter("id-obra")));
		ps.setInt(2, idAcervo);
		ps.setInt(3, Integer.parseInt(requisicao.getParameter("edicao")));
		ps.setLong(4, Long.parseLong(requisicao.getParameter("isbn")));
		ps.execute();
		ps.close();
	}

	private void inserirMidia(int idAcervo, HttpServletRequest requisicao, Connection conexao)
			throws SQLException, NumberFormatException {
		PreparedStatement ps = conexao.prepareStatement("INSERT INTO `midias` VALUES (?, ?, ?, ?)");
		ps.setInt(1, Integer.parseInt(requisicao.getParameter("id-obra")));
		ps.setInt(2, idAcervo);
		ps.setString(3, requisicao.getParameter("tempo")); // o formato deverá ser adaptado no front-end
		ps.setString(4, requisicao.getParameter("subtipo").toUpperCase());
		ps.execute();
		ps.close();
	}

	private void inserirPeriodico(int idAcervo, HttpServletRequest requisicao, Connection conexao)
			throws SQLException, NumberFormatException {
		PreparedStatement ps = conexao.prepareStatement("INSERT INTO `periodicos` "
				+ "(`id-acervo`, `periodicidade`, `mes`, `volume`, `subtipo`, `issn`) "
				+ "VALUES (?, ?, ?, ?, ?, ?)");
		ps.setInt(1, idAcervo);
		ps.setString(2, requisicao.getParameter("periodicidade"));
		ps.setString(3, requisicao.getParameter("mes"));
		ps.setInt(4, Integer.parseInt(requisicao.getParameter("volume")));
		ps.setString(5, requisicao.getParameter("subtipo"));
		ps.setInt(6, Integer.parseInt(requisicao.getParameter("issn")));
		ps.execute();
		ps.close();
	}

}
