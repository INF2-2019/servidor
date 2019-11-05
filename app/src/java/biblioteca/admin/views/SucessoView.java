package biblioteca.admin.views;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class SucessoView extends View<String> {
	private Map<String, String> params = new LinkedHashMap<>();

	public SucessoView(String s) {
		super(s);
	}

	@Override
	public void render(PrintWriter writer) throws RenderException {
		try {
			Document dom = gerarDocument(this.data);
			writer.println(Conversores.converterDocumentEmXMLString(dom));
		} catch (ParserConfigurationException | TransformerException ex) {
			throw new RenderException(ex);
		}
	}

	public void addParameter(String tag, String value) {
		params.put(tag, value);
	}

	public void removeParameter(String tag) {
		params.remove(tag);
	}

	public Document gerarDocument(String mensagem) throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document dom = db.newDocument();

		Element root = dom.createElement("sucesso");
		dom.appendChild(root);

		Element msg = dom.createElement("mensagem");
		root.appendChild(msg);
		msg.setTextContent(mensagem);

		for (String key : params.keySet()) {
			Element cur = dom.createElement(key);
			root.appendChild(cur);
			cur.setTextContent(params.get(key));
		}

		return dom;
	}
}
