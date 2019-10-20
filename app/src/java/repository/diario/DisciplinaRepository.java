/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.diario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
        Integer.parseUnsignedInt(id);
        sql = "DELETE FROM `disciplinas` WHERE `id`="+id;
        PreparedStatement stat =  con.prepareStatement(sql);
        stat.executeUpdate();
    }
}









