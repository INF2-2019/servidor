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
        try {
            Document cursosEmDocument = DisciplinaParaDocument(data);
            writer.write(Conversores.converterDocumentEmXMLString(cursosEmDocument));
        } catch (Exception ex) {
            throw new RenderException(ex);
        }
    }

    private Document DisciplinaParaDocument(Set<DisciplinaModel> disciplinas) throws ParserConfigurationException {
      
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder construtor = dbf.newDocumentBuilder();
        Document documento = construtor.newDocument();

       
        Element root = documento.createElement("disciplinas");

       
        for (DisciplinaModel disciplina : disciplinas) {
            root.appendChild(criarElementoDocument(documento, disciplina)); // para cada curso, adiciona um ELemento
        }

        documento.appendChild(root);

        return documento;
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

}
