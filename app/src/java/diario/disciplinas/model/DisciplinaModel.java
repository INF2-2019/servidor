package diario.disciplinas.model;

import diario.disciplinas.model.Model;
import java.util.Map;

public class DisciplinaModel extends Model {

    private int id, idTurmas, cargaHorariaMin;
    private String nome;

    public DisciplinaModel(int id, int idTurmas, String nome, int cargaHorariaMin) {
        this.id = id;
        this.idTurmas = idTurmas;
        this.cargaHorariaMin = cargaHorariaMin;
        this.nome = nome;
    }
    public Object[] retornarValoresRestantes(Map<String, String> parametros) {
		Object[] retorno = new Object[4];
		retorno[0] = id;

		if (!parametros.containsKey("id-turmas")) {
			retorno[1] = idTurmas;
		} else {
			retorno[1] = parametros.get("id-turmas");
		}

		if (!parametros.containsKey("nome")) {
			retorno[2] = nome;
		} else {
			retorno[2] = parametros.get("nome");
		}

		if (!parametros.containsKey("carga-horaria-min")) {
			retorno[3] = cargaHorariaMin;
		} else {
			retorno[3] = parametros.get("horas");
		}

		

		return retorno;
	}
    public DisciplinaModel(int idTurmas, int cargaHorariaMin, String nome) {
        this(ID_INDEFINIDO, idTurmas, nome, cargaHorariaMin);
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
