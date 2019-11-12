/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.descartes;

import biblioteca.descartes.views.ExcecaoParametroIncorreto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author juanr
 */
public class DescartesRepository {
	protected Connection conexao;

	public DescartesRepository(Connection conexao) {
		this.conexao = conexao;
	}

	public boolean inserir(DescartesModel modelo) throws SQLException, ExcecaoParametroIncorreto {
		if (!acervoExiste(modelo.getIdAcervo())) {
			throw new ExcecaoParametroIncorreto("Acervo com id " + modelo.getIdAcervo() + " não existe!");
		}

		String query = "INSERT INTO descartes(`id-acervo`,`data-descarte`, motivos, operador) VALUES (?,?,?,?)";
		PreparedStatement st = conexao.prepareStatement(query);
		st.setInt(1, modelo.getIdAcervo());
		st.setDate(2, modelo.getDataDescarte());
		st.setString(3, modelo.getMotivos());
		st.setString(4, modelo.getOperador());

		int r = st.executeUpdate();
		st.close();

		if (r != 0) {
			return removeAcervo(modelo.getIdAcervo());
		}

		return false;
	}

	public ArrayList consultar(Integer idAcervo) throws SQLException {
		String query = "SELECT * FROM descartes";

		if (idAcervo != null) {
			query += " WHERE `id-acervo`=?";
		}

		PreparedStatement st = conexao.prepareStatement(query);

		if (idAcervo != null) {
			st.setInt(1, idAcervo);
		}

		ResultSet resultado = st.executeQuery();

		ArrayList<DescartesModel> lista = new ArrayList<>();
		while (resultado.next()) {
			DescartesModel modelo = new DescartesModel(resultado.getInt("id-acervo"), resultado.getDate("data-descarte"), resultado.getString("motivos"), resultado.getString("operador"));
			lista.add(modelo);
		}

		st.close();
		return lista;
	}

	public boolean acervoExiste(int idAcervo) throws SQLException {
		// Código baseado em: https://stackoverflow.com/questions/867194/java-resultset-how-to-check-if-there-are-any-results/6813771#6813771
		String query = "SELECT * FROM acervo WHERE id=?";

		PreparedStatement st = conexao.prepareStatement(query);
		st.setInt(1, idAcervo);
		ResultSet resultado = st.executeQuery();


		return resultado.isBeforeFirst();
	}

	public boolean removeAcervo(int idAcervo) throws SQLException {
		String query = "DELETE FROM acervo WHERE id=?";

		PreparedStatement st = conexao.prepareStatement(query);
		st.setInt(1, idAcervo);
		int resultado = st.executeUpdate();


		return resultado != 0;
	}
}
