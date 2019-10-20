

package model.diario;

public class DisciplinaModel {
    private static final int IdIndefinido = -1;
	private int id, idTurmas, cargaHorariaMin;
	private String nome;

    public DisciplinaModel(int id,int idTurmas, int cargaHorariaMin, String nome) {
        this.id = id;
        this.idTurmas = idTurmas;
        this.cargaHorariaMin = cargaHorariaMin;
        this.nome = nome;
    }

    public DisciplinaModel(int idTurmas, int cargaHorariaMin, String nome) {
        this(IdIndefinido, idTurmas,cargaHorariaMin,nome);
    }
    
    public int getId() {
        return id;
    }
    
    public int getIdTurmas() {
        return idTurmas;
    }

    public void setIdTurmas(int idTurmas) {
        this.idTurmas = idTurmas;
    }

    public int getCargaHorariaMin() {
        return cargaHorariaMin;
    }

    public void setCargaHorariaMin(int cargaHorariaMin) {
        this.cargaHorariaMin = cargaHorariaMin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
