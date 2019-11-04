package diario.departamentos.view;

import diario.departamentos.model.Departamento;
import java.util.List;

public class View {
	
	public static String XMLConsulta(List<Departamento> deptos) {
		String xml = "<departamentos>";
		for(Departamento depto : deptos) {
			xml += XMLDepartamento(depto);
		}
		xml += "</departamentos>";
		return xml;
	}
	
	public static String XMLDepartamento(Departamento depto) {
		String xml =
				"<departamento>"
					+ "<id>" + depto.getId() + "</id>"
					+ "<id-campi>" + depto.getIdCampi() + "</id-campi>"
					+ "<nome>" + depto.getNome() + "</nome>"
				+ "</departamento>";
		return xml;
	}
	
}
