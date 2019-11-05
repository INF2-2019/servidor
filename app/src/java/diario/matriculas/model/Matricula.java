package diario.matriculas.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Matricula {

	public final static int ID_INDEFINIDO = -1;
	private int id, idDisciplinas, ano;
	private long idAlunos;
	private boolean ativo;

	public Matricula(int id, long idAlunos, int idDisciplinas, int ano, boolean ativo) {
		this.setId(id);
		this.setIdAlunos(idAlunos);
		this.setIdDisciplinas(idDisciplinas);
		this.setAno(ano);
		this.setAtivo(ativo);
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Matricula) {
			return Integer.compare(id, ((Matricula) obj).getId()) < 0;
		}
		return false;
	}

	public Matricula(int id, long idAlunos, int idDisciplinas, int ano) {
		this(id, idAlunos, idDisciplinas, ano, true);
	}

	public Matricula(long idAlunos, int idDisciplinas, int ano, boolean ativo) {
		this(ID_INDEFINIDO, idAlunos, idDisciplinas, ano, ativo);

	}

	public Matricula(long idAlunos, int idDisciplinas, int ano) {
		this(ID_INDEFINIDO, idAlunos, idDisciplinas, ano, true);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getIdAlunos() {
		return idAlunos;
	}

	public void setIdAlunos(long idAlunos) {
		this.idAlunos = idAlunos;
	}

	public int getIdDisciplinas() {
		return idDisciplinas;
	}

	public void setIdDisciplinas(int idDisciplinas) {
		this.idDisciplinas = idDisciplinas;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Element toElement() throws ParserConfigurationException {
		Matricula m = this;

		Document DOM = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

		Element matricula = DOM.createElement("matricula");

		Element id = DOM.createElement("id");
		id.setTextContent(Integer.toString(m.getId()));
		matricula.appendChild(id);

		Element idAlunos = DOM.createElement("id-alunos");
		idAlunos.setTextContent(Long.toString(m.getIdAlunos()));
		matricula.appendChild(idAlunos);

		Element idDisciplinas = DOM.createElement("id-disciplinas");
		idDisciplinas.setTextContent(Integer.toString(m.getIdDisciplinas()));
		matricula.appendChild(idDisciplinas);

		Element ano = DOM.createElement("ano");
		ano.setTextContent(Integer.toString(m.getAno()));
		matricula.appendChild(ano);

		Element ativo = DOM.createElement("ativo");
		ativo.setTextContent(Boolean.toString(m.isAtivo()));
		matricula.appendChild(ativo);

		return matricula;
	}
}
