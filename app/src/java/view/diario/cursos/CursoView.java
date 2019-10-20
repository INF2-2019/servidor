package view.diario.cursos;

import model.diario.CursoModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.sql.SQLException;
import java.util.Set;

public class CursoView {

	public static String setParaXML(Set<CursoModel> cursos) {
		// Retorna resultado de cursos
		String xml = "<cursos>";
		for(CursoModel curso : cursos){
			xml += cursoParaXML(curso);
		}
		xml += "</cursos>";

		return xml;
	}

	private static String cursoParaXML(CursoModel c) {
		return (
			"<curso>" +
				"<id>"+c.getId()+"</id>" +
				"<id-depto>"+c.getIdDepto()+"</id-depto>" +
				"<nome>"+c.getNome()+"</nome>" +
				"<horas-total>"+c.getHorasTotal()+"</horas-total>" +
				"<modalidade>"+c.getModalidade()+"</modalidade>" +
			"</curso>"
		);
	}



	public static String criarErroXML(SQLException excecao) {
		return (
			"<erro>"+
				"<status>"+excecao.getErrorCode()+"</status>" +
				"<informacao>"+excecao.getMessage()+"</informacao>" +
			"</erro>"
		);
	}

	public static String criarErroXML(Exception excecao) {
		return (
			"<erro>" +
				"<status>"+excecao.getLocalizedMessage()+"</status>" +
				"<informacao>"+excecao.getCause()+"</informacao>" +
			"</erro>"
		);
	}

	/*public static Document criarSucessoXML() {

	}*/

}
