package utils.autenticador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BibliotecaAutenticador extends Autenticador {
	private final static int DEFAULT_DURATION = 60 * 60;
	private final static int KEEP_DURATION = 7 * 24 * 60 * 60;
	private final static String ID_KEY = "identificador-biblioteca";
	private final static String ROLE_KEY = "cargo-biblioteca";

	public BibliotecaAutenticador(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	public void logar(Integer identididade, BibliotecaCargos cargo, boolean manter) {
		logar(identididade, cargo, manter ? KEEP_DURATION : DEFAULT_DURATION);
	}

	public void encerrar() {
		HttpSession session = this.request.getSession();
		session.removeAttribute(ID_KEY);
		session.removeAttribute(ROLE_KEY);
	}

	public BibliotecaCargos cargoLogado() {
		HttpSession session = this.request.getSession();
		BibliotecaCargos cargo = (BibliotecaCargos) session.getAttribute(ROLE_KEY);

		if (cargo == null) {
			return BibliotecaCargos.CONVIDADO;
		}

		return cargo;
	}

	public Object idLogado() {
		HttpSession session = this.request.getSession();
		return session.getAttribute(ID_KEY);
	}

	private void logar(Object identidade, BibliotecaCargos cargo, int duration) {
		HttpSession session = this.request.getSession();
		session.setAttribute(ID_KEY, identidade);
		session.setAttribute(ROLE_KEY, cargo);
		session.setMaxInactiveInterval(duration);
	}

}
