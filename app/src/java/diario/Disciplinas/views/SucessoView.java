package diario.disciplinas.views;

import java.io.PrintWriter;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;
import diario.disciplinas.views.RenderException;
import diario.disciplinas.views.View;
public class SucessoView extends View<String> {

    public SucessoView(String mensagem) {
        super(mensagem);
    }

    @Override
    public void render(PrintWriter writer) throws RenderException {
        Document SucessoemDocument;
        try {
            SucessoemDocument = SucessoParaDocument(data);
            System.out.println(data);
            System.out.println(SucessoemDocument);
            writer.write(Conversores.converterDocumentEmXMLString(SucessoemDocument));
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SucessoView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(SucessoView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Document SucessoParaDocument(String data) throws ParserConfigurationException {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder construtor = dbf.newDocumentBuilder();
		Document documento = construtor.newDocument();

		// Cria o elemento raiz "Resultado"
		Element sucesso = documento.createElement("sucesso");
                Element informacao = documento.createElement("informacao");
                informacao.appendChild(documento.createTextNode(data));
                sucesso.appendChild(informacao);
                documento.appendChild(sucesso);
        return documento;
    }
    
}