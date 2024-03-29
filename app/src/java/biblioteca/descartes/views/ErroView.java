/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.descartes.views;

import java.io.PrintWriter;

/**
 * @author juanr
 */
public class ErroView {
	String mensagem, causa;

	public ErroView(ExcecaoPadrao erro) {
		this(erro.mensagem, erro.causa);
	}

	public ErroView(String mensagem, String causa) {
		this.mensagem = mensagem;
		this.causa = causa;
	}

	public ErroView(String mensagem) {
		this.mensagem = mensagem;
		this.causa = null;
	}

	public ErroView() {
		this("Ocorreu um erro!");
	}

	public void render(PrintWriter out) {
		out.print(gerar());
	}

	public String gerar() {
		String resposta = "<erro>\n"
			+ "<mensagem>" + mensagem + "</mensagem>\n";
		if (causa != null) {
			resposta += "<causa>" + causa + "</causa>\n";
		}
		resposta += "</erro>";
		return resposta;
	}


}
