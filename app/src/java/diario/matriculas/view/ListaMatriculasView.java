package diario.matriculas.view;

import diario.admin.views.RenderException;
import diario.admin.views.View;
import diario.matriculas.model.Matricula;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.PrintWriter;
import java.util.List;

public class ListaMatriculasView extends View<List<Matricula>> {
	public ListaMatriculasView(List<Matricula> matriculas) {
		super(matriculas);
	}

	@Override
	public void render(PrintWriter writer) throws RenderException {
		try {
			Document DOM = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

			Element root = DOM.createElement("matriculas");

			for (Matricula m : data) {
				root.appendChild(m.toElement());
			}

			DOM.appendChild(root);

			writer.println(Conversores.converterDocumentEmXMLString(DOM));
		} catch (ParserConfigurationException | TransformerException ex) {
			throw new RenderException(ex);
		}
	}
}
