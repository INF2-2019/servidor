package views.diario.cursos;

import model.diario.CursoModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;
import views.RenderException;
import views.View;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.PrintWriter;
import java.util.Set;

public class CursoConsultaView extends View<Set<CursoModel>> {

	public CursoConsultaView(Set<CursoModel> cursoModels) {
		super(cursoModels);
	}

	@Override
	public void render(PrintWriter writer) throws RenderException {
		try{
			Document cursosEmDocument = cursoParaDocument(data);
			writer.write(Conversores.converterDocumentEmXMLString(cursosEmDocument));
		}catch (Exception ex){
			throw new RenderException(ex);
		}
	}

	private Document cursoParaDocument(Set<CursoModel> cursos) throws ParserConfigurationException {
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
		id_depto.appendChild(documento.createTextNode(""+c.getIdDepto()));
		nome.appendChild(documento.createTextNode(c.getNome()));
		horas_total.appendChild(documento.createTextNode(""+c.getHorasTotal()));
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
