package diario.professores;

import utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

/**
 * <h1>ProfessorRepository</h1>
 * Classe que isola o acesso à tabela "professores"
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class ProfessoresBD {

	public static List<Professor> getProfessores() {
		try {
			return consultar("");
		} catch (SQLException e) {
			return null;
		}
	}

	public static List<Professor> consultar(String condicao) throws SQLException {

		Connection conexao = ConnectionFactory.getDiario();
		if (conexao == null) {
			throw new SQLException();
		}

		String sqlQuery = "SELECT * FROM `professores`" + condicao;

		ResultSet rs = conexao.createStatement().executeQuery(sqlQuery);
		List professores = new LinkedList();

		while (rs.next()) {
			professores.add(new Professor(rs.getInt("id"), rs.getInt("id-depto"),
					rs.getString("nome"), rs.getString("senha"), rs.getString("email"),
					rs.getString("titulacao").toUpperCase().charAt(0))
			);
		}

		return professores;
	}

	public static void inserir(Professor professor) throws SQLException {

		Connection conexao = ConnectionFactory.getDiario();
		if (conexao == null) {
			throw new SQLException();
		}

		conexao.createStatement().executeUpdate(
				"INSERT INTO `professores` VALUES ("
				+ professor.getIdSiape() + ", " + professor.getIdDepto() + ", "
				+ professor.getNome() + ", " + professor.getEmail() + ", "
				+ professor.getSenha() + ", " + professor.getTitulacao() + ")"
		);

	}

	// Como email é o único campo com propriedade 'unique', é usado para referenciar o professor.
	// Esta função é projetada para ser capaz de alterar somente os campos necessários, por isso o Map.
	public static void atualizar(String emailCampoAtualizado, Map<String, String> camposAtualizados)
			throws SQLException {

		Connection conexao = ConnectionFactory.getDiario();
		if (conexao == null) {
			throw new SQLException();
		}

		String sqlQuery = "UPDATE `professores` SET ";
		for (Map.Entry<String, String> iterador : camposAtualizados.entrySet()) {
			sqlQuery += "`" + iterador.getKey() + "` = " + iterador.getValue() + " ";
		}
		sqlQuery += "WHERE `email` = `" + emailCampoAtualizado + "`";

		conexao.createStatement().executeUpdate(sqlQuery);

	}

	public static void deletar(String emailCampoDeletado) throws SQLException {

		Connection conexao = ConnectionFactory.getDiario();
		if (conexao == null) {
			throw new SQLException();
		}

		conexao.createStatement().executeUpdate(
				"DELETE FROM `professores` WHERE `email` = '" + emailCampoDeletado + "'");

	}

}
