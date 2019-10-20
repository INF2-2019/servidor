package utils;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class Conversores {

    public static String converterDocumentEmXMLString(Document doc) throws TransformerException {
        DOMSource dom = new DOMSource(doc);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformador = tf.newTransformer();

        transformador.setOutputProperty(OutputKeys.METHOD, "xml");
        transformador.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");

        java.io.StringWriter escreveLinhas = new java.io.StringWriter();
        StreamResult resultado = new StreamResult(escreveLinhas);

        transformador.transform(dom, resultado);
        return escreveLinhas.toString();
    }

}
