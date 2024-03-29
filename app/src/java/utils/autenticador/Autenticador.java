package utils.autenticador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Autenticador {

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public Autenticador(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
}
