
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Aluno
 */
public class RespostaXML {
    public static String erro(String mensagem, String causa){
        return  "<erro>\n"+
                "<mensagem>"+mensagem+"</mensagem>\n"+
                "<causa>"+causa+"</causa>\n"+
                "</erro>";
    }
    
    public static String erro(String mensagem){
        return  "<erro>\n"+
                "<mensagem>"+mensagem+"</mensagem>\n"+
                "</erro>";
    }
    
    public static String erro(){
        return erro("Ocorreu um erro!");
    }
    
    public static String sucesso(String mensagem, String causa){
        return  "<sucesso>\n"+
                "<mensagem>"+mensagem+"</mensagem>\n"+
                "<causa>"+causa+"</causa>\n"+
                "</sucesso>";
    }
    
    public static String sucesso(String mensagem){
        return  "<sucesso>\n"+
                "<mensagem>"+mensagem+"</mensagem>\n"+
                "</sucesso>";
    }
    
    public static String sucesso(){
        return sucesso("Sucesso na operação!");
    }
    
    public static String retornaSet(ResultSet resultados, String ...campos) throws SQLException{
        String resultado = "<info>\n";
        while(resultados.next()){
            String nome = resultados.getMetaData().getTableName(1);
            resultado+="<"+nome+">\n";
            for(String campo: campos){
                String valor = resultados.getString(campo);
                resultado+="<"+campo+">"+valor+"</"+campo+">\n";
            }
            resultado+="</"+nome+">\n";
        }
        return resultado+"</info>";
    }
}
