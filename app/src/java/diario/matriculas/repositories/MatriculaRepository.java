package diario.matriculas.repositories;

import diario.matriculas.models.Matricula;

import java.sql.*;
import java.util.*;

public class MatriculaRepository {

	public final static Set<String> validFilters = new HashSet<>(Arrays.asList("id", "id-alunos", "id-disciplinas", "ano", "ativo"));
	private Connection connection;

	public MatriculaRepository(Connection connection) {
		this.connection = connection;
	}

	public int definirAtividade(int id, boolean ativo) throws SQLException {
		String query = "UPDATE matriculas SET ativo = ? WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setBoolean(1, ativo);
		ps.setInt(2, id);
		return ps.executeUpdate();
	}

	public int updateMatricula(Matricula m) throws SQLException {
		String query = "UPDATE matriculas SET " +
			"`id-disciplinas` = COALESCE(?, matriculas.`id-disciplinas`), " +
			"`id-alunos` = COALESCE(?, matriculas.`id-alunos`), " +
			"`ano` = COALESCE(?, matriculas.ano), " +
			"`ativo` = COALESCE(?, matriculas.ativo) " +
			"WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(query);

		if (m.getIdDisciplinas() == null) {
			ps.setNull(1, Types.INTEGER);
		} else {
			ps.setInt(1, m.getIdDisciplinas());
		}

		if (m.getIdAlunos() == null) {
			ps.setNull(2, Types.BIGINT);
		} else {
			ps.setLong(2, m.getIdAlunos());
		}

		if (m.getAno() == null) {
			ps.setNull(3, Types.INTEGER);
		} else {
			ps.setInt(3, m.getAno());
		}

		if (m.isAtivo() == null) {
			ps.setNull(4, Types.BOOLEAN);
		} else {
			ps.setBoolean(4, m.isAtivo());
		}

		ps.setInt(5, m.getId());

		return ps.executeUpdate();
	}

	public void deleteMatricula(int id) throws SQLException {
		String query = "DELETE matriculas, diario FROM matriculas INNER JOIN diario ON diario.`id-matriculas` = matriculas.id WHERE `id` = ?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, id);
		ps.execute();
	}

	public void insertMatricula(Matricula matricula) throws SQLException {
		// Criar a matrícula
		String query = "INSERT INTO matriculas (`id-alunos`, `id-disciplinas`, ano, ativo) VALUES (?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		ps.setLong(1, matricula.getIdAlunos());
		ps.setInt(2, matricula.getIdDisciplinas());
		ps.setInt(3, matricula.getAno());
		ps.setBoolean(4, matricula.isAtivo());


		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();

		if (!rs.next()) {
			throw new SQLException("A matrícula não foi criada!");
		}

		// Pegar id da matrícula

		matricula.setId(rs.getInt(1));

		// Gerar o diário

		createDiario(matricula);
	}

	public List<Matricula> getFilteredMatriculas(Map<String, Object> filters) throws SQLException {
		StringBuilder query = new StringBuilder("SELECT * FROM matriculas");

		int pos = 1;
		if (filters.size() > 0) {
			query.append(" WHERE");
		}

		for (String key : filters.keySet()) {
			if (pos > 1) {
				query.append(" AND");
			}
			query.append(" `").append(key).append("` = ?");
			pos++;
		}

		PreparedStatement ps = connection.prepareStatement(query.toString());

		pos = 1;

		for (String key : filters.keySet()) {
			if (key.equals("id") || key.equals("id-disciplinas") || key.equals("ano")) {
				ps.setInt(pos, (Integer) filters.get(key));
			}

			if (key.equals("id-alunos")) {
				ps.setLong(pos, (Long) filters.get(key));
			}

			if (key.equals("ativo")) {
				ps.setBoolean(pos, (Boolean) filters.get(key));
			}

			pos++;
		}

		ResultSet rs = ps.executeQuery();

		List<Matricula> responseData = new LinkedList<>();

		while (rs.next()) {
			responseData.add(resultSetToMatricula(rs));
		}

		return responseData;
	}

	private void createDiario(Matricula matricula) throws SQLException {
		String query;
		PreparedStatement ps;
		ResultSet rs;

		// Obter os conteudos da disciplina
		query = "SELECT * FROM conteudos WHERE `id-disciplinas` = ?";
		ps = connection.prepareStatement(query);
		ps.setInt(1, matricula.getIdDisciplinas());
		rs = ps.executeQuery();

		// Desabilitar o auto-commit para otimizar o tráfego
		connection.setAutoCommit(false);

		// Inserir elemento a elemento no diário
		query = "INSERT INTO diario (`id-conteudos`, `id-matriculas`, faltas, nota) VALUES (?, ?, 0, 0.0)";
		ps = connection.prepareStatement(query);
		ps.setInt(2, matricula.getId());

		while (rs.next()) {
			ps.setInt(1, rs.getInt("id"));
			ps.execute();
		}

		// Commitar e habilitar o auto-commit
		connection.commit();
		connection.setAutoCommit(true);
	}

	private Matricula resultSetToMatricula(ResultSet rs) throws SQLException {
		return new Matricula(
			rs.getInt("id"),
			rs.getLong("id-alunos"),
			rs.getInt("id-disciplinas"),
			rs.getInt("ano"),
			rs.getBoolean("ativo")
		);
	}

	// Métodos possivelmente uteis depois

	public Matricula getMatriculaById(int id) throws SQLException, NoSuchElementException {
		String query = "SELECT * FROM matriculas WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return resultSetToMatricula(rs);
		}

		throw new NoSuchElementException(String.format("Não foi encontra matrícula de ID %d", id));
	}

	public List<Matricula> getMatriculasByAluno(long idAluno) throws SQLException {
		String query = "SELECT * FROM matriculas WHERE `id-alunos` = ?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, idAluno);
		ResultSet rs = ps.executeQuery();
		List<Matricula> responseData = new LinkedList<>();

		while (rs.next()) {
			responseData.add(resultSetToMatricula(rs));
		}

		return responseData;
	}

	public List<Matricula> getMatriculasByDisciplina(int idDisciplina) throws SQLException {
		String query = "SELECT * FROM matriculas WHERE `id-disciplinas` = ?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, idDisciplina);
		ResultSet rs = ps.executeQuery();
		List<Matricula> responseData = new LinkedList<>();

		while (rs.next()) {
			responseData.add(resultSetToMatricula(rs));
		}

		return responseData;
	}
}
