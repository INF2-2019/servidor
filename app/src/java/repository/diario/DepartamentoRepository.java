package repository.diario;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import model.diario.departamentos.Departamento;
import utils.ConnectionFactory;

public class DepartamentoRepository {
	
	public static List<Departamento> consulta()
			throws SQLException {
		Connection con = ConnectionFactory.getDiario();
		if(con == null) throw new SQLException();
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM departamentos");

		List<Departamento> deptos = new LinkedList();

		while(rs.next()) {
			deptos.add(new Departamento(rs.getInt("id"), rs.getInt("id-campi"), rs.getString("nome")));
		}

		return deptos;
	}
	
}
