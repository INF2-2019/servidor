package diario.professores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1> Classe de Validação de Parâmetros </h1>
 * Tem métodos para validação do formato de todos os parâmetros necessários para inclusão/alteração
 * de professores, além de validar se um dado departamento existe
 *
 * @author Jonata Novais Cirqueira
 */
public class Validacao {

	private static final String[] PARAMS = {"id", "id-depto", "nome", "senha", "email", "titulacao"};
	private static final String REGEXP_EMAIL = "[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*";

	public static void validarParametros(Map<String, String[]> parametros)
			throws ExcecaoParametrosIncorretos {
		if (parametros.size() < 6) {
			throw new ExcecaoParametrosIncorretos("Parâmetros insuficientes");
		}
		if (parametros.size() > 6) {
			throw new ExcecaoParametrosIncorretos("Parâmetros em excesso");
		}

		for (Map.Entry<String, String[]> iterador : parametros.entrySet()) {
			boolean parametrosPreenchidos = false;
			for (String param : PARAMS) {
				if (iterador.getKey().equals(param)) {
					parametrosPreenchidos = true;
				}
			}
			if (!parametrosPreenchidos) {
				throw new ExcecaoParametrosIncorretos("Parâmetro desconhecido: " + iterador.getKey());
			}
		}

		// Valida o código SIAPE
		try {
			if (parametros.get("id")[0].length() != 9) {
				throw new NumberFormatException();
			}
			Integer.parseInt(parametros.get("id")[0]);
		} catch (NumberFormatException e) {
			throw new ExcecaoParametrosIncorretos("Parâmetro inválido: 'id' deve ser um número de 9 dígitos");
		}

		// Valida id-depto
		try {
			Integer.parseInt(parametros.get("id-depto")[0]);
		} catch (NumberFormatException e) {
			throw new ExcecaoParametrosIncorretos("Parâmetro inválido: 'id' deve ser um número de 9 dígitos");
		}

		// Valida titulação
		switch (parametros.get("titulacao")[0].toLowerCase()) {
			case "m":
			case "e":
			case "g":
			case "d":
				break;
			default:
				throw new ExcecaoParametrosIncorretos("Parâmetro inválido: 'titulacao' deve ser um dentre 'G', 'E', 'M', 'D'");
		}

		// Valida email com expressão regular (RegEx)
		Matcher m = Pattern.compile(REGEXP_EMAIL).matcher(parametros.get("email")[0]);
		if (!m.matches()) {
			throw new ExcecaoParametrosIncorretos("Email inválido");
		}

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
