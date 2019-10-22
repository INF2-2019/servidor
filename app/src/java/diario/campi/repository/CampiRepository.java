
package diario.campi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


/**
 *
 * @author User
 */
public class CampiRepository {
	private Connection con;
    
	public CampiRepository(Connection con){
		this.con = con;
	}
    
    
	public boolean deletarCampi(String id) throws SQLException{
		String sql;
		int idParsed = Integer.parseUnsignedInt(id);
		sql = "DELETE FROM `campi` WHERE `id` = ?";

		PreparedStatement stat =  con.prepareStatement(sql);
		stat.setInt(1, idParsed);

		int sucesso = stat.executeUpdate();
		// Se deletou algo, retorna true, senão retorna false
		return sucesso != 0;
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
		String query = "UPDATE campi SET";
		if (!"".equals(nome)) {
			query += " nome='"+nome+"'";
			adcs++;
		}
		if (!"".equals(cidade)) {
		   if (adcs > 0) 
			query += ",";
		   query += " cidade='"+cidade+"'";
		  adcs++;
		}
		if (!"".equals(uf)) {
		   if (adcs > 0) 
			query += ",";
		   query += " uf='"+uf+"'";
		}
		
		query += " WHERE `id` = ?";  
		
		PreparedStatement ps = con.prepareStatement(query);
		
		ps.setInt(1, Integer.parseUnsignedInt(id));
                
		int sucesso = ps.executeUpdate();

		return sucesso != 0;

	}
   
}
