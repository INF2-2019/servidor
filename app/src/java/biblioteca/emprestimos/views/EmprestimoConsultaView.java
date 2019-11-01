package biblioteca.emprestimos.views;

import biblioteca.emprestimos.model.EmprestimoModel;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Conversores;

public class EmprestimoConsultaView extends View<Set<EmprestimoModel>> {

	public EmprestimoConsultaView(Set<EmprestimoModel> EmprestimoModel) {
		super(EmprestimoModel);
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

	private Document disciplinaParaDocument(Set<EmprestimoModel> emprestimos) throws ParserConfigurationException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder construtor = dbf.newDocumentBuilder();
		Document documento = construtor.newDocument();

		Element root = documento.createElement("emprestimos");

		for (EmprestimoModel emprestimo : emprestimos) {
			if (emprestimo != null) {
				root.appendChild(criarElementoDocument(documento, emprestimo));
			}
		}

		documento.appendChild(root);

		return documento;
	}

	private static Element criarElementoDocument(Document documento, EmprestimoModel e) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");

		Element elemento = documento.createElement("emprestimo");

		Element id = documento.createElement("id");
		Element idAlunos = documento.createElement("id-alunos");
		Element idAcervo = documento.createElement("id-acervo");
		Element dataEmprestimo = documento.createElement("data-emprestimo");
		Element dataPrevDevol = documento.createElement("data-prev-devol");
		Element dataDevolucao = documento.createElement("data-devolucao");
		Element multa = documento.createElement("multa");

		id.appendChild(documento.createTextNode("" + e.getId()));
		idAlunos.appendChild(documento.createTextNode("" + e.getIdAlunos()));
		idAcervo.appendChild(documento.createTextNode("" + e.getIdAcervo()));
		dataEmprestimo.appendChild(documento.createTextNode("" + simpleFormat.format(e.getDataEmprestimo())));
		dataPrevDevol.appendChild(documento.createTextNode("" + simpleFormat.format(e.getDataPrevDevol())));
		dataDevolucao.appendChild(documento.createTextNode("" + simpleFormat.format(e.getDataDevolucao())));
		multa.appendChild(documento.createTextNode("" + e.getMulta()));

		elemento.appendChild(id);
		elemento.appendChild(idAlunos);
		elemento.appendChild(idAcervo);
		elemento.appendChild(dataEmprestimo);
		elemento.appendChild(dataPrevDevol);
		elemento.appendChild(dataDevolucao);
		elemento.appendChild(multa);

		return elemento;
	}

}
