package diario.departamentos.repository;

import diario.departamentos.model.Departamento;
import utils.ConnectionFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DepartamentoRepository {

	public static List<Departamento> consulta()
		throws SQLException, DepartamentoInexistenteException {
		Connection con = ConnectionFactory.getDiario();
		if (con != null) {
			List<Departamento> deptos;
			try (Statement stmt = con.createStatement()) {
				ResultSet rs = stmt.executeQuery("SELECT * FROM `departamentos` INNER JOIN `campi` ON departamentos.`id-campi` = campi.id");
				deptos = new LinkedList();
				while (rs.next()) {
					deptos.add(new Departamento(rs.getInt("departamentos.id"), rs.getInt("departamentos.id-campi"), rs.getString("departamentos.nome"), rs.getString("campi.nome"), rs.getString("campi.cidade"), rs.getString("campi.uf")));
				}
			}
			con.close();
			return deptos;
		} else {
			throw new SQLException("Falha ao conectar ao banco de dados");
		}
	}

	public static Departamento consulta(int id)
		throws SQLException, DepartamentoInexistenteException {
		Connection con = ConnectionFactory.getDiario();
		if (con != null) {
			Departamento depto;
			try (PreparedStatement prst = con.prepareStatement("SELECT * FROM `departamentos`, `campi` WHERE `departamentos`.`id` = ? AND `departamentos`.`id-campi` = `campi`.`id`")) {
				prst.setInt(1, id);
				ResultSet rs = prst.executeQuery();
				if (!rs.next()) {
					throw new DepartamentoInexistenteException();
				}
				depto = new Departamento(rs.getInt("departamentos.id"), rs.getInt("departamentos.id-campi"), rs.getString("departamentos.nome"), rs.getString("campi.nome"), rs.getString("campi.cidade"), rs.getString("campi.uf"));
			}
			con.close();

			return depto;
		} else {
			throw new SQLException("Falha ao conectar ao banco de dados");
		}
	}

	public static void insere(int idCampi, String nome) throws SQLException {
		Connection con = ConnectionFactory.getDiario();
		if (con != null) {
			try (PreparedStatement prst = con.prepareStatement("INSERT INTO `departamentos` (`id-campi`, `nome`) VALUES (?, ?)")) {
				prst.setInt(1, idCampi);
				prst.setString(2, nome);
				prst.executeUpdate();
			}
			con.close();
		} else {
			throw new SQLException("Falha ao conectar ao banco de dados");
		}
	}

	public static void insere(Departamento depto) throws SQLException {
		Connection con = ConnectionFactory.getDiario();
		if (con != null) {
			try (PreparedStatement prst = con.prepareStatement("INSERT INTO `departamentos` (`id-campi`, `nome`) VALUES (?, ?)")) {
				prst.setInt(1, depto.getIdCampi());
				prst.setString(2, depto.getNome());
				prst.executeUpdate();
			}
			con.close();
		} else {
			throw new SQLException("Falha ao conectar ao banco de dados");
		}
	}

	public static void atualiza(int id, int idCampi)
		throws SQLException, DepartamentoInexistenteException {
		Connection con = ConnectionFactory.getDiario();
		if (con != null) {
			try (PreparedStatement prst = con.prepareStatement("UPDATE `departamentos` SET `id-campi` = ? WHERE `id` = ?")) {
				prst.setInt(1, idCampi);
				prst.setInt(2, id);
				if (prst.executeUpdate() == 0) {
					throw new DepartamentoInexistenteException();
				}
			}
			con.close();
		} else {
			throw new SQLException("Falha ao conectar ao banco de dados");
		}
	}

	public static void atualiza(int id, String nome)
		throws SQLException, DepartamentoInexistenteException {
		Connection con = ConnectionFactory.getDiario();
		if (con != null) {
			try (PreparedStatement prst = con.prepareStatement("UPDATE `departamentos` SET `nome` = ? WHERE `id` = ?")) {
				prst.setString(1, nome);
				prst.setInt(2, id);
				if (prst.executeUpdate() == 0) {
					throw new DepartamentoInexistenteException();
				}
			}
			con.close();
		} else {
			throw new SQLException("Falha ao conectar ao banco de dados");
		}
	}

	public static void atualiza(int id, int idCampi, String nome)
		throws SQLException, DepartamentoInexistenteException {
		Connection con = ConnectionFactory.getDiario();
		if (con != null) {
			try (PreparedStatement prst = con.prepareStatement("UPDATE `departamentos` SET `id-campi` = ?, `nome` = ? WHERE `id` = ?")) {
				prst.setInt(1, idCampi);
				prst.setString(2, nome);
				prst.setInt(3, id);
				if (prst.executeUpdate() == 0) {
					throw new DepartamentoInexistenteException();
				}
			}
			con.close();
		} else {
			throw new SQLException("Falha ao conectar ao banco de dados");
		}
	}

	public static void atualiza(Departamento depto)
		throws SQLException, DepartamentoInexistenteException {
		Connection con = ConnectionFactory.getDiario();
		if (con != null) {
			try (PreparedStatement prst = con.prepareStatement("UPDATE `departamentos` SET `id-campi` = ?, `nome` = ? WHERE `id` = ?")) {
				prst.setInt(1, depto.getIdCampi());
				prst.setString(2, depto.getNome());
				prst.setInt(3, depto.getId());
				if (prst.executeUpdate() == 0) {
					throw new DepartamentoInexistenteException();
				}
			}
			con.close();
		} else {
			throw new SQLException("Falha ao conectar ao banco de dados");
		}
	}

	public static void remove(int id)
		throws SQLException, DepartamentoInexistenteException {
		Connection con = ConnectionFactory.getDiario();
		if (con != null) {
			try (PreparedStatement prst = con.prepareStatement("DELETE FROM `departamentos` WHERE `id` = ?")) {
				prst.setInt(1, id);
				if (prst.executeUpdate() == 0) {
					throw new DepartamentoInexistenteException();
				}
			}
			con.close();
		} else {
			throw new SQLException("Falha ao conectar ao banco de dados");
		}
	}

	public static void remove(Departamento depto)
		throws SQLException, DepartamentoInexistenteException {
		Connection con = ConnectionFactory.getDiario();
		if (con != null) {
			try (PreparedStatement prst = con.prepareStatement("DELETE FROM `departamentos` WHERE `id` = ?")) {
				prst.setInt(1, depto.getId());
				if (prst.executeUpdate() == 0) {
					throw new DepartamentoInexistenteException();
				}
			}
			con.close();
		} else {
			throw new SQLException("Falha ao conectar ao banco de dados");
		}
	}
}
