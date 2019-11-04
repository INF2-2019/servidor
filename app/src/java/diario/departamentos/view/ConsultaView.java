package diario.departamentos.view;

import diario.departamentos.model.Departamento;
import java.util.List;

public class ConsultaView {

	public static String consulta(List<Departamento> deptos, Exception ex) {
		String xml = "<consulta><departamentos>";
		if(ex == null) {
			for(Departamento depto : deptos) {
				xml += xmlDepartamento(depto);
			}
		}
		xml += "</departamentos>";
		if(ex == null) {
			xml += SucessoView.sucesso("Departamentos consultados com sucesso");
		} else {
			xml += ErroView.erro("Falha ao consultar departamentos", ex);
		}
		xml += "</consulta>";
		return xml;
	}
	
	public static String xmlDepartamento(Departamento depto) {
		String xml =
				"<departamento>" +
					"<id>" + depto.getId() + "</id>" +
					"<id-campi>" + depto.getIdCampi() + "</id-campi>" +
					"<nome>" + depto.getNome() + "</nome>" +
				"</departamento>";
		return xml;
	}
}
