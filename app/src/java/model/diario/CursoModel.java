package model.diario;

public class CursoModel {
	private static final int ID_INDEFINIDO = -1;
	private int id, idDepto, horasTotal;
	private String nome, modalidade;

	public CursoModel(int idDepto, String nome, int horasTotal, String modalidade){
		this.id = ID_INDEFINIDO;
		this.idDepto = idDepto;
		this.nome = nome;
		this.horasTotal = horasTotal;
		this.modalidade = modalidade;
	}

	public CursoModel(int id, int idDepto, String nome, int horasTotal, String modalidade){
		this.id = id;
		this.idDepto = idDepto;
		this.nome = nome;
		this.horasTotal = horasTotal;
		this.modalidade = modalidade;
	}

	public int getId(){
		return id;
	}

	public int getIdDepto() {
		return idDepto;
	}

	public int getHorasTotal() {
		return horasTotal;
	}

	public String getNome() {
		return nome;
	}

	public String getModalidade() {
		return modalidade;
	}

}
