package repository.diario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import model.diario.CampiModel;

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
		


		PreparedStatement ps = con.prepareStatement("INSERT INTO `cursos` (`nome`, `cidade`, `uf`) VALUES (?, ?, ?, ?)");

		ps.setString(1, nome);
		ps.setString(2, cidade);
		ps.setString(3, uf);

		int sucesso = ps.executeUpdate();

		return sucesso != 0;

	}
   
}
