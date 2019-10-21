package views.diario.etapas;

import model.diario.EtapasModel;
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
import java.util.Set;

public class EtapasConsultaView extends View<Set<EtapasModel>> {

    public EtapasConsultaView(Set<EtapasModel> etapasModels) {
	super(etapasModels);
    }

    @Override
    public void render(PrintWriter writer) throws RenderException {
	try {
	    writer.write(setParaXML(data));
	} catch (Exception ex) {
	    throw new RenderException(ex);
	}
    }

    public static String setParaXML(Set<EtapasModel> etapas) throws TransformerException, ParserConfigurationException {
	// Converte para Document e então retorna como String
	Document etapasEmDocument = etapaParaDocument(etapas);
	return Conversores.documentParaXMLString(etapasEmDocument);
    }

    private static Document etapaParaDocument(Set<EtapasModel> etapas) throws ParserConfigurationException {
	// Cria o documento
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder construtor = dbf.newDocumentBuilder();
	Document documento = construtor.newDocument();

	// Cria o elemento raiz "Resultado"
	Element resultado = documento.createElement("etapas");

	// Adiciona um elemento criado para cada um dos elementos do Set "etapas" ao resultado
	for (EtapasModel etapa : etapas) {
	    resultado.appendChild(criarElementoDocument(documento, etapa)); // para cada curso, adiciona um ELemento
	}

	documento.appendChild(resultado);
	return documento;
    }

    // Método que cria uma tag Elemento no documento XML;
    // A tag Elemento compreende todos atributos da classe em forma de outras tags, como id, por exemplo
    private static Element criarElementoDocument(Document documento, EtapasModel e) {
	// Recebe um documento como parâmetro para criar uma tag dentro deste
	Element elemento = documento.createElement("etapa");

	// Adiciona tags da classe
	Element id = documento.createElement("id");
	Element ano = documento.createElement("ano");
	Element valor = documento.createElement("valor");

	// Adiciona conteúdo das tags
	id.appendChild(documento.createTextNode("" + e.getId())); // o "" no início serve para passar como String
	ano.appendChild(documento.createTextNode("" + e.getAno()));
	valor.appendChild(documento.createTextNode("" + e.getValor()));

	// Adiciona no "elemento raiz"
	elemento.appendChild(id);
	elemento.appendChild(ano);
	elemento.appendChild(valor);

	return elemento;

    }

    /*public static Document criarSucessoXML() {
	}*/
}
