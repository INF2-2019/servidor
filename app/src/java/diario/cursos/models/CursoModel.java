package diario.cursos.models;

public class CursoModel extends Model {
	private int id, idDepto, horasTotal;
	private String nome, modalidade;

	public CursoModel(int idDepto, String nome, int horasTotal, String modalidade){
		// Para evitar repetição, reaproveitamento do construtor completo
		this(ID_INDEFINIDO, idDepto, nome, horasTotal, modalidade);
	}

	public CursoModel(int id, int idDepto, String nome, int horasTotal, String modalidade){
		// Usar setters para validar os dados de maneira isolada
		this.setId(id);
		this.setIdDepto(idDepto);
		this.setNome(nome);
		this.setHorasTotal(horasTotal);
		this.setModalidade(modalidade);
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

	public void setId(int id) {
		this.id = id;
	}

	public void setIdDepto(int idDepto) {
		this.idDepto = idDepto;
	}

	public void setHorasTotal(int horasTotal) {
		this.horasTotal = horasTotal;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}
}
