package diario.cursos.view;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.PrintWriter;

public class ErroView extends View {

	public ErroView(Exception excecao) {
		super(excecao);
	}

	@Override
	public void render(PrintWriter writer) throws RenderException{
		try {
			writer.write(Conversores.converterDocumentEmXMLString(criarErroXML((Exception) data)));
		} catch (ParserConfigurationException | TransformerException ex) {
			throw new RenderException(ex);
		}
	}

	private static Document criarErroXML(Exception excecao) throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder construtor = dbf.newDocumentBuilder();
		Document documento = construtor.newDocument();

		Element erro = documento.createElement("erro");
		Element msg = documento.createElement("mensagem");

		msg.appendChild(documento.createTextNode("Os parâmetros inseridos são inválidos."));
		erro.appendChild(msg);

		documento.appendChild(erro);

		return documento;
	}

}
