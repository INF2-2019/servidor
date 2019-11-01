package diario.turmas.models;

public class TurmaModel {
    private int id, idCursos;
    private String nome;

    public TurmaModel() {
        this(-1, 0, "");
    }
    
    public TurmaModel(int id) {
        this(id, 0, "");
    }
    
    public TurmaModel(int id, int idCursos) {
        this(id, idCursos, "");
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
