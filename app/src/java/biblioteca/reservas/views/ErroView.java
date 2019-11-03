/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.reservas.views;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class ErroView extends View {

	public ErroView(Exception excecao) {
		super(excecao);
	}

	@Override
	public void render(PrintWriter writer) throws RenderException {
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
		Element msg = documento.createElement("informacao");
		if (excecao instanceof NumberFormatException || excecao instanceof SQLException) {
			msg.appendChild(documento.createTextNode("Os parâmetros inseridos são inválidos."));
		} else {
			msg.appendChild(documento.createTextNode(excecao.getMessage()));
		}
		erro.appendChild(msg);

		documento.appendChild(erro);

		return documento;
	}

}
