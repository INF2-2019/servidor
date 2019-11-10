/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.diario.diario;

import diario.diario.utils.ErroException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author juanr
 */
public class DiarioParametros extends DiarioModel{

    protected String tipo;
    protected HttpServletRequest request;
    
    public DiarioParametros(){
        
    }
    
    public DiarioParametros(HttpServletRequest request) throws ErroException{
        setParametros(request);
    }
    
    public void setParametros(HttpServletRequest request)throws ErroException{
        this.request = request;
        
        if(existe(request,"conteudo"))
            setIdConteudo(request.getParameter("conteudo"));
        if(existe(request,"matricula"))
            setIdMatricula(request.getParameter("matricula"));
        if(existe(request,"falta"))
            setFalta(request.getParameter("falta"));
        if(existe(request,"nota"))
            setNota(request.getParameter("nota"));
        if(existe(request,"tipo"))
            setTipo(request.getParameter("tipo"));
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
    
    public void obrigatorios(HttpServletRequest request, String ...parametros) throws ErroException{
        for(String parametro:parametros){
            if(!existe(request,parametro))
                throw new ErroException("Erro com '" + parametro + "'", "O parametro '" + parametro + "' é obrigatório!");
        }
    }
    
    public void obrigatorios(String ...parametros) throws ErroException{
        for(String parametro:parametros){
            if(!existe(request,parametro))
                throw new ErroException("Erro com '" + parametro + "'", "O parametro '" + parametro + "' é obrigatório!");
        }
    }
    
    /* ERROS */
    
    public ErroException erroDecimal(String parametro){
        return new ErroException(parametro+" deve ser decimal!","O parametro "+parametro+" não está no formato correto");
    }
    
    public ErroException erroInteiro(String parametro){
        return new ErroException(parametro+" deve ser inteiro!","O parametro "+parametro+" não está no formato correto");
    }
    
    public ErroException erroData(String parametro){
        return new ErroException(parametro+" deve ser uma data!","O parametro "+parametro+" não está no formato correto");
    }
    
    /* GETTERS E SETTERS*/

    public void setIdConteudo(String idConteudo) throws ErroException{
        try{
            this.idConteudo = Integer.valueOf(idConteudo);
        }catch(Exception e){
            throw erroInteiro("conteudo");
        }
    }

    public void setIdMatricula(String idMatricula) throws ErroException{
        try{
            this.idMatricula = Integer.valueOf(idMatricula);
        }catch(Exception e){
            throw erroInteiro("matricula");
        }
    }

    public void setFalta(String falta) throws ErroException{
        try{    
            this.falta = Integer.valueOf(falta);
        }catch(Exception e){
            throw erroInteiro("falta");
        }
    }

    public void setNota(String nota) throws ErroException{
        try{
            this.nota = Double.valueOf(nota);
        }catch(Exception e){
            throw erroDecimal("nota");
        }
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) throws ErroException{
        if(!("conteudo".equals(tipo) || "atividade".equals(tipo))) {
             throw new ErroException("'tipo' não esta formatado corretamente", "O 'tipo' pode ser 'conteudo' ou 'atividade'");
         }
        
        this.tipo = tipo;
    }
    
    
    
}
