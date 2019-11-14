package diario.professores.services;

public class ExcecaoNaoAutorizado extends Exception {

	public ExcecaoNaoAutorizado() {
	}

	public ExcecaoNaoAutorizado(String message) {
		super(message);
	}
}
