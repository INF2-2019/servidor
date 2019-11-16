package diario.relatorios.relatorio9;

import java.util.List;


public class XmlProf{
    
    public static String xmlProf(int id, String nome){
            String xml =
				"<professor>" +
					"<id>" + id + "</id>" +
					"<nome>" + nome + "</nome>" +
					"<cursos>";
            return xml;
    };
	
	public static String xmlFinal(String xml, String[] dis, int[] cargas){ 
			for (int i = 0; i < dis.length; i++) {
				xml+="<disciplina>";
				xml+= "<nome>" + dis[i] + "</nome>";
				xml+= "<carga>" + cargas[i] + "</carga>";
				xml+="</disciplina>";
			}
            return xml;
    };
	
	public static String xmlCurso(String xml, String nomeCurso) {
		xml+=
				"<curso>" +
					"<nome>"+ nomeCurso+ "</nome>"+
					"<disciplinas>";
		return xml;
	}
}