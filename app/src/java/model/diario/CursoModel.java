package model.diario;

public class CursoModel {
	private static final int ID_INDEFINIDO = -1;
	private int id, id_depto, horas_total;
	private String nome, modalidade;

	public CursoModel(int id_depto, String nome, int horas_total, String modalidade){
		this.id = ID_INDEFINIDO;
		this.id_depto = id_depto;
		this.nome = nome;
		this.horas_total = horas_total;
		this.modalidade = modalidade;
	}

	public CursoModel(int id, int id_depto, String nome, int horas_total, String modalidade){
		this.id = id;
		this.id_depto = id_depto;
		this.nome = nome;
		this.horas_total = horas_total;
		this.modalidade = modalidade;
	}

	public int getId(){
		return id;
	}

	public int getId_depto() {
		return id_depto;
	}

	public int getHoras_total() {
		return horas_total;
	}

	public String getNome() {
		return nome;
	}

	public String getModalidade() {
		return modalidade;
	}

}
