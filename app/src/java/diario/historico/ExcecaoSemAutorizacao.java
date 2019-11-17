package diario.historico;

public class ExcecaoSemAutorizacao extends Exception {

	public ExcecaoSemAutorizacao() {
		super();
	}

	public ExcecaoSemAutorizacao(String mensagem) {
		super(mensagem);
	}
}
