package biblioteca.emprestimos.model;

import biblioteca.emprestimos.views.AlunoException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class EmprestimoModel extends Model {

	public static double multaPerDay = 0.50;
	public static int tempoEmprestimo = 7;
	private int id, idAcervo;
	private Long idAlunos;
	private Date dataEmprestimo, dataPrevDevol, dataDevolucao;
	private double multa;

	public EmprestimoModel(int id, Long idAlunos, int idAcervo, Date dataEmprestimo, Date dataPrevDevol, Date dataDevolucao, double multa) {
		this.id = id;
		this.idAlunos = idAlunos;
		this.idAcervo = idAcervo;
		this.dataEmprestimo = dataEmprestimo;
		this.dataPrevDevol = dataPrevDevol;
		this.dataDevolucao = dataDevolucao;
		this.multa = multa;
	}

	public EmprestimoModel(Long idAlunos, int idAcervos, Date dataEmprestimo, Date dataPrevDevol, Date dataDevolucao, double multa) {
		this(ID_INDEFINIDO, idAlunos, idAcervos, dataEmprestimo, dataPrevDevol, dataDevolucao, multa);
	}

	public static SortedMap<String, String> definirMap(HttpServletRequest req) throws AlunoException {
		final String[] params = {"id-alunos", "id-acervo", "data-emprestimo", "data-prev-devol", "data-devolucao", "multa"};
		SortedMap<String, String> dados = new TreeMap<String, String>();

		// definir os valores do map condicionalmente, conforme a requisição
		for (String param : params) {
			if (req.getParameter(param) != null) {
				dados.put(param, req.getParameter(param));
			}
		}
		return dados;
	}

	public Object[] retornarValoresRestantes(Map<String, String> parametros) {
		Object[] retorno = new Object[7];
		retorno[0] = id;

		if (!parametros.containsKey("id-alunos")) {
			retorno[1] = idAlunos;
		} else {
			retorno[1] = parametros.get("id-alunos");
		}

		if (!parametros.containsKey("id-acervo")) {
			retorno[2] = idAcervo;
		} else {
			retorno[2] = parametros.get("id-acervo");
		}

		if (!parametros.containsKey("data-emprestimo")) {
			retorno[3] = dataEmprestimo;
		} else {
			retorno[3] = parametros.get("data-emprestimo");
		}
		if (!parametros.containsKey("data-prev-devol")) {
			retorno[4] = dataPrevDevol;
		} else {
			retorno[4] = parametros.get("data-prev-devol");
		}
		if (!parametros.containsKey("data-devolucao")) {
			retorno[5] = dataDevolucao;
		} else {
			retorno[5] = parametros.get("data-devolucao");
		}
		if (!parametros.containsKey("multa")) {
			retorno[6] = multa;
		} else {
			retorno[6] = parametros.get("multa");
		}

		return retorno;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getIdAlunos() {
		return idAlunos;
	}

	public void setIdAlunos(Long idAlunos) {
		this.idAlunos = idAlunos;
	}

	public int getIdAcervo() {
		return idAcervo;
	}

	public void setIdAcervo(int idAcervo) {
		this.idAcervo = idAcervo;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataPrevDevol() {
		return dataPrevDevol;
	}

	public void setDataPrevDevol(Date dataPrevDevol) {
		this.dataPrevDevol = dataPrevDevol;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public double getMulta() {
		return multa;
	}

	public void setMulta(double multa) {
		this.multa = multa;
	}

}
