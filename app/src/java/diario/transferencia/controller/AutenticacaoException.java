package diario.transferencia.controller;

public class AutenticacaoException extends Exception {
	
	public AutenticacaoException() {
		super("Permissão apenas para administrador");
	}

	public AutenticacaoException(String msg) {
		super(msg);
	}
	
}
