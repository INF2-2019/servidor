package diario.professores;

/**
 * Exceção para erro nos prâmetros de uma requisição
 *
 * @author Jonata Novais Cirqueria
 * @author Nikolas Victor Mota
 */
public class ExcecaoParametrosIncorretos extends Exception {

	public ExcecaoParametrosIncorretos() {
		super();
	}

	public ExcecaoParametrosIncorretos(String msg) {
		super(msg);
	}

}
