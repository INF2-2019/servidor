package biblioteca.campi.repository;

import diario.campi.view.viewConsulta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import utils.autenticador.*;

/**
 *
 * @author User
 */
public class CampiRepository {

	private Connection con;

	public CampiRepository(Connection con) {
		this.con = con;
	}

	public String deletarCampi(String id) throws SQLException {
		String sql;
		PreparedStatement ps = con.prepareStatement("SELECT * FROM `acervo` WHERE `id-campi` = ?");
		int idParsed = Integer.parseUnsignedInt(id);
		ps.setInt(1, idParsed);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return ("acervo");
		}
		sql = "DELETE FROM `campi` WHERE `id` = ?";

		PreparedStatement stat = con.prepareStatement(sql);
		stat.setInt(1, idParsed);

		int sucesso = stat.executeUpdate();

		if (sucesso != 0) {
			return ("sucesso");
		} else {
			return ("erro");
		}

	}

	public boolean inserirCampi(String nome, String cidade, String uf) throws NumberFormatException, SQLException {

		PreparedStatement ps = con.prepareStatement("INSERT INTO `campi` (`nome`, `cidade`, `uf`) VALUES (?, ?, ?)");

		ps.setString(1, nome);
		ps.setString(2, cidade);
		ps.setString(3, uf);

		int sucesso = ps.executeUpdate();

		return sucesso != 0;

	}

	public boolean alterarCampi(String id, String nome, String cidade, String uf) throws NumberFormatException, SQLException, TransformerException, ParserConfigurationException {
		int adcs = 0;
		int cont = 1;
		boolean[] pars = new boolean[3];
		for (int i = 0; i < 3; i++) {
			pars[i] = false;
		}
		String query = "UPDATE campi SET";
		if (!"".equals(nome)) {
			query += " nome= ?";
			adcs++;
			pars[0] = true;
		}
		if (!"".equals(cidade)) {
			if (adcs > 0) {
				query += ",";
			}
			query += " cidade= ?";
			adcs++;
			pars[1] = true;
		}
		if (!"".equals(uf)) {
			if (adcs > 0) {
				query += ",";
			}
			query += " uf= ?";
			pars[2] = true;
		}

		query += " WHERE `id` = ?";

		PreparedStatement ps = con.prepareStatement(query);

		if (pars[0]) {
			ps.setString(cont, nome);
			cont++;
		}
		if (pars[1]) {
			ps.setString(cont, cidade);
			cont++;
		}

		if (pars[2]) {
			ps.setString(cont, uf);
			cont++;
		}

		ps.setInt(cont, Integer.parseUnsignedInt(id));

		int sucesso = ps.executeUpdate();

		return sucesso != 0;

	}

	public boolean checarAutorizacaoADM(HttpServletRequest request, HttpServletResponse response) {
		DiarioAutenticador x = new DiarioAutenticador(request, response);

		return x.cargoLogado() == DiarioCargos.ADMIN;
	}

	public String listarCampi() throws SQLException {
		String xml = "";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM `campi`");

		while (rs.next()) {
			xml += viewConsulta.XMLCampi(rs.getInt("id"), rs.getString("nome"), rs.getString("cidade"), rs.getString("uf"));
		}
		xml = viewConsulta.XMLConsulta(xml);
		return xml;
	}

	public String consultarPorId(String id) throws SQLException {
		String xml = "";
		PreparedStatement ps = con.prepareStatement("SELECT * FROM `campi` WHERE `id` = ?");
		int idParsed = Integer.parseUnsignedInt(id);
		ps.setInt(1, idParsed);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			xml += viewConsulta.XMLCampi(rs.getInt("id"), rs.getString("nome"), rs.getString("cidade"), rs.getString("uf"));
		}
		return xml;
	}

}
