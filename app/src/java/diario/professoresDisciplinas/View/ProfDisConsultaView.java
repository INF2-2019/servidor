package diario.professoresDisciplinas.View;

import diario.professoresDisciplinas.Model.ProfessoresDisciplinasModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.PrintWriter;
import java.util.Set;

public class ProfDisConsultaView extends View<Set<ProfessoresDisciplinasModel>> {

	public ProfDisConsultaView(Set<ProfessoresDisciplinasModel> ProfessoresDisciplinasModel) {
		super(ProfessoresDisciplinasModel);
	}

	private static Element criarElementoDocument(Document documento, ProfessoresDisciplinasModel c) {

		Element elemento = documento.createElement("professor-disciplina");

		Element idProf = documento.createElement("id-professores");
		Element idDis = documento.createElement("id-disciplinas");

		idProf.appendChild(documento.createTextNode("" + c.getIdProfessor()));
		idDis.appendChild(documento.createTextNode("" + c.getIdDisciplina()));

		elemento.appendChild(idProf);
		elemento.appendChild(idDis);

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

	private Document disciplinaParaDocument(Set<ProfessoresDisciplinasModel> disciplinas) throws ParserConfigurationException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder construtor = dbf.newDocumentBuilder();
		Document documento = construtor.newDocument();

		Element root = documento.createElement("professores-disciplinas");

		for (ProfessoresDisciplinasModel disciplina : disciplinas) {
			if (disciplina != null) {
				root.appendChild(criarElementoDocument(documento, disciplina));
			}
		}

		documento.appendChild(root);

		return documento;
	}

}
