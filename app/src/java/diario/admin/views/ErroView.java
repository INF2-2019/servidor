package diario.admin.views;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.PrintWriter;

public class ErroView extends View<String>{
	private String causa;

	public ErroView(String mensagem) {
		super(mensagem);
	}

	public ErroView(String mensagem, String causa){
		super(mensagem);
		this.causa = causa;
	}

		@Override
		public void render(PrintWriter writer) throws RenderException{
			try{
				Document dom = gerarDocument(this.data, this.causa);
				writer.println(Conversores.converterDocumentEmXMLString(dom));
			}catch (ParserConfigurationException | TransformerException ex){
				throw new RenderException(ex);
			}
		}

		public Document gerarDocument(String mensagem) throws ParserConfigurationException {
			return gerarDocument(mensagem, null);
		}

		public Document gerarDocument(String mensagem, String causaStr) throws ParserConfigurationException {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.newDocument();

			Element root = dom.createElement("erro");
			dom.appendChild(root);

			Element msg = dom.createElement("mensagem");
			root.appendChild(msg);
			msg.setNodeValue(mensagem);

			if(causaStr != null){
				Element causa = dom.createElement("causa");
				root.appendChild(causa);
				causa.setNodeValue(causaStr);
			}

			return dom;
		}

}
