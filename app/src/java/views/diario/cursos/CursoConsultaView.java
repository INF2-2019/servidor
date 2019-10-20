package views.diario.cursos;

import model.diario.CursoModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;
import views.RenderException;
import views.View;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Set;

public class CursoConsultaView extends View<Set<CursoModel>> {

	public CursoConsultaView(Set<CursoModel> cursoModels) {
		super(cursoModels);
	}

	@Override
	public void render(PrintWriter writer) throws RenderException {
		try{
			writer.write(setParaXML(data));
		}catch (Exception ex){
			throw new RenderException(ex);
		}
	}

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
