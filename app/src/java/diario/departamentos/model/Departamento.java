package diario.departamentos.model;

public class Departamento {
	
	private int id, idCampi;
	private String nome;
	
	public Departamento() {
		this(0, 0, "");
	}
	
	public Departamento(int idCampi, String nome) {
		this(0, idCampi, nome);
	}
	
	public Departamento(int id, int idCampi, String nome) {
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
