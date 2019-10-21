package diario.campi.model;


public class CampiModel extends Model {
	private int id;
	private String nome, cidade, uf;

	public CampiModel(String nome, String cidade, String uf){
		// Para evitar repetição, reaproveitamento do construtor completo
		this(ID_INDEFINIDO, nome, cidade, uf);
	}

	public CampiModel(int id, String nome, String cidade, String uf){
		// Usar setters para validar os dados de maneira isolada
		this.setId(id);		
		this.setNome(nome);
		this.setCidade(cidade);
		this.setUf(uf);
	}

	public int getId(){
		return id;
	}

	public String getCidade() {
		return cidade;
	}

	public String getNome() {
		return nome;
	}

	public String getUf() {
		return uf;
	}

	public void setId(int id) {
		this.id = id;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}
