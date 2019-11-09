package biblioteca.reservas.views;

public class ExcecaoreservaExistente extends Exception {

	public ExcecaoreservaExistente() {
		super("Já existe uma reserva sobre esse id no acervo");
	}

	public ExcecaoreservaExistente(String mensagem) {
		super(mensagem);
	}
}
