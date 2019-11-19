package diario.departamentos.view;

import diario.departamentos.model.Departamento;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.PrintWriter;
import java.util.List;

public class DepartamentosView extends View<List<Departamento>> {

	public DepartamentosView(List<Departamento> departamentos) {
		super(departamentos);
	}

	@Override
	public void render(PrintWriter writer) throws RenderException {
		try {
			writer.write(Conversores.converterDocumentEmXMLString(criarDepartamentosXML(data)));
		} catch (TransformerException | ParserConfigurationException e) {
			throw new RenderException(e);
		}
	}

	private Document criarDepartamentosXML(List<Departamento> deptos) throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbf.newDocumentBuilder();
		Document document = docBuilder.newDocument();

		Element deptosEl = document.createElement("departamentos");

		for (Departamento depto : deptos) {
			Element deptoEl = document.createElement("departamento");

			Element idEl = document.createElement("id");
			idEl.appendChild(document.createTextNode(String.valueOf(depto.getId())));
			deptoEl.appendChild(idEl);

			Element idCampiEl = document.createElement("id-campi");
			idCampiEl.appendChild(document.createTextNode(String.valueOf(depto.getIdCampi())));
			deptoEl.appendChild(idCampiEl);

			Element nomeEl = document.createElement("nome");
			nomeEl.appendChild(document.createTextNode(depto.getNome()));
			deptoEl.appendChild(nomeEl);

			Element nomeCampiEl = document.createElement("nome-campi");
			nomeCampiEl.appendChild(document.createTextNode(depto.getNomeCampi()));
			deptoEl.appendChild(nomeCampiEl);

			Element cidadeEl = document.createElement("cidade");
			cidadeEl.appendChild(document.createTextNode(depto.getCidade()));
			deptoEl.appendChild(cidadeEl);

			Element ufEl = document.createElement("uf");
			ufEl.appendChild(document.createTextNode(depto.getUf()));
			deptoEl.appendChild(ufEl);

			deptosEl.appendChild(deptoEl);
		}

		document.appendChild(deptosEl);
		return document;
	}

}
