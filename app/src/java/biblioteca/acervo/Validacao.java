package biblioteca.acervo;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jonata Novais Cirqueira
 */
public class Validacao {

	private static final String REGEXP_HORA = "([01]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?";

	private static final String[] PARAMS_ACERVO = {"id-campi", "nome", "tipo", "local", "ano", "editora", "paginas"};
	private static final String[] PARAMS_LIVROS = {"id-obra", "edicao", "isbn"};
	private static final String[] PARAMS_ACADEMICOS = {"id-obra", "programa"};
	private static final String[] PARAMS_MIDIAS = {"id-obra", "tempo", "subtipo"};
	private static final String[] PARAMS_PERIODICOS = {"id-obra", "periodicidade", "mes", "volume", "subtipo", "issn"};

	public static void validarParametros(HttpServletRequest requisicao)
			throws ExcecaoParametrosIncorretos, NumberFormatException {

		for (String param : PARAMS_ACERVO) {
			if (requisicao.getParameter(param) == null) {
				throw new ExcecaoParametrosIncorretos("Parâmetro obrigatório: " + param);
			}
			if (param.equals("id-campi") || param.equals("ano") || param.equals("paginas")) {
				Integer.parseInt(requisicao.getParameter(param));
			}
		}

		String tipo = requisicao.getParameter("tipo").toLowerCase();
		switch (tipo) {

			case "academicos":
				for (String param : PARAMS_ACADEMICOS) {
					if (requisicao.getParameter(param) == null) {
						throw new ExcecaoParametrosIncorretos("Parâmetro obrigatório: " + param);
					}
					if (param.equals("id-obra")) {
						Integer.parseInt(requisicao.getParameter(param));
					}
				}
				break;

			case "livros":
				for (String param : PARAMS_LIVROS) {
					if (requisicao.getParameter(param) == null) {
						throw new ExcecaoParametrosIncorretos("Parâmetro obrigatório para tipo " + tipo + ": " + param);
					}
					if (param.equals("isbn")) {
						Long.parseLong(requisicao.getParameter(param));
					} else {
						Integer.parseInt(requisicao.getParameter(param));
					}
				}
				break;

			case "midias":
				for (String param : PARAMS_MIDIAS) {
					if (requisicao.getParameter(param) == null) {
						throw new ExcecaoParametrosIncorretos("Parâmetro obrigatório para tipo " + tipo + ": " + param);
					}
					if (param.equals("id-obra")) {
						Integer.parseInt(requisicao.getParameter(param));
					} else if (param.equals("tempo")) {
						Matcher m = Pattern.compile(REGEXP_HORA).matcher(requisicao.getParameter(param));
						if (!m.matches()) {
							throw new ExcecaoParametrosIncorretos("Tempo da mídia em formato inválido");
						}
					}
				}
				break;

			case "periodicos":
				for (String param : PARAMS_PERIODICOS) {
					if (requisicao.getParameter(param) == null) {
						throw new ExcecaoParametrosIncorretos("Parâmetro obrigatório para tipo " + tipo + ": " + param);
					}
					if (param.equals("id-obra") || param.equals("volume") || param.equals("issn")) {
						Integer.parseInt(requisicao.getParameter(param));
					}
				}
				break;

			default:
				throw new ExcecaoParametrosIncorretos("Parâmetro inválido: 'tipo' deve conter um dentre 'academicos', 'livros', 'periodicos', 'midias'");
		}

	}

//	public static void validarIdCampi(int idCampi, Connection conexao)
//			throws ExcecaoParametrosIncorretos, SQLException {
//		PreparedStatement ps = conexao.prepareStatement("SELECT * WHERE `id-campi` = ?");
//		ps.setInt(1, idCampi);
//		ResultSet rs = ps.executeQuery();
//	}
	public static void validarIdObra(int idObra, Connection conexao)
			throws ExcecaoParametrosIncorretos, SQLException {

		PreparedStatement ps = conexao.prepareStatement("SELECT * FROM `periodicos` WHERE `id` = ?");
		ps.setInt(1, idObra);
		ResultSet resultado = ps.executeQuery();
		if (resultado.first()) {
			throw new ExcecaoParametrosIncorretos("Já existe uma obra com este ID p");
		}

		final String[] TABELAS = {"livros", "midias", "academicos"};
		for (String nomeTabela : TABELAS) {
			String sql = String.format("SELECT * FROM `%s` WHERE `id-obra` = ?", nomeTabela);
			ps = conexao.prepareStatement(sql);
			ps.setInt(1, idObra);
			resultado = ps.executeQuery();
			if (resultado.first()) {
				throw new ExcecaoParametrosIncorretos("Já existe uma obra com este ID");
			}
		}

		ps.close();

	}

}
