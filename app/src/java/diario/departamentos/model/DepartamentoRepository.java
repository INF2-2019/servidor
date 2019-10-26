package diario.departamentos.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import utils.ConnectionFactory;

public class DepartamentoRepository {

    public static List<Departamento> consulta(){
        Connection con = ConnectionFactory.getDiario();
        if(con != null){
            try{
                Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM `departamentos`");

				List<Departamento> deptos = new LinkedList();

				while(rs.next()) {
					deptos.add(new Departamento(rs.getInt("id"), rs.getInt("id-campi"), rs.getString("nome")));
				}
                
                stmt.close();
                con.close();
                return deptos;
            }
            catch(SQLException e){
                List<Departamento> deptos = new LinkedList();
                deptos.add(new Departamento(0, 0, "<erro>Falha ao consultar departamentos no banco de dados</erro>"));
                return deptos;
            }
        }
        else{
            List<Departamento> deptos = new LinkedList();
            deptos.add(new Departamento(0, 0, "<erro>Falha ao conectar ao banco de dados</erro>"));
            return deptos;
        }
    }

    public static Departamento consulta(int id){
        Connection con = ConnectionFactory.getDiario();
        if(con != null){
            try{
                PreparedStatement prst = con.prepareStatement("SELECT * FROM `departamentos` WHERE `id` = ?");
                prst.setInt(1, id);
				ResultSet rs = prst.executeQuery();
				prst.close();
				con.close();

                return new Departamento(rs.getInt("id"), rs.getInt("idCampi"), rs.getString("nome"));
            }
            catch(SQLException e){
                return new Departamento(0, 0, "<erro>Falha ao consultar departamentos no banco de dados</erro>");
            }
        }
        else{
            return new Departamento(0, 0, "<erro>Falha ao conectar ao banco de dados</erro>");
        }
    }

    public static Departamento insere(int idCampi, String nome){
        Connection con = ConnectionFactory.getDiario();
        if(con != null){
            try{
                PreparedStatement prst = con.prepareStatement("INSERT INTO `departamentos` (`id-campi`, `nome`) VALUES (?, ?)");
				prst.setInt(1, idCampi);
				prst.setString(2, nome);
                ResultSet rs = prst.executeQuery();
                prst.close();
                con.close();

                return new Departamento(rs.getInt("id"), rs.getInt("idCampi"), rs.getString("nome"));
            }
            catch(SQLException e){
                return new Departamento(0, 0, "<erro>Falha ao inserir departamento no banco de dados</erro>");
            }
        }
        else{
            return new Departamento(0, 0, "<erro>Falha ao conectar ao banco de dados</erro>");
        }
    }

    public static Departamento insere(Departamento depto){
        Connection con = ConnectionFactory.getDiario();
        if(con != null){
            try{
                PreparedStatement prst = con.prepareStatement("INSERT INTO `departamentos` (`id-campi`, `nome`) VALUES (?, ?)");
				prst.setInt(1, depto.getIdCampi());
				prst.setString(2, depto.getNome());
                ResultSet rs = prst.executeQuery();
                prst.close();
                con.close();

                return new Departamento(rs.getInt("id"), rs.getInt("idCampi"), rs.getString("nome"));
            }
            catch(SQLException e){
                return new Departamento(0, 0, "<erro>Falha ao inserir departamento no banco de dados</erro>");
            }
        }
        else{
            return new Departamento(0, 0, "<erro>Falha ao conectar ao banco de dados</erro>");
        }
    }

    public static Departamento atualiza(int id, int idCampi){
        Connection con = ConnectionFactory.getDiario();
        if(con != null){
            try{
                PreparedStatement prst = con.prepareStatement("UPDATE `departamentos` SET `id-campi` = ? WHERE `id` = ?");
				prst.setInt(1, idCampi);
				prst.setInt(2, id);
				ResultSet rs = prst.executeQuery();
				prst.close();
				con.close();
                
                return new Departamento(rs.getInt("id"), rs.getInt("idCampi"), rs.getString("nome"));
            }
            catch(SQLException e){
                return new Departamento(0, 0, "<erro>Falha ao atualizar o departamento<erro>");
            }
        }
        else{
            return new Departamento(0, 0, "<erro>Falha ao conectar ao banco de dados</erro>");
        }
    }

    public static Departamento atualiza(int id, String nome){
        Connection con = ConnectionFactory.getDiario();
        if(con != null){
            try{
                PreparedStatement prst = con.prepareStatement("UPDATE `departamentos` SET `nome` = ? WHERE `id` = ?");
				prst.setString(1, nome);
				prst.setInt(2, id);
				ResultSet rs = prst.executeQuery();
				prst.close();
				con.close();
                
                return new Departamento(rs.getInt("id"), rs.getInt("idCampi"), rs.getString("nome"));
            }
            catch(SQLException e){
                return new Departamento(0, 0, "<erro>Falha ao atualizar o departamento<erro>");
            }
        }
        else{
            return new Departamento(0, 0, "<erro>Falha ao conectar ao banco de dados</erro>");
        }
    }

    public static Departamento atualiza(int id, int idCampi, String nome){
        Connection con = ConnectionFactory.getDiario();
        if(con != null){
            try{
                PreparedStatement prst = con.prepareStatement("UPDATE `departamentos` SET `id-campi` = ?, `nome` = ? WHERE `id` = ?");
				prst.setInt(1, idCampi);
				prst.setString(2, nome);
				prst.setInt(3, id);
				ResultSet rs = prst.executeQuery();
				prst.close();
				con.close();
                
                return new Departamento(rs.getInt("id"), rs.getInt("idCampi"), rs.getString("nome"));
            }
            catch(SQLException e){
                return new Departamento(0, 0, "<erro>Falha ao atualizar o departamento<erro>");
            }
        }
        else{
            return new Departamento(0, 0, "<erro>Falha ao conectar ao banco de dados</erro>");
        }
    }

    public static Departamento atualiza(Departamento depto){
        Connection con = ConnectionFactory.getDiario();
        if(con != null){
            try{
                PreparedStatement prst = con.prepareStatement("UPDATE `departamentos` SET `id-campi` = ?, `nome` = ? WHERE `id` = ?");
				prst.setInt(1, depto.getIdCampi());
				prst.setString(2, depto.getNome());
				prst.setInt(3, depto.getId());
				ResultSet rs = prst.executeQuery();
				prst.close();
				con.close();
                
                return new Departamento(rs.getInt("id"), rs.getInt("idCampi"), rs.getString("nome"));
            }
            catch(SQLException e){
                return new Departamento(0, 0, "<erro>Falha ao atualizar o departamento<erro>");
            }
        }
        else{
            return new Departamento(0, 0, "<erro>Falha ao conectar ao banco de dados</erro>");
        }
    }

    public static Departamento remove(int id){
        Connection con = ConnectionFactory.getDiario();
        if(con != null){
            try{
                PreparedStatement prst = con.prepareStatement("DELETE FROM `departamentos` WHERE `id` = ?");
				prst.setInt(1, id);
				ResultSet rs = prst.executeQuery();
				prst.close();
				con.close();
                
                return new Departamento(rs.getInt("id"), rs.getInt("idCampi"), rs.getString("nome"));
            }
            catch(SQLException e){
                return new Departamento(0, 0, "<erro>Falha ao remover departamento do banco de dados</erro>");
            }
        }
        else{
            return new Departamento(0, 0, "<erro>Falha ao conectar ao banco de dados</erro>");
        }
    }

    public static Departamento remove(Departamento depto){
        Connection con = ConnectionFactory.getDiario();
        if(con != null){
            try{
                PreparedStatement prst = con.prepareStatement("DELETE FROM `departamentos` WHERE `id` = ?");
				prst.setInt(1, depto.getId());
				ResultSet rs = prst.executeQuery();
				prst.close();
				con.close();
                
                return new Departamento(rs.getInt("id"), rs.getInt("idCampi"), rs.getString("nome"));
            }
            catch(SQLException e){
                return new Departamento(0, 0, "<erro>Falha ao remover departamento do banco de dados</erro>");
            }
        }
        else{
            return new Departamento(0, 0, "<erro>Falha ao conectar ao banco de dados</erro>");
        }
    }
}
