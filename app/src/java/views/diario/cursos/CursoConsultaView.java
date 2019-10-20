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
<<<<<<< refs/remotes/origin/master:app/src/java/views/diario/cursos/CursoConsultaView.java
import java.io.PrintWriter;
=======
import javax.xml.transform.TransformerException;
import java.sql.SQLException;
>>>>>>> Adicionado XML de erro:app/src/java/view/diario/cursos/CursoView.java
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
		Element idDepto = documento.createElement("id-depto");
		Element nome = documento.createElement("nome");
		Element horasTotal = documento.createElement("horas-total");
		Element modalidade = documento.createElement("modalidade");

		// Adiciona conteúdo das tags
		id.appendChild(documento.createTextNode(""+c.getId())); // o "" no início serve para passar como String
<<<<<<< refs/remotes/origin/master:app/src/java/views/diario/cursos/CursoConsultaView.java
		id_depto.appendChild(documento.createTextNode(""+c.getIdDepto()));
		nome.appendChild(documento.createTextNode(c.getNome()));
		horas_total.appendChild(documento.createTextNode(""+c.getHorasTotal()));
=======
		idDepto.appendChild(documento.createTextNode(""+c.getIdDepto()));
		nome.appendChild(documento.createTextNode(c.getNome()));
		horasTotal.appendChild(documento.createTextNode(""+c.getHorasTotal()));
>>>>>>> Adicionado XML de erro:app/src/java/view/diario/cursos/CursoView.java
		modalidade.appendChild(documento.createTextNode(c.getModalidade()));

		// Adiciona no "elemento raiz"
		elemento.appendChild(id);
		elemento.appendChild(idDepto);
		elemento.appendChild(nome);
		elemento.appendChild(horasTotal);
		elemento.appendChild(modalidade);

		return elemento;
	}

	public static Document criarErroXML(SQLException excecao) throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder construtor = dbf.newDocumentBuilder();
		Document documento = construtor.newDocument();

		Element erro = documento.createElement("Erro");
		Element status = documento.createElement("status");
		Element info = documento.createElement("informacao");

		status.appendChild(documento.createTextNode(""+excecao.getErrorCode()));
		info.appendChild(documento.createTextNode(excecao.getMessage()));
		erro.appendChild(status);
		erro.appendChild(info);

		documento.appendChild(erro);

		return documento;
	}

	public static Document criarErroXML(Exception excecao) throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder construtor = dbf.newDocumentBuilder();
		Document documento = construtor.newDocument();

		Element erro = documento.createElement("Erro");
		Element status = documento.createElement("status");
		Element info = documento.createElement("informacao");

		status.appendChild(documento.createTextNode(""+excecao.getLocalizedMessage()));
		info.appendChild(documento.createTextNode(excecao.toString()));
		erro.appendChild(status);
		erro.appendChild(info);

		documento.appendChild(erro);

		return documento;
	}

	/*public static Document criarSucessoXML() {

	}*/

}
