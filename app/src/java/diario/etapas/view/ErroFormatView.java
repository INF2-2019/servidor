package diario.etapas.view;

import diario.etapas.RenderException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.PrintWriter;

public class ErroFormatView extends View {

	public ErroFormatView(Exception excecao) {
		super(excecao);
	}

	private static Document criarErroXML(Exception excecao) throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder construtor = dbf.newDocumentBuilder();
		Document documento = construtor.newDocument();

		Element erro = documento.createElement("erro");
		Element msg = documento.createElement("mensagem");

		msg.appendChild(documento.createTextNode("Erro: algum número foi submetido no formato errado"));
		erro.appendChild(msg);

		documento.appendChild(erro);

		return documento;
	}

	@Override
	public void render(PrintWriter writer) throws RenderException {
		try {
			writer.write(Conversores.converterDocumentEmXMLString(criarErroXML((Exception) data)));
		} catch (ParserConfigurationException | TransformerException ex) {
			throw new RenderException(ex);
		}
	}

}
