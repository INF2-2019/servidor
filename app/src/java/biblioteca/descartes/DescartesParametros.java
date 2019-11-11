/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.descartes;

import biblioteca.descartes.views.ExcecaoPadrao;
import biblioteca.descartes.views.ExcecaoParametroIncorreto;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author juanr
 */
public class DescartesParametros extends DescartesModel {
    
    protected HttpServletRequest request;

    public DescartesParametros() {
    }

    public DescartesParametros(HttpServletRequest request) throws ExcecaoParametroIncorreto{
        setParametros(request);
    }
    
    
    public void setParametros(HttpServletRequest request)throws ExcecaoParametroIncorreto{
        this.request = request;
        
        if(existe(request,"acervo"))
            setIdAcervo(request.getParameter("acervo"));
        if(existe(request,"data"))
            setDataDescarte(request.getParameter("data"));
        if(existe(request,"funcionario"))
            setOperador(request.getParameter("funcionario"));
        if(existe(request,"motivacao"))
            setMotivos(request.getParameter("motivacao"));
    }
    
    /* UTIL */
    
    public static boolean existe(HttpServletRequest req, String parametro){
        return req.getParameter(parametro)!=null;
    }
    
    public boolean existe(HttpServletRequest request, String ...parametros){
        for(String parametro:parametros){
            if(!existe(request,parametro)) return false;
        }
        return true;
    }
    
    public boolean existe(String ...parametros){
        for(String parametro:parametros){
            if(!existe(request,parametro)) return false;
        }
        return true;
    }
    
    public void obrigatorios(HttpServletRequest request, String ...parametros) throws ExcecaoParametroIncorreto{
        for(String parametro:parametros){
            if(!existe(request,parametro))
                throw new ExcecaoParametroIncorreto("Parâmetro obrigatório: '" + parametro + "'");
        }
    }
    
    public void obrigatorios(String ...parametros) throws ExcecaoParametroIncorreto{
        for(String parametro:parametros){
            if(!existe(request,parametro))
                throw new ExcecaoParametroIncorreto("Parâmetro obrigatório: '" + parametro + "'");
        }
    }
    
    /* ERROS */
    
    public ExcecaoParametroIncorreto erroDecimal(String parametro){
        return new ExcecaoParametroIncorreto(parametro+" deve ser decimal!","O parametro "+parametro+" não está no formato correto");
    }
    
    public ExcecaoParametroIncorreto erroInteiro(String parametro){
        return new ExcecaoParametroIncorreto(parametro+" deve ser inteiro!","O parametro "+parametro+" não está no formato correto");
    }
    
    public ExcecaoParametroIncorreto erroData(String parametro){
        return new ExcecaoParametroIncorreto(parametro+" deve ser uma data!","O parametro "+parametro+" não está no formato correto");
    }
    
    /* GETTERS E SETTERS*/
    
    public void setIdAcervo(String idAcervo) throws ExcecaoParametroIncorreto{
        try{
            this.idAcervo = Integer.valueOf(idAcervo);
        }catch(NumberFormatException e){
            throw erroInteiro("acervo");
        }
    }
    
    public void setDataDescarte(String dataDescarte) throws ExcecaoParametroIncorreto{
        try{
            this.dataDescarte = Date.valueOf(dataDescarte);
        }catch(Exception e){
            throw erroData("data");
        }
    }
}
