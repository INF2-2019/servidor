package diario.campi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import diario.campi.model.*;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;

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
            
            
                       

                
                PreparedStatement ps = con.prepareStatement("UPDATE campi SET nome = '"+nome+"', cidade='"+cidade+"', uf='"+uf+"'   WHERE `id` = '"+id+"' ");
               
                

		int sucesso = ps.executeUpdate();

		return sucesso != 0;

	}
   
}
