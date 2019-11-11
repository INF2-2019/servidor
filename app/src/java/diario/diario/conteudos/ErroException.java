/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.diario.conteudos;

/**
 *
 * @author juanr
 */
public class ErroException extends Exception{
    public String causa = null, mensagem = null; 
    
    public ErroException(String causa, String mensagem){
        this.causa = causa;
        this.mensagem = mensagem;
    }

    public ErroException(String string) {
        super(string);
        this.mensagem = string;
        this.causa = super.getMessage();
    }
    
    @Override
    public String getMessage(){
        return this.mensagem;
    }
    
}
