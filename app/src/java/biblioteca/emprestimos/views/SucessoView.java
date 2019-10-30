package biblioteca.emprestimos.views;

import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

public class SucessoView extends View<String> {
    
    boolean isMulta=false;
    double multa;
    
    public SucessoView(String mensagem) {
        super(mensagem);
    }
    
    public SucessoView(String mensagem, double multa){
        super(mensagem);
        this.multa = multa;
        isMulta = true;
    }

    @Override
    public void render(PrintWriter writer) throws RenderException {
        Document SucessoemDocument;
        try {
            SucessoemDocument = SucessoParaDocument(data);
            writer.write(Conversores.converterDocumentEmXMLString(SucessoemDocument));
        } catch (ParserConfigurationException ex) {
            System.err.println("Não foi possivel fazer a conversão. Erro: " + ex.toString());
        } catch (TransformerException ex) {
            System.err.println("Não foi possivel fazer a conversão. Erro: " + ex.toString());
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
        if(isMulta){
            Element mult = documento.createElement("multa");
            mult.appendChild(documento.createTextNode(""+multa));
            sucesso.appendChild(mult);
        }
        return documento;
    }

}
