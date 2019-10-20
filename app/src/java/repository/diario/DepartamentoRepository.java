package repository.diario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import model.diario.DepartamentoModel;
import utils.ConnectionFactory;

public class DepartamentoRepository {

	public static List<DepartamentoModel> consulta()
			throws SQLException {
		Connection con = ConnectionFactory.getDiario();
		if(con == null) throw new SQLException();

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM departamentos");

		List<DepartamentoModel> deptos = new LinkedList();

		while(rs.next()) {
			deptos.add(new DepartamentoModel(rs.getInt("id"), rs.getInt("id-campi"), rs.getString("nome")));
		}

		return deptos;
	}

	public static void atualiza(DepartamentoModel depto)
			throws SQLException {
		Connection con = ConnectionFactory.getDiario();
		if(con == null) throw new SQLException();

		Statement stmt = con.createStatement();
		stmt.executeUpdate (
				"UPDATE `departamentos`"
				+ " SET `id-campi` = " + depto.getIdCampi() + ", `nome` = '" + depto.getNome() + "'"
				+ " WHERE `id` = " + depto.getId()
			);

	}
	
	public static void insere(DepartamentoModel depto)
            throws SQLException {
        Connection con = ConnectionFactory.getDiario();
        if(con == null) throw new SQLException();

        Statement stmt = con.createStatement();
        stmt.executeUpdate (
                "INSERT INTO `departamentos` (`id-campi`, `nome`)"
                + " VALUES (" + depto.getIdCampi() + ", '" + depto.getNome() + "')"
        );
    }

    public static void remove(int id)
            throws SQLException {
        Connection con = ConnectionFactory.getDiario();
        if(con == null) throw new SQLException();

        Statement stmt = con.createStatement();
        stmt.executeUpdate (
                "DELETE FROM `departamentos`"
                + " WHERE `id` = " + id
        );
    }

}
