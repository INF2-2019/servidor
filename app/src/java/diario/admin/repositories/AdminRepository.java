package diario.admin.repositories;

import diario.admin.models.Admin;

import java.sql.*;

public class AdminRepository {
	private Connection connection;

	public AdminRepository(Connection connection) {
		this.connection = connection;
	}

	public Admin getAdminFromUsuario(String usuario) throws SQLException, AdminNotFoundException {
		String query = "SELECT * FROM admin WHERE `usuario` = ?";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, usuario);
		ResultSet rs = ps.executeQuery();

		if (!rs.next()) {
			throw new AdminNotFoundException(usuario);
		}

		return resultSetToAdmin(rs);
	}

	public void insertAdmin(Admin adm) throws SQLException {
		String query = "INSERT INTO admin (nome, usuario, email, senha) VALUES (?, ?, ?, ?)";

		PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, adm.getNome());
		ps.setString(2, adm.getUsuario());
		ps.setString(3, adm.getEmail());
		ps.setString(4, adm.getHashSenha());
		ps.executeUpdate();

		ResultSet rs = ps.getGeneratedKeys();

		if (rs.next()) {
			int id = rs.getInt(1);
			adm.setId(id);
		}
	}

	private Admin resultSetToAdmin(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String nome = rs.getString("nome");
		String usuario = rs.getString("usuario");
		String email = rs.getString("email");
		String hashSenha = rs.getString("senha");

		return new Admin(id, nome, usuario, email, hashSenha);
	}
}
