/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.diario.conteudos;

import diario.diario.utils.ErroException;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author juanr
 */
public class ConteudosParametros extends ConteudosModel{
    protected String tipo;
    protected HttpServletRequest request;
    public ConteudosParametros(){
        
    }
    
    public ConteudosParametros(HttpServletRequest request) throws ErroException{
        setParametros(request);
    }
    
    public void setParametros(HttpServletRequest request)throws ErroException{
        this.request = request;
        
        if(existe(request,"id"))
            setId(request.getParameter("id"));
        if(existe(request,"etapa"))
            setIdEtapa(request.getParameter("etapa"));
        if(existe(request,"disciplina"))
            setIdDisciplina(request.getParameter("disciplina"));
        if(existe(request,"conteudo"))
            setConteudo(request.getParameter("conteudo"));
        if(existe(request,"valor"))
            setValor(request.getParameter("valor"));
        if(existe(request,"data"))
            setData(request.getParameter("data"));
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
    
    public ErroException erroStringVazia(String parametro){
        return new ErroException(parametro+" não pode estar vazio!","O parametro "+parametro+" não pode estar vazio");
    }
    
    /* Getters e Setters */

    public void setId(String id) throws ErroException{
        try{
            this.id = Integer.valueOf(id);
        } catch(NumberFormatException e){
            throw erroInteiro("conteudo");
        }
    }

    public void setIdEtapa(String idEtapa) throws ErroException{
        try{
            this.idEtapa = Integer.valueOf(idEtapa);
        } catch(NumberFormatException e){
            throw erroInteiro("etapa");
        }
    }

    public void setIdDisciplina(String idDisciplina) throws ErroException{
        try{
            this.idDisciplina = Integer.valueOf(idDisciplina);
        } catch(NumberFormatException e){
            throw erroInteiro("disciplina");
        }
    }

    @Override
    public void setConteudo(String conteudo){
        this.conteudo = conteudo;
    }

    public void setData(String data) throws ErroException{
        try{
            this.data = Date.valueOf(data);
        }catch(Exception e){
            throw erroData("data");
        }
    }

    public void setValor(String valor) throws ErroException{
        try{
            this.valor = Double.valueOf(valor);
        }catch(NumberFormatException e){
            throw erroDecimal("valor");
        }
        if(this.valor<0)
            throw new ErroException("O valor não pode ser negativo!", "O campo valor aceita apenas numeros positivos");
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
