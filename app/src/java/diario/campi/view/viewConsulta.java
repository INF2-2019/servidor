/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.campi.view;

import java.sql.Date;
import java.text.SimpleDateFormat;

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
	
	public static String XMLConsultaAlunos(String alunos) {
		String xml = "<alunos>";
		xml+=alunos;
		xml += "</alunos>";
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
	
	public static String XMLAlunoCompleto(int id, String nome, String email, String sexo, Date nascimento, String logradouro, int numero, String complemento, String bairro, String cidade, int cep, String uf, String foto) {
		String data = new SimpleDateFormat("dd/MM/yyyy").format(nascimento);
		String xml =
				"<aluno>"
					+ "<id>" + id + "</id>"
					+ "<nome>" + nome + "</nome>"
					+ "<email>" + email + "</email>"
					+ "<sexo>" + sexo + "</sexo>"
					+ "<nascimento>" + data + "</nascimento>"
					+ "<logradouro>" + logradouro + "</logradouro>"
					+ "<numero>" + numero + "</numero>"
					+ "<complemento>" + complemento + "</complemento>"
					+ "<bairro>" + bairro + "</bairro>"
					+ "<cidade>" + cidade + "</cidade>"
					+ "<cep>" + cep + "</cep>"
					+ "<uf>" + uf + "</uf>"
					+ "<foto>" + foto + "</foto>"
				+ "</aluno>";
		return xml;
	}
}
