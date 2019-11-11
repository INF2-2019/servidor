/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.descartes.views;

import biblioteca.descartes.DescartesModel;
import biblioteca.descartes.DescartesModel;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author juanr
 */
public class DescartesView {
    ArrayList<DescartesModel> descartes;

    public DescartesView(ArrayList<DescartesModel> descartes) {
        this.descartes = descartes;
    }
    
    public void render(PrintWriter out){
        out.print(gerar());
    }
    
    public String gerar(){
        String resposta = "<info>\n";
        for(DescartesModel modelo:descartes)
            resposta+=gerarUnico(modelo)+"\n";
        resposta+="</info>";
        return resposta;
    }
    
    public String gerarUnico(DescartesModel modelo){
        String resposta = "<descartes>\n";
        resposta+="<id-acervo>"+modelo.getIdAcervo()+"</id-acervo>\n";
        resposta+="<data-descarte>"+modelo.getDataDescarte()+"</data-descarte>\n";
        resposta+="<motivos>"+modelo.getMotivos()+"</motivos>\n";
        resposta+="<operador>"+modelo.getOperador()+"</operador>\n";
        resposta+="</descartes>";
        return resposta;
    }
}
