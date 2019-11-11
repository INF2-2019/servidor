
package diario.professores_disciplinas.Model;

import java.util.Map;

public class ProfessoresDisciplinasModel {
    private int idProfessor, idDisciplina;

    public ProfessoresDisciplinasModel(int idProfessor, int idDisciplina) {
        this.idProfessor = idProfessor;
        this.idDisciplina = idDisciplina;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }
    
    public Object[] retornarValoresRestantes(Map<String, String> parametros) {
		Object[] retorno = new Object[2];
                
                retorno[0] = parametros.get("id-prefessor");
                retorno[1] = parametros.get("id-disciplina");

		return retorno;
	}
    
    
}
