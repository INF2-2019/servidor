package model.diario;

import model.Model;

public class DepartamentoModel extends Model {
	
	int id, idCampi;
	String nome;
	
	public DepartamentoModel() {
		this(0, 0, "");
	}

	public DepartamentoModel(int id, int idCampi, String nome) {
		this.id = id;
		this.idCampi = idCampi;
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCampi() {
		return idCampi;
	}

	public void setIdCampi(int idCampi) {
		this.idCampi = idCampi;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
