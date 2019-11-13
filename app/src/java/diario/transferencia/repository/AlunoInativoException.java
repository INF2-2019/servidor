package diario.transferencia.repository;

public class AlunoInativoException extends Exception {

	public AlunoInativoException() {
		super("Aluno inexistente ou inativo");
	}

	public AlunoInativoException(String msg) {
		super(msg);
	}

}
