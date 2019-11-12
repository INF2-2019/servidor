package diario.professores_disciplinas.Model;

import diario.professores_disciplinas.View.ExcecaoConteudoVinculado;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

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

	public static Map<String, String> definirMap(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();

		if (req.getParameter("id-professores") != null) {
			dados.put("id-professores", req.getParameter("id-professores"));
		}

		if (req.getParameter("id-disciplinas") != null) {
			dados.put("id-disciplinas", req.getParameter("id-disciplinas"));
		}

		return dados;
	}

}
