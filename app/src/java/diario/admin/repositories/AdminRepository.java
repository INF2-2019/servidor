package diario.admin.repositories;

import diario.admin.models.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepository {
	private Connection connection;

	public AdminRepository(Connection connection) {
		this.connection = connection;
	}

	public Admin getAdminFromUsuario(String usuario) throws SQLException, AdminNotFoundException {
		String query = "SELECT * FROM admins WHERE `usuario` = ?";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, usuario);
		ResultSet rs = ps.executeQuery();

		if(!rs.next()){
			throw new AdminNotFoundException(usuario);
		}

		return resultSetToAdmin(rs);
	}

	private Admin resultSetToAdmin(ResultSet rs) throws SQLException{
		int id = rs.getInt("id");
		String nome = rs.getString("nome");
		String usuario = rs.getString("usuario");
		String email = rs.getString("email");
		String hashSenha = rs.getString("senha");

		return new Admin(id, nome, usuario, email, hashSenha);
	}
}
