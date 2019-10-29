package diario.disciplinas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import diario.disciplinas.model.DisciplinaModel;
import java.util.SortedMap;
import diario.disciplinas.views.ExcecaoConteudoVinculado;

public class DisciplinaRepository {

	private Connection con;

	public DisciplinaRepository(Connection con) {
		this.con = con;
	}

	public void deletar(String id) throws SQLException, ExcecaoConteudoVinculado {
		String sql;
		int idParsed = Integer.parseUnsignedInt(id);
		PreparedStatement stat = con.prepareCall("SELECT * FROM `conteudos` WHERE `id-disciplinas` = ?");
		stat.setInt(1, idParsed);
		ResultSet verificacao = stat.executeQuery();
		if (verificacao.next()) {
			throw new ExcecaoConteudoVinculado();
		}
		verificacao.close();

		sql = "DELETE FROM `disciplinas` WHERE `id` = ?";

		stat = con.prepareStatement(sql);
		stat.setInt(1, idParsed);
		stat.executeUpdate();
	}

	public boolean inserir(Map<String, String> valores) throws NumberFormatException, SQLException {

		if (valores.size() != 3) {
			return false;
		}

		int idTurmas = 0;

		if (valores.containsKey("id-turmas")) {
			idTurmas = Integer.parseUnsignedInt(valores.get("id-turmas"));
		}

		int cargaHorariaMin = 0;

		if (valores.containsKey("carga-horaria-min")) {
			cargaHorariaMin = Integer.parseUnsignedInt(valores.get("carga-horaria-min"));
		}

		PreparedStatement ps = con.prepareStatement("INSERT INTO `disciplinas` (`id-turmas`, `nome`, `carga-horaria-min`) VALUES (?, ?, ?)");

		ps.setInt(1, idTurmas);
		ps.setString(2, valores.get("nome"));
		ps.setInt(3, cargaHorariaMin);

		int sucesso = ps.executeUpdate();

		return sucesso != 0;

	}

	public boolean atualizar(SortedMap<String, String> filtros, String id) throws SQLException {
		int idParsed = Integer.parseUnsignedInt(id);
		if (filtros.containsKey("id-turmas")) {
			Integer.parseUnsignedInt(filtros.get("id-turmas"));
		}

		if (filtros.containsKey("carga-horaria-min")) {
			Integer.parseUnsignedInt(filtros.get("carga-horaria-min"));
		}

		DisciplinaModel disciplina = consultarId(Integer.toString(idParsed));
		Object[] vals = disciplina.retornarValoresRestantes(filtros);
		String[] keys = {"id", "id-turmas", "nome", "carga-horaria-min"};
		Map<String, Object> valores = new LinkedHashMap<>();

		for (int i = 0; i < keys.length; i++) {
			valores.put(keys[i], vals[i]);
		}
		return atualizarPorId(valores);
	}

	public boolean atualizarPorId(Map<String, Object> parametros) throws NumberFormatException, SQLException {
		int id = Integer.parseUnsignedInt(parametros.get("id").toString());
		int idTurma = Integer.parseUnsignedInt(parametros.get("id-turmas").toString());
		int cargaHorariaMin = Integer.parseUnsignedInt(parametros.get("carga-horaria-min").toString());

		PreparedStatement ps = con.prepareStatement("UPDATE `disciplinas` SET `id-turmas` = ?, `nome` = ?, `carga-horaria-min` = ?  WHERE `id` = ?");
		ps.setInt(1, idTurma);
		ps.setString(2, (String) parametros.get("nome"));
		ps.setInt(3, cargaHorariaMin);
		ps.setInt(4, id);

		int sucesso = ps.executeUpdate();

		return sucesso != 0;
	}

	public Set<DisciplinaModel> consultar(Map<String, String> filtros) throws NumberFormatException, SQLException {
		String sql;
		Set<DisciplinaModel> disciplinaResultado = new LinkedHashSet<>();
		sql = "SELECT * FROM `disciplinas` ORDER BY `id`";
		int idTurma = -1, horas = -1;

		if (filtros.containsKey("id-turmas")) {
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
			idTurma = Integer.parseUnsignedInt(filtros.get("id-turmas"));
		}

		if (filtros.containsKey("carga-horaria-min")) {
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem
			horas = Integer.parseUnsignedInt(filtros.get("carga-horaria-min"));
		}

		ResultSet resultadoBusca = con.prepareCall(sql).executeQuery();

		boolean adicionar;
		while (resultadoBusca.next()) {
			adicionar = true;

			DisciplinaModel disciplina = resultSetParaDisciplina(resultadoBusca);
			if (filtros.containsKey("id-turmas")) {
				if (idTurma != disciplina.getIdTurmas()) {
					adicionar = false;
				}
			}
			if (filtros.containsKey("nome")) {
				if (!filtros.get("nome").equals(disciplina.getNome())) {
					adicionar = false;
				}
			}
			if (filtros.containsKey("carga-horaria-min")) {
				if (horas != disciplina.getCargaHorariaMin()) {
					adicionar = false;
				}
			}

			if (adicionar) {
				disciplinaResultado.add(disciplina);
			}
		}

		return disciplinaResultado;
	}

	private DisciplinaModel resultSetParaDisciplina(ResultSet res) throws SQLException {
		int id = res.getInt("id");
		int idTurmas = res.getInt("id-turmas");
		String nome = res.getString("nome");
		int cargaHorariaMin = res.getInt("carga-horaria-min");

		return new DisciplinaModel(id, idTurmas, nome, cargaHorariaMin);
	}

	public DisciplinaModel consultarId(String idStr) throws NumberFormatException, SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM `disciplinas` WHERE `id` = ?");
		// Se id não for um inteiro sem sinal, joga a exceção NumberFormatException
		int id = Integer.parseUnsignedInt(idStr);

		ps.setInt(1, id);

		ResultSet resultado = ps.executeQuery();

		if (resultado.next()) {
			return resultSetParaDisciplina(resultado);
		}
		return null;

	}

}
