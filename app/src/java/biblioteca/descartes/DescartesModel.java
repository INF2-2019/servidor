/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.descartes;

import java.sql.Date;

/**
 * @author juanr
 */
public class DescartesModel {
	int idAcervo;
	Date dataDescarte;
	String motivos, operador;

	public DescartesModel() {
	}

	public DescartesModel(int idAcervo, Date dataDescarte, String motivos, String operador) {
		this.idAcervo = idAcervo;
		this.dataDescarte = dataDescarte;
		this.motivos = motivos;
		this.operador = operador;
	}


	public int getIdAcervo() {
		return idAcervo;
	}

	public void setIdAcervo(int idAcervo) {
		this.idAcervo = idAcervo;
	}

	public Date getDataDescarte() {
		return dataDescarte;
	}

	public void setDataDescarte(Date dataDescarte) {
		this.dataDescarte = dataDescarte;
	}

	public String getMotivos() {
		return motivos;
	}

	public void setMotivos(String motivos) {
		this.motivos = motivos;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}


}
