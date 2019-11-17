package diario.transferencia.view;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.PrintWriter;

public class SucessoView extends View {

	public SucessoView(String msg) {
		super(msg);
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
		DocumentBuilder docBuilder = dbf.newDocumentBuilder();
		Document document = docBuilder.newDocument();

		Element sucesso = document.createElement("sucesso");
		Element msg = document.createElement("mensagem");

		msg.appendChild(document.createTextNode(mensagem));
		sucesso.appendChild(msg);

		document.appendChild(sucesso);
		return document;
	}
}
