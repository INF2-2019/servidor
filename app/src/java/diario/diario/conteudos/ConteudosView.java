/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.diario.conteudos;

import java.util.ArrayList;

/**
 *
 * @author juanr
 */
public class ConteudosView {
    
    public static String consulta(ArrayList<ConteudosModel> conteudos){
        String resultado = "<info>\n";

        for(ConteudosModel conteudo: conteudos){
            
            String conteudoUnicoXML = "<conteudo>\n";
            conteudoUnicoXML += gerarId(conteudo)+"\n";
            conteudoUnicoXML += gerarIdEtapa(conteudo)+"\n";
            conteudoUnicoXML += gerarIdDisciplina(conteudo)+"\n";
            conteudoUnicoXML += gerarConteudo(conteudo)+"\n";
            conteudoUnicoXML += gerarData(conteudo)+"\n";
            conteudoUnicoXML += gerarValor(conteudo)+"\n";
            conteudoUnicoXML+="</conteudo>\n";
            resultado+=conteudoUnicoXML;
        }
        
        return resultado+"</info>";
    }
    
    public static String consultaConteudo(ArrayList<ConteudosModel> conteudos){
        String resultado = "<info>\n";
        for(ConteudosModel conteudo: conteudos){
            String conteudoUnicoXML = "<conteudo>\n";
            conteudoUnicoXML += gerarId(conteudo)+"\n";
            conteudoUnicoXML += gerarIdEtapa(conteudo)+"\n";
            conteudoUnicoXML += gerarIdDisciplina(conteudo)+"\n";
            conteudoUnicoXML += gerarConteudo(conteudo)+"\n";
            conteudoUnicoXML += gerarData(conteudo)+"\n";
            conteudoUnicoXML+="</conteudo>\n";
            resultado+=conteudoUnicoXML;
        }
        
        return resultado+"</info>";
    }
    
    public static String gerarConteudo(ConteudosModel conteudo){
        return "<conteudos>"+conteudo.getConteudo()+"</conteudos>";
    }
    
    public static String gerarData(ConteudosModel conteudo){
        return "<data>"+conteudo.getData()+"</data>";
    }
    
    public static String gerarId(ConteudosModel conteudo){
        return "<id>"+conteudo.getId()+"</id>";
    }
    
    public static String gerarIdDisciplina(ConteudosModel conteudo){
        return "<id-disciplinas>"+conteudo.getIdDisciplina()+"</id-disciplinas>";
    }
    
    public static String gerarIdEtapa(ConteudosModel conteudo){
        return "<id-etapas>"+conteudo.getIdEtapa()+"</id-etapas>";
    }
    
    public static String gerarValor(ConteudosModel conteudo){
        return "<valor>"+conteudo.getValor()+"</valor>";
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
