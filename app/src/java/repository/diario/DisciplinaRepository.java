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
     public void DeletarDisciplina(Map<String, String> filtro) throws SQLException{
        String sql;
        Set<DisciplinaModel> cursosResultado = new HashSet<>();
        boolean jaAdicionado = false;
        sql = "DELETE FROM `disciplinas` WHERE ";
         if (filtro.containsKey("id-turma")) {
             System.out.println(filtro.get("id-turma"));
                Integer.parseUnsignedInt(filtro.get("id-turma"));
            }
            if (filtro.containsKey("horas")) {
               
                Integer.parseUnsignedInt(filtro.get("carga-horaria-min"));
            }
           for(Map.Entry<String,String> filtros : filtro.entrySet()){
               if(jaAdicionado){
                    sql += "AND (`" + filtros.getKey() + "` = '" + filtros.getValue() + "') ";
               }else{
                   sql += "(`" + filtros.getKey() + "` = '" + filtros.getValue() + "') ";
                   jaAdicionado = true;
               }
           }
           PreparedStatement stat =  con.prepareStatement(sql);
           stat.executeUpdate();
}
}









