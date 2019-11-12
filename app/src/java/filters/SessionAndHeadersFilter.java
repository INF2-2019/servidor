package filters;

import utils.Headers;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebFilter(filterName = "SessionAndHeadersFilter", urlPatterns = "/*", description = "Filter for setting up headers and cookie settings")
public class SessionAndHeadersFilter implements Filter {
	private final static String SET_COOKIE = "Set-Cookie";
	private final static String SET_COOKIE_PARAMS = "SameSite=None";
//	private final static String SET_COOKIE_PARAMS = "Secure; SameSite=None"; Se estiver com SSL

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
		chain.doFilter(req, resp);
		if (resp instanceof HttpServletResponse) {
			sameSiteFix((HttpServletResponse) resp);
			Headers.XMLHeaders((HttpServletRequest) req, (HttpServletResponse) resp);
		}
	}

	private void sameSiteFix(HttpServletResponse response) {
		Collection<String> headers = response.getHeaders(SET_COOKIE);
		boolean first = true;
		for (String header : headers) {
			String newHeader = String.format("%s; %s", header, SET_COOKIE_PARAMS);
			if (first) {
				response.setHeader(SET_COOKIE, newHeader);
				first = false;
				continue;
			}
			response.addHeader(SET_COOKIE, newHeader);
		}
	}

	public void init(FilterConfig config) throws ServletException {

	}

}
