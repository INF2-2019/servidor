package diario.matriculas.models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Matricula {

	public final static Integer ID_INDEFINIDO = -1;
	private Integer id;
	private Integer idDisciplinas;
	private Integer ano;
	private Long idAlunos;
	private Boolean ativo;

	public Matricula(Integer id, Long idAlunos, Integer idDisciplinas, Integer ano, Boolean ativo) {
		this.setId(id);
		this.setIdAlunos(idAlunos);
		this.setIdDisciplinas(idDisciplinas);
		this.setAno(ano);
		this.setAtivo(ativo);
	}

	public Matricula(Integer id, Long idAlunos, Integer idDisciplinas, Integer ano) {
		this(id, idAlunos, idDisciplinas, ano, true);
	}

	public Matricula(Long idAlunos, Integer idDisciplinas, Integer ano, Boolean ativo) {
		this(ID_INDEFINIDO, idAlunos, idDisciplinas, ano, ativo);

	}

	public Matricula(Long idAlunos, Integer idDisciplinas, Integer ano) {
		this(ID_INDEFINIDO, idAlunos, idDisciplinas, ano, true);
	}

	public Matricula() {
		this(null, null, null, null, null);
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getIdAlunos() {
		return idAlunos;
	}

	public void setIdAlunos(Long idAlunos) {
		this.idAlunos = idAlunos;
	}

	public Integer getIdDisciplinas() {
		return idDisciplinas;
	}

	public void setIdDisciplinas(Integer idDisciplinas) {
		this.idDisciplinas = idDisciplinas;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
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
