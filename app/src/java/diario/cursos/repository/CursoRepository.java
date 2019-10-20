package diario.cursos.repository;

import diario.cursos.models.CursoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class CursoRepository {
	private Connection con;

	public CursoRepository(Connection conexao) {
		this.con = conexao;
	}

	public Set<CursoModel> consultar(Map<String, String> filtros) throws NumberFormatException, SQLException {
		Set<CursoModel> cursosResultado = new LinkedHashSet<>();
		boolean jaAdicionado = false;
		String sql;

		if(filtros.isEmpty()) {
			sql = "SELECT * FROM `cursos`";
		} else {
			sql = "SELECT * FROM `cursos` WHERE ";

			if (filtros.containsKey("id-depto")) {
				// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
				// não preciso usar o valor do parse se der certo
				Integer.parseUnsignedInt(filtros.get("id-depto"));
			}

			if (filtros.containsKey("horas-total")) {
				// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
				// não preciso usar o valor do parse se der certo
				Integer.parseUnsignedInt(filtros.get("horas-total"));
			}

			// Montar a query SQL com base nos filtros passados no Map
			for (Map.Entry<String, String> filtro : filtros.entrySet()) {
				if (jaAdicionado) {
					sql += "AND (`" + filtro.getKey() + "` = '" + filtro.getValue() + "') ";
				} else {
					sql += "(`" + filtro.getKey() + "` = '" + filtro.getValue() + "') ";
					jaAdicionado = true;
				}
			}
		}
		sql += " ORDER BY `id`";

		ResultSet resultadoBusca = con.prepareCall(sql).executeQuery();

		// Itera por cada item do resultado e adiciona nos resultados
		while(resultadoBusca.next()){
			cursosResultado.add(resultSetParaCurso(resultadoBusca));
		}

		return cursosResultado;
	}

	public boolean deletar(String idStr) throws NumberFormatException, SQLException {
		PreparedStatement ps = con.prepareStatement("DELETE FROM `cursos` WHERE `id` = ?");
		// Se id não for um inteiro sem sinal, joga a exceção NumberFormatException
		int id = Integer.parseUnsignedInt(idStr);

		ps.setInt(1, id);

		int sucesso = ps.executeUpdate();

		// Se deletou algo, retorna true, senão retorna false
		return sucesso != 0;

	}

	public boolean inserir(Map<String, String> valores) throws NumberFormatException, SQLException {
		// Tem que ter os 4 valores a serem inseridos no BD
		if(valores.size() != 4)
			return false;

		int idDepto = 0;

		if (valores.containsKey("id-depto"))
			idDepto = Integer.parseUnsignedInt(valores.get("id-depto"));

		int horasTotal = 0;

		if (valores.containsKey("horas-total"))
			horasTotal = Integer.parseUnsignedInt(valores.get("horas-total"));

		PreparedStatement ps = con.prepareStatement("INSERT INTO `cursos` (`id-depto`, `nome`, `horas-total`, `modalidade`) VALUES (?, ?, ?, ?)");

		ps.setInt(1, idDepto);
		ps.setString(2, valores.get("nome"));
		ps.setInt(3, horasTotal);
		ps.setString(4, valores.get("modalidade"));

		int sucesso = ps.executeUpdate();

		return sucesso != 0;

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
