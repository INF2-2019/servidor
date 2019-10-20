package model.diario;

<<<<<<< HEAD
import model.Model;

public class CursoModel extends Model {
=======
public class CursoModel {
	private static final int ID_INDEFINIDO = -1;
>>>>>>> cursos
	private int id, idDepto, horasTotal;
	private String nome, modalidade;

	public CursoModel(int idDepto, String nome, int horasTotal, String modalidade){
<<<<<<< HEAD
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
=======
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
>>>>>>> cursos
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
