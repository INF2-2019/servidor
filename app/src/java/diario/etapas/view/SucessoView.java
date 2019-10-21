package diario.etapas.view;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;
import views.RenderException;
import views.View;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.PrintWriter;

public class SucessoView extends View {

	public SucessoView(String mensagem) {
		super(mensagem);
	}

	@Override
	public void render(PrintWriter writer) throws RenderException {
		try {
			writer.write(Conversores.converterDocumentEmXMLString(criarSucessoXML((String) data)));
		} catch (TransformerException | ParserConfigurationException e) {
			throw new RenderException(e);
		}
	}

	private Document criarSucessoXML(String mensagem) throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder construtor = dbf.newDocumentBuilder();
		Document documento = construtor.newDocument();

		Element sucesso = documento.createElement("sucesso");
		Element msg = documento.createElement("mensagem");

		msg.appendChild(documento.createTextNode(mensagem));
		sucesso.appendChild(msg);

		documento.appendChild(sucesso);
		return documento;
	}

}
