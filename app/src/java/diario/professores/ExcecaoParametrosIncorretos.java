package diario.professores;

/**
 * <h1> Exceção de Parâmetros Incorretos</h1>
 * Exceção para erro nos parâmetros enviados de uma requisição
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
