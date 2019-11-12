package diario.professores_disciplinas.Repository;

import diario.professores_disciplinas.Model.ProfessoresDisciplinasModel;
import diario.professores_disciplinas.View.ExcecaoConteudoVinculado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ProfessoresDisciplinasRepository {

	private Connection con;

	public ProfessoresDisciplinasRepository(Connection con) {
		this.con = con;
	}

	public void deletar(String id, int v) throws SQLException, ExcecaoConteudoVinculado {
		String sql = "";
		ResultSet verificacao;
		PreparedStatement stat = con.prepareCall("Init");
		int idP = Integer.parseUnsignedInt(id);
		if (v == 1) {
			stat = con.prepareCall("SELECT * FROM `prof_disciplinas` WHERE `id-professores` = ?");
			stat.setInt(1, idP);
			verificacao = stat.executeQuery();
			if (!verificacao.next()) {
				throw new ExcecaoConteudoVinculado("Não existe esse professor.");
			}
			verificacao.close();
			sql = "DELETE FROM `prof_disciplinas` WHERE `id-professores` = ?";
		} else if (v == 2) {
			stat = con.prepareCall("SELECT * FROM `prof_disciplinas` WHERE `id-disciplinas` = ?");
			stat.setInt(1, idP);
			verificacao = stat.executeQuery();
			if (!verificacao.next()) {
				throw new ExcecaoConteudoVinculado("Não existe essa disciplina.");
			}
			verificacao.close();
			sql = "DELETE FROM `prof_disciplinas` WHERE `id-disciplinas` = ?";
		}
		stat = con.prepareStatement(sql);
		stat.setInt(1, idP);
		stat.executeUpdate();
	}

	public boolean inserir(Map<String, String> valores) throws NumberFormatException, SQLException, ExcecaoConteudoVinculado {

		ResultSet result;
		if (valores.get("id-professores") == null) {
			throw new ExcecaoConteudoVinculado("O siape do professor é obrigatório.");
		}
		if (valores.get("id-professores") == null) {
			throw new ExcecaoConteudoVinculado("O siape do professor é obrigatório.");
		}
		int idProfessor = Integer.parseUnsignedInt(valores.get("id-professores"));
		int idDisciplinas = Integer.parseUnsignedInt(valores.get("id-disciplinas"));

		PreparedStatement ps = con.prepareStatement("SELECT  * FROM `professores` WHERE `id` = ? ");
		ps.setInt(1, idProfessor);
		result = ps.executeQuery();
		if (!result.next()) {
			throw new ExcecaoConteudoVinculado("Não existe esse professor cadastrado.");
		}

		ps = con.prepareStatement("SELECT  * FROM `disciplinas` WHERE `id` = ? ");
		ps.setInt(1, idDisciplinas);
		result = ps.executeQuery();
		if (!result.next()) {
			throw new ExcecaoConteudoVinculado("Não existe essa disciplina cadastrada.");
		}

		ps = con.prepareStatement("INSERT INTO `prof_disciplinas` (`id-professores`, `id-disciplinas`) VALUES (?, ?)");

		ps.setInt(1, idProfessor);
		ps.setInt(2, idDisciplinas);

		int sucesso = ps.executeUpdate();

		return sucesso != 0;

	}

	public Set<ProfessoresDisciplinasModel> consultar(Map<String, String> filtros) throws NumberFormatException, SQLException {
		String sql;
		Set<ProfessoresDisciplinasModel> disciplinaResultado = new LinkedHashSet<>();
		sql = "SELECT * FROM `prof_disciplinas` ORDER BY `id-professores`";
		int idProfessor = -1, idDisciplina = -1;

		if (filtros.containsKey("id-professores")) {
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
			idProfessor = Integer.parseUnsignedInt(filtros.get("id-professores"));
		}

		if (filtros.containsKey("id-disciplinas")) {
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem
			idDisciplina = Integer.parseUnsignedInt(filtros.get("id-disciplinas"));
		}

		ResultSet resultadoBusca = con.prepareCall(sql).executeQuery();

		boolean adicionar;
		while (resultadoBusca.next()) {
			adicionar = true;

			ProfessoresDisciplinasModel disciplina = resultSetParaDisciplina(resultadoBusca);
			if (filtros.containsKey("id-professores")) {
				if (idProfessor != disciplina.getIdProfessor()) {
					adicionar = false;
				}
			}
			if (filtros.containsKey("id-disciplinas")) {
				if (idDisciplina != disciplina.getIdDisciplina()) {
					adicionar = false;
				}
			}
			if (adicionar) {
				disciplinaResultado.add(disciplina);
			}
		}

		return disciplinaResultado;
	}

	private ProfessoresDisciplinasModel resultSetParaDisciplina(ResultSet res) throws SQLException {
		int idProfessor = res.getInt("id-professores");
		int idDisciplinas = res.getInt("id-disciplinas");

		return new ProfessoresDisciplinasModel(idProfessor, idDisciplinas);
	}

	public ProfessoresDisciplinasModel consultarId(String idStr, int v) throws NumberFormatException, SQLException {

		if (v == 1) {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM `prof_disciplinas` WHERE `id-professores` = ?");
			// Se id não for um inteiro sem sinal, joga a exceção NumberFormatException
			int id = Integer.parseUnsignedInt(idStr);

			ps.setInt(1, id);

			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				return resultSetParaDisciplina(resultado);
			}
		} else {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM `prof_disciplinas` WHERE `id-disciplinas` = ?");
			// Se id não for um inteiro sem sinal, joga a exceção NumberFormatException
			int id = Integer.parseUnsignedInt(idStr);

			ps.setInt(1, id);

			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				return resultSetParaDisciplina(resultado);
			}
		}

		return null;
	}

}
