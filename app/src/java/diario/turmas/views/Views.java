package diario.turmas.views;

import diario.turmas.models.TurmaModel;

import java.util.ArrayList;

public class Views {

	public static String retornaConsulta(ArrayList<TurmaModel> arr) {
		String ret = "<turmas>";
		for (TurmaModel turma : arr) {
			ret += "<turma><id>" + turma.getId() + "</id>";
			ret += "<id-cursos>" + turma.getIdCursos() + "</id-cursos>";
			ret += "<nome>" + turma.getNome() + "</nome></turma>";
		}
		ret += "</turmas>";
		if (arr.isEmpty()) {
			return "<turmas><mensagem>Não há nenhuma turma</mensagem></turmas>";
		}
		return ret;
	}

	public static String retornaSucesso(String msg) {
		String ret = "<sucesso>";
		ret += msg;
		ret += "</sucesso>";
		return ret;
	}

	public static String retornaErro(String msg) {
		String ret = "<erro>";
		ret += msg;
		ret += "</erro>";
		return ret;
	}

}
