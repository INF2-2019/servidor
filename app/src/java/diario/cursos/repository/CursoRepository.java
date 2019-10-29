package diario.cursos.repository;

import diario.cursos.models.CursoModel;
import diario.cursos.view.ExcecaoTurmaVinculada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CursoRepository {

	private Connection con;

	public CursoRepository(Connection conexao) {
		this.con = conexao;
	}

	public Set<CursoModel> consultar(Map<String, String> filtros) throws NumberFormatException, SQLException {
		Set<CursoModel> cursosResultado = new LinkedHashSet<>();
		String sql = "SELECT * FROM `cursos` ORDER BY `id`";
		int idDepto = -1, horasTotal = -1;

		if (filtros.containsKey("id-depto")) {
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
			idDepto = Integer.parseUnsignedInt(filtros.get("id-depto"));
		}

		if (filtros.containsKey("horas-total")) {
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem
			horasTotal = Integer.parseUnsignedInt(filtros.get("horas-total"));
		}

		ResultSet resultadoBusca = con.prepareCall(sql).executeQuery();

		// Itera por cada item do resultado e adiciona nos resultados
		boolean adicionar;
		while (resultadoBusca.next()) {
			adicionar = true;

			CursoModel curso = resultSetParaCurso(resultadoBusca);
			if (filtros.containsKey("id-depto")) {
				if (idDepto != curso.getIdDepto()) {
					adicionar = false;
				}
			}
			if (filtros.containsKey("nome")) {
				if (!filtros.get("nome").equals(curso.getNome())) {
					adicionar = false;
				}
			}
			if (filtros.containsKey("horas-total")) {
				if (horasTotal != curso.getHorasTotal()) {
					adicionar = false;
				}
			}
			if (filtros.containsKey("modalidade")) {
				if (!filtros.get("modalidade").equals(curso.getModalidade())) {
					adicionar = false;
				}
			}

			if (adicionar) {
				cursosResultado.add(curso);
			}
		}

		return cursosResultado;
	}

	public CursoModel consultarId(String idStr) throws NumberFormatException, SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM `cursos` WHERE `id` = ?");
		// Se id não for um inteiro sem sinal, joga a exceção NumberFormatException
		int id = Integer.parseUnsignedInt(idStr);

		ps.setInt(1, id);

		ResultSet resultado = ps.executeQuery();

		if (resultado.next()) {
			return resultSetParaCurso(resultado);
		}

		return null;

	}

	public boolean deletar(String idStr) throws NumberFormatException, ExcecaoTurmaVinculada, SQLException {
		PreparedStatement ps = con.prepareStatement("DELETE FROM `cursos` WHERE `id` = ?");
		// Se id não for um inteiro sem sinal, joga a exceção NumberFormatException
		int id = Integer.parseUnsignedInt(idStr);

		ResultSet verificacao = con.prepareCall("SELECT * FROM `turmas` WHERE `id-cursos` = "+id).executeQuery();
		if(verificacao.next()) {
			throw new ExcecaoTurmaVinculada();
		}
		verificacao.close();

		ps.setInt(1, id);

		int sucesso = ps.executeUpdate();

		// Se deletou algo, retorna true, senão retorna false
		return sucesso != 0;

	}

	public boolean inserir(Map<String, String> valores) throws NumberFormatException, SQLException {
		// Tem que ter os 4 valores a serem inseridos no BD
		if (valores.size() != 4) {
			return false;
		}

		int idDepto = 0;

		if (valores.containsKey("id-depto")) {
			idDepto = Integer.parseUnsignedInt(valores.get("id-depto"));
		}

		int horasTotal = 0;

		if (valores.containsKey("horas-total")) {
			horasTotal = Integer.parseUnsignedInt(valores.get("horas-total"));
		}

		PreparedStatement ps = con.prepareStatement("INSERT INTO `cursos` (`id-depto`, `nome`, `horas-total`, `modalidade`) VALUES (?, ?, ?, ?)");

		ps.setInt(1, idDepto);
		ps.setString(2, valores.get("nome"));
		ps.setInt(3, horasTotal);
		ps.setString(4, valores.get("modalidade"));

		int sucesso = ps.executeUpdate();

		return sucesso != 0;

	}

	public boolean atualizarPorId(Map<String, Object> parametros) throws NumberFormatException, NullPointerException, SQLException {
		int id = Integer.parseUnsignedInt(parametros.get("id").toString());
		int idDepto = Integer.parseUnsignedInt(parametros.get("id-depto").toString());
		int horasTotal = Integer.parseUnsignedInt(parametros.get("horas-total").toString());

		PreparedStatement ps = con.prepareStatement("UPDATE `cursos` SET `id-depto` = ?, `nome` = ?, `horas-total` = ?, `modalidade` = ? WHERE `id` = ?");
		ps.setInt(1, idDepto);
		ps.setString(2, (String) parametros.get("nome"));
		ps.setInt(3, horasTotal);
		ps.setString(4, (String) parametros.get("modalidade"));
		ps.setInt(5, id);

		int sucesso = ps.executeUpdate();

		return sucesso != 0;
	}

	public boolean atualizar(Map<String, String> parametros) throws NumberFormatException, SQLException {
		int id = Integer.parseUnsignedInt(parametros.get("id"));

		if (parametros.containsKey("id-depto")) {
			Integer.parseUnsignedInt(parametros.get("id-depto"));
		}

		if (parametros.containsKey("horas-total")) {
			Integer.parseUnsignedInt(parametros.get("horas-total"));
		}

		CursoModel curso = consultarId(Integer.toString(id));
		if(curso == null)
			throw new NullPointerException("Id inválido");
		Object[] vals = curso.retornarValoresRestantes(parametros);
		String[] keys = {"id", "id-depto", "nome", "horas-total", "modalidade"};
		Map<String, Object> valores = new LinkedHashMap<>();

		for (int i = 0; i < keys.length; i++) {
			valores.put(keys[i], vals[i]);
		}

		return atualizarPorId(valores);
	}

	private CursoModel resultSetParaCurso(ResultSet res) throws SQLException {
		int id = res.getInt("id");
		int id_depto = res.getInt("id-depto");
		String nome = res.getString("nome");
		int horas_total = res.getInt("horas-total");
		String modalidade = res.getString("modalidade");

		return new CursoModel(id, id_depto, nome, horas_total, modalidade);
	}

}
