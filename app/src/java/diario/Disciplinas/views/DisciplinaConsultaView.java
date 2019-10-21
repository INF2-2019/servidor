
package diario.disciplinas.views;

import java.io.PrintWriter;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import diario.disciplinas.model.DisciplinaModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;
import diario.disciplinas.views.RenderException;
import diario.disciplinas.views.View;
 
public class DisciplinaConsultaView extends View<Set<DisciplinaModel>> {

    public DisciplinaConsultaView(Set<DisciplinaModel> DisciplinaModel) {
		super(DisciplinaModel);
	}
    @Override
    public void render(PrintWriter writer) throws RenderException {
       try{
		Document cursosEmDocument = DisciplinaParaDocument(data);
                System.out.println("aaaa");
			writer.write(Conversores.converterDocumentEmXMLString(cursosEmDocument));
		}catch (Exception ex){
			throw new RenderException(ex);
		}
    }
    private Document DisciplinaParaDocument(Set<DisciplinaModel> cursos) throws ParserConfigurationException {
		// Cria o documento
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder construtor = dbf.newDocumentBuilder();
		Document documento = construtor.newDocument();

		// Cria o elemento raiz "Resultado"
		Element resultado = documento.createElement("disciplinas");

		// Adiciona um elemento criado para cada um dos elementos do Set "cursos" ao resultado
		for (DisciplinaModel curso : cursos) {
			resultado.appendChild(criarElementoDocument(documento, curso)); // para cada curso, adiciona um ELemento
		}

		documento.appendChild(resultado);

		return documento;
	}
    private static Element criarElementoDocument(Document documento, DisciplinaModel c) {
		// Recebe um documento como parâmetro para criar uma tag dentro deste
		Element elemento = documento.createElement("disciplina");

		// Adiciona tags da classe
		Element id = documento.createElement("id");
		Element id_depto = documento.createElement("id-turmas");
		Element nome = documento.createElement("nome");
		Element carga_horaria_min = documento.createElement("carga-horaria-min");
		

		// Adiciona conteúdo das tags
		id.appendChild(documento.createTextNode(""+c.getId())); // o "" no início serve para passar como String
		id_depto.appendChild(documento.createTextNode(""+c.getIdTurmas()));
		nome.appendChild(documento.createTextNode(c.getNome()));
		carga_horaria_min.appendChild(documento.createTextNode(""+c.getCargaHorariaMin()));
		

		// Adiciona no "elemento raiz"
		elemento.appendChild(id);
		elemento.appendChild(id_depto);
		elemento.appendChild(nome);
		elemento.appendChild(carga_horaria_min);

		return elemento;
	}
    
}