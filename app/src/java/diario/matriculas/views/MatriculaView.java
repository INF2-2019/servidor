package diario.matriculas.views;

import diario.admin.views.RenderException;
import diario.admin.views.View;
import diario.matriculas.models.Matricula;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.PrintWriter;

public class MatriculaView extends View<Matricula> {

	public MatriculaView(Matricula matricula) {
		super(matricula);
	}

	@Override
	public void render(PrintWriter writer) throws RenderException {
		Matricula m = this.data;
		try {
			Document DOM = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element matricula = m.toElement();
			DOM.appendChild(matricula);
			writer.println(Conversores.converterDocumentEmXMLString(DOM));
		} catch (ParserConfigurationException | TransformerException ex) {
			throw new RenderException(ex);
		}
	}
}
