package biblioteca.acervo;

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
import utils.ConnectionFactory;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

@WebServlet(name = "ConsultarAcervo", urlPatterns = "/biblioteca/acervo/consultar")
/**
 *
 * @author Jonata Novais Cirqueira
 */
public class ConsultarAcervo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.addHeader("Content-Type", "application/xml; charset=utf-8");

		PrintWriter saida = resposta.getWriter();
		saida.println("<acervo>");
		try (Connection conexao = ConnectionFactory.getBiblioteca()) {

			DiarioAutenticador autenticador = new DiarioAutenticador(requisicao, resposta);
			if (autenticador.cargoLogado() == DiarioCargos.CONVIDADO) {
				throw new ExcecaoParametrosIncorretos("Você não tem permissão para essa operação");
			}

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			ResultSet acervo;
			if (requisicao.getParameter("id") != null) {
				PreparedStatement ps = conexao.prepareStatement("SELECT * FROM `acervo` WHERE `id` = ?");
				ps.setInt(1, Integer.parseInt(requisicao.getParameter("id")));
				acervo = ps.executeQuery();
			} else {
				acervo = conexao.createStatement().executeQuery("SELECT * FROM `acervo`");
			}

			while (acervo.next()) {
				saida.println("<item>");
				escreverItemDados(acervo, saida);

				String tipo = acervo.getString("tipo").toLowerCase();
				ResultSet item = conexao.createStatement().executeQuery(
						String.format("SELECT * FROM `%s` WHERE `id-acervo` = %d", tipo, acervo.getInt("id")));
				item.first();
				switch (tipo) {
					case "livros":
						escreverLivro(item, saida);
						break;
					case "academicos":
						escreverAcademico(item, saida);
						break;
					case "midias":
						escreverMidia(item, saida);
						break;
					case "periodicos":
						escreverPeriodico(item, saida);
						break;
				}
				saida.println("</item>");
			}

		} catch (SQLException e) {
			resposta.setStatus(500);
			saida.println("<erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</erro>");
		} catch (Exception e) {
			resposta.setStatus(400);
			saida.println("<erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</erro>");
		} finally {
			saida.println("</acervo>");
		}

	}

	private void escreverItemDados(ResultSet item, PrintWriter saida) throws SQLException {
		saida.println("  <id>" + item.getInt("id") + "</id>");
		saida.println("  <id-campi>" + item.getInt("id-campi") + "</id-campi>");
		saida.println("  <nome>" + item.getString("nome") + "</nome>");
		saida.println("  <tipo>" + item.getString("tipo") + "</tipo>");
		saida.println("  <local>" + item.getString("local") + "</local>");
		saida.println("  <ano>" + item.getInt("ano") + "</ano>");
		saida.println("  <editora>" + item.getString("editora") + "</editora>");
		saida.println("  <paginas>" + item.getInt("paginas") + "</paginas>");
	}

	private void escreverLivro(ResultSet item, PrintWriter saida) throws SQLException {
		saida.println("<livro>");
		saida.println("  <id-obra>" + item.getInt("id-obra") + "</id-obra>");
		saida.println("  <edicao>" + item.getInt("edicao") + "</edicao>");
		saida.println("  <isbn>" + item.getLong("isbn") + "</isbn>");
		saida.println("</livro>");
	}

	private void escreverAcademico(ResultSet item, PrintWriter saida) throws SQLException {
		saida.println("<academico>");
		saida.println("  <id-obra>" + item.getInt("id-obra") + "</id-obra>");
		saida.println("  <programa>" + item.getString("programa") + "</programa>");
		saida.println("</academico>");
	}

	private void escreverMidia(ResultSet item, PrintWriter saida) throws SQLException {
		saida.println("<midia>");
		saida.println("  <id-obra>" + item.getInt("id-obra") + "</id-obra>");
		saida.println("  <tempo>" + item.getTime("tempo") + "</tempo>");
		saida.println("  <subtipo>" + item.getString("subtipo") + "</subtipo>");
		saida.println("</midia>");
	}

	private void escreverPeriodico(ResultSet item, PrintWriter saida) throws SQLException {
		saida.println("<periodico>");
		saida.println("  <id>" + item.getInt("id") + "</id>");
		saida.println("  <periodicidade>" + item.getString("periodicidade") + "</periodicidade>");
		saida.println("  <mes>" + item.getString("mes") + "</mes>");
		saida.println("  <volume>" + item.getInt("volume") + "</volume>");
		saida.println("  <subtipo>" + item.getString("subtipo") + "</subtipo>");
		saida.println("  <issn>" + item.getInt("issn") + "</issn>");
		saida.println("</periodico>");
	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado a entrega dos dados do acervo em XML";
	}

}
