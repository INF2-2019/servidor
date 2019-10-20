package views.diario.departamentos;

import java.util.List;
import model.diario.DepartamentoModel;

public class DepartamentoView {

	public static String xmlDepartamentos(List<DepartamentoModel> deptos) {
		String xml = "<departamentos>";
		for(DepartamentoModel depto : deptos) {
			xml += xmlDepartamento(depto);
		}
		xml += "</departamentos>";
		return xml;
	}

	public static String xmlDepartamento(DepartamentoModel depto) {
		return	"<departamento>"
					+ "<id>" + depto.getId() + "</id>"
					+ "<id-campi>" + depto.getIdCampi() + "</id-campi>"
					+ "<nome>" + depto.getNome() + "</nome>"
				+ "</departamento>";
	}

}
