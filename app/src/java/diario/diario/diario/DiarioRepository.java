/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.diario.diario;

import diario.diario.utils.ErroException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;
import utils.ConnectionFactory;

/**
 *
 * @author juanr
 */
public class DiarioRepository {
    
    /* Utils */

    public static void update(String query) throws ErroException, SQLException{
        Connection conexao = ConnectionFactory.getDiario();
        
        if(conexao==null)
            throw new ErroException("Falha na conexão!","Falha em tentar conectar com o banco de dados");
        
        PreparedStatement st = conexao.prepareStatement(query);
        st.executeUpdate();

        st.close();
        conexao.close();
    }
    
    public static boolean temColuna(ResultSet rs, String coluna) throws SQLException{
        /* Código baseado em: https://stackoverflow.com/questions/3942583/how-to-check-that-a-resultset-contains-a-specifically-named-field */
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int numberOfColumns = rsMetaData.getColumnCount();

        for (int i = 1; i < numberOfColumns + 1; i++) {
            String columnName = rsMetaData.getColumnName(i);
            if (coluna.equals(columnName))
                return true;
        }
        return false;
    }
    
    public static ArrayList execute(String query) throws ErroException, SQLException{
        Connection conexao = ConnectionFactory.getDiario();
        
        if(conexao==null)
            throw new ErroException("Falha na conexão!","Falha em tentar conectar com o banco de dados");
        
        PreparedStatement st = conexao.prepareStatement(query);
        ResultSet resultado = st.executeQuery();
        
        ArrayList<DiarioModel> lista = new ArrayList<DiarioModel>();
        while(resultado.next()){
            DiarioModel modelo;
            boolean tem_falta = temColuna(resultado,"faltas"),
                    tem_nota = temColuna(resultado,"nota");
            
            if(tem_falta&&tem_nota){
                modelo = new DiarioModel(resultado.getInt("id-conteudos"), resultado.getInt("id-matriculas"), resultado.getInt("faltas"), resultado.getDouble("nota"));
            } else if(tem_nota){
                modelo = new DiarioModel(resultado.getInt("id-conteudos"), resultado.getInt("id-matriculas"),resultado.getDouble("nota"));
            } else {
                modelo = new DiarioModel(resultado.getInt("id-conteudos"), resultado.getInt("id-matriculas"), resultado.getInt("faltas"));
            }
         
            lista.add(modelo);
        }

        st.close();
        conexao.close();
        
        return lista;
    }
    
    /* Inserir */

    public static void insere(int idConteudo, int idMatricula, int falta, Double nota) throws ErroException, SQLException{
        update("INSERT INTO diario(`id-conteudos`,`id-matriculas`, faltas, nota) VALUES ("+idConteudo+", "+idMatricula+", "+falta+", "+nota+")");
    }
    
    public static void insere(int idConteudo, int idMatricula, int falta) throws ErroException, SQLException{
        update("INSERT INTO diario(`id-conteudos`,`id-matriculas`, faltas, nota) VALUES ("+idConteudo+", "+idMatricula+", "+falta+", 0.0)");
    }
    
    /* Atualizar */
    
    public static void atualizar(int idConteudo, int idMatricula, int falta) throws ErroException, SQLException{
        update("UPDATE diario SET faltas="+falta+" WHERE `id-conteudos`="+idConteudo+" AND `id-matriculas`="+idMatricula);
    }
    
    public static void atualizar(int idConteudo, int idMatricula, int falta, Double nota) throws ErroException, SQLException{
        update("UPDATE diario SET faltas="+falta+", nota="+nota+" WHERE `id-conteudos`="+idConteudo+" AND `id-matriculas`="+idMatricula);
    }
    
    public static void atualizar(int idConteudo, int idMatricula, Double nota) throws ErroException, SQLException{
        update("UPDATE diario SET nota="+nota+" WHERE `id-conteudos`="+idConteudo+" AND `id-matriculas`="+idMatricula);
    }
    
    /* Deletar */
    
    public static void remover(int idConteudo, int idMatricula) throws ErroException, SQLException{
        update("DELETE FROM diario WHERE `id-conteudos`="+idConteudo+" AND `id-matriculas`="+idMatricula);
    }
    
    public static void removerPorConteudo(int idConteudo) throws ErroException, SQLException{
        update("DELETE FROM diario WHERE `id-conteudos`="+idConteudo);
    }
    
    public static void removerPorMatricula(int idMatricula) throws ErroException, SQLException{
        update("DELETE FROM diario WHERE `id-matriculas`="+idMatricula);
    }
    
    /* Consulta */
    
    public static ArrayList consulta(int idConteudo, int idMatricula) throws ErroException, SQLException{
        return execute("SELECT * FROM diario WHERE `id-conteudos`="+idConteudo+" AND `id-matriculas`="+idMatricula);
    }
    
    public static ArrayList consultaPorConteudo(int idConteudo) throws ErroException, SQLException{
        return execute("SELECT * FROM diario WHERE `id-conteudos`="+idConteudo);
    }
    
    public static ArrayList consultaPorMatricula(int idMatricula, boolean e_atividade) throws ErroException, SQLException{
        return execute("SELECT * FROM diario WHERE `id-matriculas`="+idMatricula);
    }
    
    
}
