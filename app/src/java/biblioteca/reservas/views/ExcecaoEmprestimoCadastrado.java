package biblioteca.reservas.views;

public class ExcecaoEmprestimoCadastrado extends Exception {

	public ExcecaoEmprestimoCadastrado() {
		super("o item ainda está emprestado");
	}

	public ExcecaoEmprestimoCadastrado(String mensagem) {
		super(mensagem);
	}
}
