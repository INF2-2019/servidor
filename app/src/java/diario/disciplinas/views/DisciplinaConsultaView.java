package diario.disciplinas.views;

import diario.disciplinas.model.DisciplinaModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.PrintWriter;
import java.util.Set;

public class DisciplinaConsultaView extends View<Set<DisciplinaModel>> {

	public DisciplinaConsultaView(Set<DisciplinaModel> DisciplinaModel) {
		super(DisciplinaModel);
	}

	private static Element criarElementoDocument(Document documento, DisciplinaModel c) {

		Element elemento = documento.createElement("disciplina");

		Element id = documento.createElement("id");
		Element id_depto = documento.createElement("id-turmas");
		Element nome = documento.createElement("nome");
		Element carga_horaria_min = documento.createElement("carga-horaria-min");

		id.appendChild(documento.createTextNode("" + c.getId()));
		id_depto.appendChild(documento.createTextNode("" + c.getIdTurmas()));
		nome.appendChild(documento.createTextNode(c.getNome()));
		carga_horaria_min.appendChild(documento.createTextNode("" + c.getCargaHorariaMin()));

		elemento.appendChild(id);
		elemento.appendChild(id_depto);
		elemento.appendChild(nome);
		elemento.appendChild(carga_horaria_min);

		return elemento;
	}

	@Override
	public void render(PrintWriter writer) throws RenderException {
		try {
			Document cursosEmDocument = disciplinaParaDocument(data);
			writer.write(Conversores.converterDocumentEmXMLString(cursosEmDocument));
		} catch (Exception ex) {
			throw new RenderException(ex);
		}
	}

	private Document disciplinaParaDocument(Set<DisciplinaModel> disciplinas) throws ParserConfigurationException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder construtor = dbf.newDocumentBuilder();
		Document documento = construtor.newDocument();

		Element root = documento.createElement("disciplinas");

		for (DisciplinaModel disciplina : disciplinas) {
			if (disciplina != null) {
				root.appendChild(criarElementoDocument(documento, disciplina));
			}
		}

		documento.appendChild(root);

		return documento;
	}

}
