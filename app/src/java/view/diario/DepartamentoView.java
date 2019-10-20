package view.diario;

import java.util.List;
import model.diario.departamentos.Departamento;

public class DepartamentoView {
	
	public static String xmlDepartamentos(List<Departamento> deptos) {
		String xml = "<departamentos>";
		for(Departamento depto : deptos) {
			xml += xmlDepartamento(depto);
		}
		xml += "</departamentos>";
		return xml;
	}
	
	public static String xmlDepartamento(Departamento depto) {
		return	"<departamento>"
					+ "<id>" + depto.getId() + "</id>"
					+ "<id-campi>" + depto.getIdCampi() + "</id-campi>"
					+ "<nome>" + depto.getNome() + "</nome>"
				+ "</departamento>";
	}
	
}
