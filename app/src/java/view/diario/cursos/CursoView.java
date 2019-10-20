package view.diario.cursos;

import model.diario.CursoModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.Set;

public class CursoView {

	public static String setParaXML(Set<CursoModel> cursos) throws TransformerException, ParserConfigurationException {
		// Converte para Document e então retorna como String
		Document cursosEmDocument = cursoParaDocument(cursos);
		return Conversores.converterDocumentEmXMLString(cursosEmDocument);
	}

	private static Document cursoParaDocument(Set<CursoModel> cursos) throws ParserConfigurationException {
		// Cria o documento
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder construtor = dbf.newDocumentBuilder();
		Document documento = construtor.newDocument();

		// Cria o elemento raiz "Resultado"
		Element resultado = documento.createElement("Resultado");
		// Adiciona um elemento criado para cada um dos elementos do Set "cursos" ao resultado

		for (CursoModel curso : cursos) {
			resultado.appendChild(criarElementoDocument(documento, curso)); // para cada curso, adiciona um ELemento
		}

		documento.appendChild(resultado);

		return documento;
	}

	// Método que cria uma tag Elemento no documento XML;
	// A tag Elemento compreende todos atributos da classe em forma de outras tags, como id, por exemplo
	private static Element criarElementoDocument(Document documento, CursoModel c) {
		// Recebe um documento como parâmetro para criar uma tag dentro deste
		Element elemento = documento.createElement("Elemento");

		// Adiciona tags da classe
		Element id = documento.createElement("id");
		Element id_depto = documento.createElement("id-depto");
		Element nome = documento.createElement("nome");
		Element horas_total = documento.createElement("horas-total");
		Element modalidade = documento.createElement("modalidade");

		// Adiciona conteúdo das tags
		id.appendChild(documento.createTextNode(""+c.getId())); // o "" no início serve para passar como String
		id_depto.appendChild(documento.createTextNode(""+c.getId_depto()));
		nome.appendChild(documento.createTextNode(c.getNome()));
		horas_total.appendChild(documento.createTextNode(""+c.getHoras_total()));
		modalidade.appendChild(documento.createTextNode(c.getModalidade()));

		// Adiciona no "elemento raiz"
		elemento.appendChild(id);
		elemento.appendChild(id_depto);
		elemento.appendChild(nome);
		elemento.appendChild(horas_total);
		elemento.appendChild(modalidade);

		return elemento;
	}

}
