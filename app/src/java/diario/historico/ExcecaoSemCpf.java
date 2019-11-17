package diario.historico;

public class ExcecaoSemCpf extends Exception {

	public ExcecaoSemCpf() {
		super();
	}

	public ExcecaoSemCpf(String mensagem) {
		super(mensagem);
	}
}
