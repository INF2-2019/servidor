/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.reservas.views;

/**
 *
 * @author User
 */
public class ExcecaoEmprestimoCadastrado extends Exception{
	public ExcecaoEmprestimoCadastrado() {
		super("o item ainda está emprestado");
	}

	public ExcecaoEmprestimoCadastrado(String mensagem) {
		super(mensagem);
	}
}
