package diario.departamentos.repository;

public class DepartamentoInexistenteException extends Exception {

	public DepartamentoInexistenteException() {
		super("Departamento inexistente");
	}

	public DepartamentoInexistenteException(String msg) {
		super(msg);
	}

}
