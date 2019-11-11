
package diario.professores_disciplinas.Repository;

import diario.professores_disciplinas.Model.ProfessoresDisciplinasModel;
import diario.professores_disciplinas.View.ExcecaoConteudoVinculado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class ProfessoresDisciplinasRepository {
    
    private Connection con;

	public ProfessoresDisciplinasRepository(Connection con) {
		this.con = con;
	}

	public void deletar(String id, int v) throws SQLException, ExcecaoConteudoVinculado {
		String sql;
                ResultSet verificacao;
                PreparedStatement stat = con.prepareCall("Init");
                int idP = Integer.parseUnsignedInt(id);
                if(v==1){
                    stat = con.prepareCall("SELECT * FROM `professores-discplinas` WHERE `id-professores` = ?");
                    stat.setInt(1, idP);
                    verificacao = stat.executeQuery();
                    if (verificacao.next()) {
                            throw new ExcecaoConteudoVinculado("Não existe esse professor.");
                    }
                    verificacao.close();
                }
                else if(v==2){
                    int idDi = Integer.parseUnsignedInt(id);
                    stat = con.prepareCall("SELECT * FROM `professores-discplinas` WHERE `id-disciplinas` = ?");
                    stat.setInt(1, idP);
                    verificacao = stat.executeQuery();
                    if (verificacao.next()) {
                            throw new ExcecaoConteudoVinculado("Não existe essa disciplina.");
                    }
                    verificacao.close();
                }
                
		sql = "DELETE FROM `disciplinas` WHERE `id` = ?";

		stat = con.prepareStatement(sql);
		stat.setInt(1, idP);
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

	public Set<ProfessoresDisciplinasModel> consultar(Map<String, String> filtros) throws NumberFormatException, SQLException {
		String sql;
		Set<ProfessoresDisciplinasModel> disciplinaResultado = new LinkedHashSet<>();
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

			ProfessoresDisciplinasModel disciplina = resultSetParaDisciplina(resultadoBusca);
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

	private ProfessoresDisciplinasModel resultSetParaDisciplina(ResultSet res) throws SQLException {
		int id = res.getInt("id");
		int idTurmas = res.getInt("id-turmas");
		String nome = res.getString("nome");
		int cargaHorariaMin = res.getInt("carga-horaria-min");

		return new DisciplinaModel(id, idTurmas, nome, cargaHorariaMin);
	}

	public ProfessoresDisciplinasModel consultarId(String idStr) throws NumberFormatException, SQLException {
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
