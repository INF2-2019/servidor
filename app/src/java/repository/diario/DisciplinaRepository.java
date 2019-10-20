
package repository.diario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import model.diario.DisciplinaModel;

/**
 *
 * @author User
 */
public class DisciplinaRepository {
    private Connection con;
    
    public DisciplinaRepository(Connection con){
        this.con = con;
    }
    public void DeletarDisciplina(String id) throws SQLException{
        String sql;
        int idParsed = Integer.parseUnsignedInt(id);
        sql = "DELETE FROM `disciplinas` WHERE `id` = ?";
        
        PreparedStatement stat =  con.prepareStatement(sql);
        stat.setInt(1, idParsed);
        stat.executeUpdate();
    }
   
}

