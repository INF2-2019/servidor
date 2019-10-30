package biblioteca.acervo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

/**
 * Servlet dedicado a atualizar registros do acervo e outras tabelas da biblioteca
 *
 * @author Indra Matsiendra
 * @author Jonata Novais Cirqueira
 */
@WebServlet(name = "atualizar", urlPatterns = {"/biblioteca/acervo/atualizar"})
public class AtualizarAcervo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws IOException {

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.setContentType("application/xml;charset=UTF-8");

		DiarioAutenticador autenticador = new DiarioAutenticador(requisicao, resposta);
		if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
			resposta.setStatus(403);
			return;
		}

		PrintWriter saida = resposta.getWriter();

		try (Connection conexao = ConnectionFactory.getBiblioteca()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			/*VALIDAÇÕES*/
			if (requisicao.getParameter("id") == null) {
				throw new ExcecaoParametrosIncorretos("Parâmetro obrigaótrio: 'id'");
			}
			Validacao.validarParametros(requisicao);
			Validacao.validarIdCampi(Integer.parseInt(requisicao.getParameter("id-campi")), conexao);
			Validacao.validarIdObra(Integer.parseInt(requisicao.getParameter("id")), conexao);

			/*MODIFICAÇÃO DO ACERVO:*/
			String statement = "UPDATE `acervo` SET "
					+ "`id` = ? , `id-campi` = ? ,`nome` = ? , `tipo` = ? , `local` = ?, "
					+ "`ano` = ? , `editora` = ? , `paginas` = ? WHERE `id` = ?";
			PreparedStatement ps = conexao.prepareStatement(statement);
			ps.setInt(1, Integer.parseInt(requisicao.getParameter("id")));
			ps.setString(2, requisicao.getParameter("id-campi"));
			ps.setString(3, requisicao.getParameter("nome"));
			ps.setString(4, requisicao.getParameter("tipo"));
			ps.setString(5, requisicao.getParameter("local"));
			ps.setInt(6, Integer.parseInt(requisicao.getParameter("ano")));
			ps.setString(7, requisicao.getParameter("editora"));
			ps.setString(8, requisicao.getParameter("paginas"));
			ps.setInt(9, Integer.parseInt(requisicao.getParameter("id")));
			ps.execute();
			ps.close();

			/*MODIFICAÇÃO DA TABELA DA OBRA:*/
			switch (requisicao.getParameter("tipo")) {

				case "academicos":
					atualizarAcademico(Integer.parseInt(requisicao.getParameter("id")), requisicao, conexao);
					break;
				case "livros":
					atualizarLivro(Integer.parseInt(requisicao.getParameter("id")), requisicao, conexao);
					break;
				case "midias":
					atualizarMidia(Integer.parseInt(requisicao.getParameter("id")), requisicao, conexao);
					break;
				case "periodicos":
					atualizarPeriodico(Integer.parseInt(requisicao.getParameter("id")), requisicao, conexao);
					break;

			}
			conexao.close();

			saida.println("<sucesso>");
			saida.println("  <mensagem>Registro alterado com sucesso</mensagem>");
			saida.println("</sucesso>");

		} catch (Exception e) {

			saida.println("<erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</erro>");

		}

	}

	private void atualizarAcademico(int idAcervo, HttpServletRequest requisicao, Connection conexao)
			throws SQLException, NumberFormatException {
		PreparedStatement ps = conexao.prepareStatement("UPDATE `academicos` "
				+ "SET `id-obra` = ?, `id-acervo` = ?, `programa` = ? WHERE `id-acervo` = ?");
		ps.setInt(1, Integer.parseInt("id-obra"));
		ps.setInt(2, idAcervo);
		ps.setString(3, requisicao.getParameter("programa"));
		ps.setInt(4, idAcervo);
		ps.execute();
		ps.close();
	}

	private void atualizarLivro(int idAcervo, HttpServletRequest requisicao, Connection conexao)
			throws SQLException, NumberFormatException {
		PreparedStatement ps = conexao.prepareStatement("UPDATE `livros` SET `id-obra` = ? , `id-acervo` = ? ,`edicao` = ? ,`isbn` = ?  WHERE `id-acervo` = ?");
		ps.setInt(1, Integer.parseInt(requisicao.getParameter("id-obra")));
		ps.setInt(2, idAcervo);
		ps.setInt(3, Integer.parseInt(requisicao.getParameter("edicao")));
		ps.setLong(4, Long.parseLong(requisicao.getParameter("isbn")));
		ps.setInt(5, idAcervo);
		ps.execute();
		ps.close();
	}

	private void atualizarMidia(int idAcervo, HttpServletRequest requisicao, Connection conexao)
			throws SQLException, NumberFormatException {
		PreparedStatement ps = conexao.prepareStatement("UPDATE `midias` SET `id-obra` = ? , `id-acervo` = ? ,`tempo` = ? ,`subtipo` = ?  WHERE `id-acervo` = ?");
		ps.setInt(1, Integer.parseInt(requisicao.getParameter("id-obra")));
		ps.setInt(2, idAcervo);
		ps.setString(3, requisicao.getParameter("tempo")); // o formato deverá ser adaptado no front-end
		ps.setString(4, requisicao.getParameter("subtipo").toUpperCase());
		ps.setInt(5, idAcervo);
		ps.execute();
		ps.close();
	}

	private void atualizarPeriodico(int idAcervo, HttpServletRequest requisicao, Connection conexao)
			throws SQLException, NumberFormatException {
		PreparedStatement ps = conexao.prepareStatement("UPDATE `periodicos` SET "
				+ "`id-acervo`=?, `periodicidade`=?, `mes`=?, `volume`=?, `subtipo`=?, `issn`=?"
				+ "WHERE `id-acervo`=?");
		ps.setInt(1, idAcervo);
		ps.setString(2, requisicao.getParameter("periodicidade"));
		ps.setString(3, requisicao.getParameter("mes"));
		ps.setInt(4, Integer.parseInt(requisicao.getParameter("volume")));
		ps.setString(5, requisicao.getParameter("subtipo"));
		ps.setInt(6, Integer.parseInt(requisicao.getParameter("issn")));
		ps.setInt(7, idAcervo);
		ps.execute();
		ps.close();
	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado a atualizar registros do acervo e outras tabelas da biblioteca";
	}

}
