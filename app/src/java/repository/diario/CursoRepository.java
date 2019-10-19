package repository.diario;

import model.diario.curso.Curso;

import java.sql.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CursoRepository {
	private Connection con;

	public CursoRepository(Connection conexao) {
		this.con = conexao;
	}

	public Set<Curso> realizarBusca(Map<String, String> filtros) throws NumberFormatException, SQLException {
		String sql = "SELECT * FROM `cursos` WHERE ";
		Set<Curso> cursosResultado = new HashSet<>();
		boolean jaAdicionado = false;

		if(filtros.containsKey("id-depto")){
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
			// não preciso usar o valor do parse se der certo
			Integer.parseUnsignedInt(filtros.get("id-depto"));
		}

		if(filtros.containsKey("horas")){
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
			// não preciso usar o valor do parse se der certo
			Integer.parseUnsignedInt(filtros.get("horas"));
		}

		for (Map.Entry<String, String> filtro : filtros.entrySet()) {
			if (jaAdicionado) {
				sql += "AND (`" + filtro.getKey() + "` = '"+ filtro.getValue() +"') ";
			} else {
				sql += "(`" + filtro.getKey() + "` = '"+ filtro.getValue() +"') ";
				jaAdicionado = true;
			}
		}

		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet resultadoBusca = ps.executeQuery();

		while(resultadoBusca.next()){
			cursosResultado.add(converterResultSetParaCurso(resultadoBusca));
		}

		return cursosResultado;
	}

	public Curso converterResultSetParaCurso(ResultSet res) throws SQLException {
		int id = res.getInt("id");
		int id_depto = res.getInt("id-depto");
		String nome = res.getString("nome");
		int horas_total = res.getInt("horas-total");
		String modalidade = res.getString("modalidade");

		return new Curso(id, id_depto, nome, horas_total, modalidade);
	}

}
