package biblioteca.reservas.views;

import java.io.PrintWriter;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import biblioteca.reservas.model.ReservaModel;
import java.text.SimpleDateFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import utils.Conversores;

public class ReservaConsultaView extends View<Set<ReservaModel>> {

	public ReservaConsultaView(Set<ReservaModel> ReservaModel) {
		super(ReservaModel);
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

	private Document disciplinaParaDocument(Set<ReservaModel> reservas) throws ParserConfigurationException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder construtor = dbf.newDocumentBuilder();
		Document documento = construtor.newDocument();

		Element root = documento.createElement("reservas");

		for (ReservaModel reserva : reservas) {
			if (reserva != null) {
				root.appendChild(criarElementoDocument(documento, reserva));
			}
		}

		documento.appendChild(root);

		return documento;
	}

	private static Element criarElementoDocument(Document documento, ReservaModel c) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
		Element elemento = documento.createElement("reserva");

		Element id = documento.createElement("id");
		Element idAluno = documento.createElement("id-aluno");
		Element idAcervo = documento.createElement("id-acervo");
		Element dataReserva = documento.createElement("data-reserva");
		Element tempoEspera = documento.createElement("tempo-espera");
		Element emprestou = documento.createElement("emprestou");

		id.appendChild(documento.createTextNode("" + c.getId()));
		idAluno.appendChild(documento.createTextNode("" + c.getIdAlunos()));
		idAcervo.appendChild(documento.createTextNode("" + c.getIdAcervo()));
		dataReserva.appendChild(documento.createTextNode("" + simpleFormat.format(c.getDataReserva())));
		tempoEspera.appendChild(documento.createTextNode("" + c.getTempoEspera()));
		emprestou.appendChild(documento.createTextNode("" + c.isEmprestou()));

		elemento.appendChild(id);
		elemento.appendChild(idAluno);
		elemento.appendChild(idAcervo);
		elemento.appendChild(dataReserva);
		elemento.appendChild(tempoEspera);
		elemento.appendChild(emprestou);

		return elemento;
	}

}
