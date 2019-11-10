/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.diario.diario;

import java.util.ArrayList;

/**
 *
 * @author juanr
 */
public class DiarioView {
    public static String consulta(ArrayList<DiarioModel> diarios){
        String resultado = "<info>\n";
        
        for(DiarioModel diario :diarios){
            String diarioUnicoXML = "<diario>\n";
            diarioUnicoXML+= gerarIdConteudo(diario)+"\n";
            diarioUnicoXML+= gerarIdMatricula(diario)+"\n";
            
            if(diario.getFalta()!=null)
                diarioUnicoXML+=gerarFaltas(diario)+"\n";
            if(diario.getNota()!=null)
                diarioUnicoXML+=gerarNota(diario)+"\n";
            
            diarioUnicoXML+="</diario>\n";
            resultado+=diarioUnicoXML;
        }

        return resultado+"</info>";
    }
    
    public static String consultaSemFalta(ArrayList<DiarioModel> diarios){
        String resultado = "<info>\n";
        
        for(DiarioModel diario :diarios){
            String diarioUnicoXML = "<diario>\n";
            diarioUnicoXML+= gerarIdConteudo(diario)+"\n";
            diarioUnicoXML+= gerarIdMatricula(diario)+"\n";
            diarioUnicoXML+=gerarNota(diario)+"\n";
            
            diarioUnicoXML+="</diario>\n";
            resultado+=diarioUnicoXML;
        }

        return resultado+"</info>";
    }
    
    public static String consultaSemNota(ArrayList<DiarioModel> diarios){
        String resultado = "<info>\n";
        
        for(DiarioModel diario :diarios){
            String diarioUnicoXML = "<diario>\n";
            diarioUnicoXML+= gerarIdConteudo(diario)+"\n";
            diarioUnicoXML+= gerarIdMatricula(diario)+"\n";
            diarioUnicoXML+=gerarFaltas(diario)+"\n";

            diarioUnicoXML+="</diario>\n";
            resultado+=diarioUnicoXML;
        }

        return resultado+"</info>";
    }
    
    public static String gerarIdConteudo(DiarioModel diario){
        return "<id-conteudo>"+diario.getIdConteudo()+"</id-conteudo>";
    }
    
    public static String gerarIdMatricula(DiarioModel diario){
        return "<id-matricula>"+diario.getIdMatricula()+"</id-matricula>";
    }
    
    public static String gerarFaltas(DiarioModel diario){
        return "<faltas>"+diario.getFalta()+"</faltas>";
    }
    
    public static String gerarNota(DiarioModel diario){
        return "<nota>"+diario.getNota()+"</nota>";
    }
    
    /* Erro e Sucesso */
    
    public static String erro(String mensagem, String causa){
        return  "<info>\n"+
                "<erro>\n"+
                "<mensagem>"+mensagem+"</mensagem>\n"+
                "<causa>"+causa+"</causa>\n"+
                "</erro>\n"+
                "</info>";
    }
    
    public static String erro(String mensagem){
        return  "<info>\n"+
                "<erro>\n"+
                "<mensagem>"+mensagem+"</mensagem>\n"+
                "</erro>\n"+
                "</info>";
    }
    
    public static String erro(){
        return erro("Ocorreu um erro!");
    }
    
    public static String sucesso(String mensagem, String causa){
        return  "<info>\n"+
                "<sucesso>\n"+
                "<mensagem>"+mensagem+"</mensagem>\n"+
                "<causa>"+causa+"</causa>\n"+
                "</sucesso>\n"+
                "</info>";
    }
    
    public static String sucesso(String mensagem){
        return  "<info>\n"+
                "<sucesso>\n"+
                "<mensagem>"+mensagem+"</mensagem>\n"+
                "</sucesso>\n"+
                "</info>";
    }
    
    public static String sucesso(){
        return sucesso("Sucesso na operação!");
    }
}
