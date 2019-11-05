package diario.turmas.models;

public class TurmaModel {

	private int id, idCursos;
	private String nome;

	private static final int ID_INDEFINIDO = -1, IDCURSOS_INDEFINIDO = 0;
	private static final String NOME_INDEFINIDO = "";

	public TurmaModel() {
		this(ID_INDEFINIDO, IDCURSOS_INDEFINIDO, NOME_INDEFINIDO);
	}

	public TurmaModel(int id) {
		this(id, IDCURSOS_INDEFINIDO, NOME_INDEFINIDO);
	}

	public TurmaModel(int id, int idCursos) {
		this(id, idCursos, NOME_INDEFINIDO);
	}

	public TurmaModel(int id, int idCursos, String nome) {
		this.id = id;
		this.idCursos = idCursos;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCursos() {
		return idCursos;
	}

	public void setIdCursos(int idCursos) {
		this.idCursos = idCursos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
