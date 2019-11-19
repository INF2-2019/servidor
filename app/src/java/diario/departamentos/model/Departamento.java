package diario.departamentos.model;

public class Departamento {

	private int id, idCampi;
	private String nome, nomeCampi, cidade, uf;

	public Departamento() {
		this(0, 0, "", "", "", "");
	}

	public Departamento(int idCampi, String nome) {
		this(0, idCampi, nome, "", "", "");
	}

	public Departamento(int id, int idCampi, String nome, String nomeCampi, String cidade, String uf) {
		this.id = id;
		this.idCampi = idCampi;
		this.nome = nome;
		this.nomeCampi = nomeCampi;
		this.cidade = cidade;
		this.uf = uf;
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

	public String getNomeCampi() {
		return nomeCampi;
	}

	public void setNomeCampi(String nomeCampi) {
		this.nomeCampi = nomeCampi;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
