package biblioteca.reservas.model;

import java.util.Date;
import java.util.Map;

public class ReservaModel extends Model {

	int id, idAcervo, TempoEspera;
	long idAlunos;
	Date dataReserva;
	boolean emprestou;

	public ReservaModel(int id, long idAlunos, int idAcervo, int TempoEspera, Date dataReserva, boolean emprestou) {
		this.id = id;
		this.idAlunos = idAlunos;
		this.idAcervo = idAcervo;
		this.TempoEspera = TempoEspera;
		this.dataReserva = dataReserva;
		this.emprestou = emprestou;
	}

	public Object[] retornarValoresRestantes(Map<String, String> parametros) {
		Object[] retorno = new Object[6];
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
		if (!parametros.containsKey("tempo-espera")) {
			retorno[3] = TempoEspera;
		} else {
			retorno[3] = parametros.get("tempo-espera");
		}
		if (!parametros.containsKey("data-reserva")) {
			retorno[4] = dataReserva;
		} else {
			retorno[4] = parametros.get("data-reserva");
		}
		if (!parametros.containsKey("emprestou")) {
			retorno[5] = dataReserva;
		} else {
			retorno[5] = parametros.get("emprestou");
		}

		return retorno;
	}

	public ReservaModel(long idAlunos, int idAcervo, int TempoEspera, Date dataReserva, boolean emprestou) {
		this.id = ID_INDEFINIDO;
		this.idAlunos = idAlunos;
		this.idAcervo = idAcervo;
		this.TempoEspera = TempoEspera;
		this.dataReserva = dataReserva;
		this.emprestou = emprestou;
	}

	public int getId() {
		return id;
	}

	public long getIdAlunos() {
		return idAlunos;
	}

	public void setIdAlunos(long idAlunos) {
		this.idAlunos = idAlunos;
	}

	public int getIdAcervo() {
		return idAcervo;
	}

	public void setIdAcervo(int idAcervo) {
		this.idAcervo = idAcervo;
	}

	public int getTempoEspera() {
		return TempoEspera;
	}

	public void setTempoEspera(int TempoEspera) {
		this.TempoEspera = TempoEspera;
	}

	public Date getDataReserva() {
		return dataReserva;
	}

	public void setDataReserva(Date dataReserva) {
		this.dataReserva = dataReserva;
	}

	public boolean isEmprestou() {
		return emprestou;
	}

	public void setEmprestou(boolean emprestou) {
		this.emprestou = emprestou;
	}

}
