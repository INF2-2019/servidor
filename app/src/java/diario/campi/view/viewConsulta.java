/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.campi.view;

/**
 *
 * @author WazX
 */
public class viewConsulta {
	public static String XMLConsulta(String campis) {
		String xml = "<campis>";
		xml+=campis;
		xml += "</campis>";
		return xml;
	}
	
	public static String XMLCampi(int id, String nome, String cidade, String uf) {
		String xml =
				"<campi>"
					+ "<id>" + id + "</id>"
					+ "<nome>" + nome + "</nome>"
					+ "<cidade>" + cidade + "</cidade>"
					+ "<uf>" + uf + "</uf>"
				+ "</campi>";
		return xml;
	}
	
	public static String XMLAluno(int id, String nome, String email) {
		String xml =
				"<aluno>"
					+ "<id>" + id + "</id>"
					+ "<nome>" + nome + "</nome>"
					+ "<email>" + email + "</email>"
				+ "</aluno>";
		return xml;
	}
}
