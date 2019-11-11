package diario.transferencia.controller;

public class ParametrosIncorretosException extends Exception {

	public ParametrosIncorretosException() {
		super("Falha ao receber parâmetros");
	}

	public ParametrosIncorretosException(String msg) {
		super(msg);
	}

}
