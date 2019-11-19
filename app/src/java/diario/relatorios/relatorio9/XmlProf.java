package diario.relatorios.relatorio9;

public class XmlProf {

	public static String xmlProf(int id, String nome) {
		String xml
				= "<professor>"
				+ "<id>" + id + "</id>"
				+ "<nome>" + nome + "</nome>"
				+ "<cursos>";
		return xml;
	}

	;
	
	public static String xmlFinal(String xml, String dis, String cargas) {
		xml += "<disciplina>";
		xml += "<nome>" + dis + "</nome>";
		xml += "<carga>" + cargas + "</carga>";
		xml += "</disciplina>";
		return xml;
	}

	;
	
	public static String xmlCurso(String xml, String nomeCurso) {
		xml
				+= "<curso>"
				+ "<nome>" + nomeCurso + "</nome>"
				+ "<disciplinas>";
		return xml;
	}
}
