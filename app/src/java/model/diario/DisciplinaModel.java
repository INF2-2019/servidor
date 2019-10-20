

package model.diario;


public class DisciplinaModel {
    private static final int ID_INDEFINIDO = -1;
	private int id, id_turmas, carga_horaria_min;
	private String nome;

    public DisciplinaModel(int id_turmas, int carga_horaria_min, String nome) {
        this.id = ID_INDEFINIDO;
        this.id_turmas = id_turmas;
        this.carga_horaria_min = carga_horaria_min;
        this.nome = nome;
    }

    public DisciplinaModel(int id, int id_turmas, int carga_horaria_min, String nome) {
        this.id = id;
        this.id_turmas = id_turmas;
        this.carga_horaria_min = carga_horaria_min;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }
     
    public int getId_turmas() {
        return id_turmas;
    }

    public void setId_turmas(int id_turmas) {
        this.id_turmas = id_turmas;
    }

    public int getCarga_horaria_min() {
        return carga_horaria_min;
    }

    public void setCarga_horaria_min(int carga_horaria_min) {
        this.carga_horaria_min = carga_horaria_min;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
