package repository.diario;

import model.diario.CursoModel;

import javax.servlet.http.HttpServletRequest;
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
		// Base da query SQL
		String sql;
		Set<CursoModel> cursosResultado = new HashSet<>();
		boolean jaAdicionado = false;

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

		PreparedStatement ps = con.prepareStatement(sql);
		System.out.println(ps);
		ResultSet resultadoBusca = ps.executeQuery();

		// Itera por cada item do resultado e adiciona nos resultados
		while(resultadoBusca.next()){
			cursosResultado.add(resultSetParaCurso(resultadoBusca));
		}

		return cursosResultado;
	}

	public boolean deletar(Map<String, String> filtros) throws NumberFormatException, SQLException {
		String sql = "DELETE FROM `cursos` WHERE ";
		boolean jaAdicionado = false;

		if (filtros.containsKey("id-depto"))
			Integer.parseUnsignedInt(filtros.get("id-depto"));


		if (filtros.containsKey("horas-total"))
			Integer.parseUnsignedInt(filtros.get("horas-total"));

		for (Map.Entry<String, String> filtro : filtros.entrySet()) {
			if (jaAdicionado) {
				sql += "AND (`" + filtro.getKey() + "` = '" + filtro.getValue() + "') ";
			} else {
				sql += "(`" + filtro.getKey() + "` = '" + filtro.getValue() + "') ";
				jaAdicionado = true;
			}
		}

		PreparedStatement ps = con.prepareStatement(sql);
		int sucesso = ps.executeUpdate();

		// Se deletou algo, retorna true, senão retorna false
		return sucesso != 0;

	}

	public boolean inserir(Map<String, String> valores) throws NumberFormatException, SQLException {
		// Tem que ter os 4 valores a serem inseridos no BD
		if(valores.size() != 4)
			return false;

		int id_depto = 0;

		if (valores.containsKey("id-depto"))
			id_depto = Integer.parseUnsignedInt(valores.get("id-depto"));

		int horas_total = 0;

		if (valores.containsKey("horas-total"))
			horas_total = Integer.parseUnsignedInt(valores.get("horas-total"));

		PreparedStatement ps = con.prepareStatement("INSERT INTO `cursos` (`id-depto`, `nome`, `horas-total`, `modalidade`) VALUES (?, ?, ?, ?)");

		ps.setInt(1, id_depto);
		ps.setString(2, valores.get("nome"));
		ps.setInt(3, horas_total);
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

	public Map<String, String> definirMap(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();

		// definir os valores do map condicionalmente, conforme a requisição
		if (req.getParameter("departamento") != null) {
			dados.put("id-depto", req.getParameter("departamento"));
		}

		if (req.getParameter("nome") != null) {
			dados.put("nome", req.getParameter("nome"));
		}

		if (req.getParameter("horas") != null) {
			dados.put("horas-total", req.getParameter("horas"));
		}

		if (req.getParameter("modalidade") != null) {
			dados.put("modalidade", req.getParameter("modalidade"));
		}

		return dados;
	}

}
