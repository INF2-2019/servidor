package diario.departamentos.controller;

public class ParametrosIncorretosException extends Exception {

	public ParametrosIncorretosException() {
		super("Parâmetros incorretos");
	}

	public ParametrosIncorretosException(String msg) {
		super(msg);
	}

}
