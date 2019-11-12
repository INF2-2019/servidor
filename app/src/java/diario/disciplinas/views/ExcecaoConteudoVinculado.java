/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.disciplinas.views;

/**
 *
 * @author User
 */
public class ExcecaoConteudoVinculado extends Exception {
	public ExcecaoConteudoVinculado() {
		super("Não foi possível deletar o curso pois existem turmas vinculadas.");
	}

	public ExcecaoConteudoVinculado(String mensagem) {
		super(mensagem);
	}
}
