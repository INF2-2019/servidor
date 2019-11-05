/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.acervo;

/**
 *
 * @author Aluno
 */
public class ExcecaoParametrosIncorretos extends Exception {
    
    public ExcecaoParametrosIncorretos(){
        super();
    }

    public ExcecaoParametrosIncorretos(String mensagem) {
        super(mensagem);
    }
    
}
