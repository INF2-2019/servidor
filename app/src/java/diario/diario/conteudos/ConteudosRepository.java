/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.diario.conteudos;

import diario.diario.diario.DiarioModel;
import diario.diario.diario.DiarioRepository;
import diario.diario.utils.ErroException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.ConnectionFactory;

/**
 *
 * @author juanr
 */
public class ConteudosRepository {
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
        
        ArrayList<ConteudosModel> lista = new ArrayList<ConteudosModel>();
        while(resultado.next()){
            ConteudosModel modelo = new ConteudosModel(resultado.getInt("id"),resultado.getInt("id-etapas"),resultado.getInt("id-disciplinas"),resultado.getString("conteudos"),resultado.getDate("data"),resultado.getDouble("valor"));
            lista.add(modelo);
        }

        st.close();
        conexao.close();
        
        return lista;
    }
    
    /* Inserir */

    public static void insere(int idEtapas, int idDisciplinas, String conteudo, Date data ,Double nota) throws ErroException, SQLException{
        update("INSERT INTO conteudos(`id-etapas`,`id-disciplinas`, conteudos , data, valor) VALUES ("+idEtapas+","+idDisciplinas+",\""+conteudo+"\",\""+data.toString()+"\","+nota+")");
    }
    
    public static void insere(int idEtapas, int idDisciplinas, String conteudo, Date data) throws ErroException, SQLException{
        update("INSERT INTO conteudos(`id-etapas`,`id-disciplinas`, conteudos , data, valor) VALUES ("+idEtapas+","+idDisciplinas+",\""+conteudo+"\",\""+data.toString()+"\", 0.0)");
    }
    
    /* Atualizar */
    /* Nessa parte tive que fazer uma escolha, pois se eu fizesse um metodo pra cada possibilidade de combinação de campos ficaria muito grande
    então optei por utilizar o ConteudosModel já que desse jeito só um metodo é necessário e faz integração direta com os parametros do servlet (ConteudosParametros)
    */
    public static void atualizar(ConteudosModel modelo) throws ErroException, SQLException{        
        ArrayList modificacoes = new ArrayList();
        int id = modelo.getId();
        if(modelo.getIdEtapa()!=null)
            modificacoes.add("`id-etapas`="+modelo.getIdEtapa());
        if(modelo.getIdDisciplina()!=null)
            modificacoes.add("`id-disciplinas`="+modelo.getIdDisciplina());
        if(modelo.getConteudo()!=null)
            modificacoes.add("conteudos=\""+modelo.getConteudo()+"\"");
        if(modelo.getData()!=null)
            modificacoes.add("data=\""+modelo.getData()+"\"");
        if(modelo.getValor()!=null)
            modificacoes.add("valor="+modelo.getValor());

        if(modificacoes.size()==0)
            throw new ErroException("Nenhuma alteração detectada!","Nenhum parametro de modificação recebido");
        
        update("UPDATE conteudos SET "+String.join(",",modificacoes)+" WHERE id="+id);
    }

    
    /* Deletar */
    
    public static void remover(int id) throws ErroException, SQLException{
        DiarioRepository.removerPorConteudo(id);
        update("DELETE FROM conteudos WHERE id="+id);
    }
    
    public static void removerPorEtapa(int idEtapa) throws ErroException, SQLException{
        ConteudosModel modelo = new ConteudosModel();
        modelo.setIdEtapa(idEtapa);
        ArrayList<ConteudosModel> resultados = consulta(modelo);
        
        if(resultados.size()==0)
            throw new ErroException("Não existe!","Não há nenhum conteudo nessa etapa");
        
        for(ConteudosModel resultado : resultados){
            int id = resultado.getId();
            DiarioRepository.removerPorConteudo(id);
        }
        
        update("DELETE FROM conteudos WHERE `id-etapas`="+idEtapa);
        
    }
    
    public static void removerPorDisciplina(int idDisciplina) throws ErroException, SQLException{
        ConteudosModel modelo = new ConteudosModel();
        modelo.setIdDisciplina(idDisciplina);
        ArrayList<ConteudosModel> resultados = consulta(modelo);
        
        if(resultados.size()==0)
            throw new ErroException("Não existe!","Não há nenhum conteudo nessa disciplina");
        
        for(ConteudosModel resultado : resultados){
            int id = resultado.getId();
            DiarioRepository.removerPorConteudo(id);
        }
        
        update("DELETE FROM conteudos WHERE `id-disciplinas`="+idDisciplina);
    }
    
    public static void removerPorEtapaEDisciplina(int idEtapa, int idDisciplina) throws ErroException, SQLException{
        ConteudosModel modelo = new ConteudosModel();
        modelo.setIdEtapa(idEtapa);
        modelo.setIdDisciplina(idDisciplina);
        ArrayList<ConteudosModel> resultados = consulta(modelo);
        
        if(resultados.size()==0)
            throw new ErroException("Não existe!","Não há nenhum conteudo que esteja simultaneamente nesse conteudo e disciplina");
        
        for(ConteudosModel resultado : resultados){
            int id = resultado.getId();
            DiarioRepository.removerPorConteudo(id);
        }
        
        update("DELETE FROM conteudos WHERE `id-etapas`="+idEtapa +" AND `id-disciplinas`="+idDisciplina);
    }
   
    
    /* Consulta */
    
    public static ArrayList consulta(ConteudosModel modelo, String complemento)throws ErroException, SQLException{
        ArrayList filtro = new ArrayList();
        
        
        if(modelo.getId()!=null)
            filtro.add("id="+modelo.getId());
        else{
            if(modelo.getIdEtapa()!=null)
                filtro.add("`id-etapas`="+modelo.getIdEtapa());
            if(modelo.getIdDisciplina()!=null)
                filtro.add("`id-disciplinas`="+modelo.getIdDisciplina());
            if(modelo.getData()!=null)
                filtro.add("data=\""+modelo.getData()+"\"");
        }
        
        if(filtro.size()==0)
            throw new ErroException("Nenhum parametro detectado!","Nenhum parametro de consulta recebido");
        
        return execute("SELECT * FROM conteudos WHERE "+String.join(" AND ",filtro)+complemento);
    }
    
    public static ArrayList consulta(ConteudosModel modelo)throws ErroException, SQLException{
        return consulta(modelo,"");
    }
    
    public static ArrayList consultaAtividade(ConteudosModel modelo) throws ErroException, SQLException{
        return consulta(modelo," AND valor>0.0");
    }
    
    public static ArrayList consultaConteudo(ConteudosModel modelo) throws ErroException, SQLException{
        return consulta(modelo," AND valor=0.0");
    }
    
    
}
