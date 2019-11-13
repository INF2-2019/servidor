package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Headers {

	public static void DefaultHeader(HttpServletRequest request, HttpServletResponse response) {
		String CORS = request.getHeader("Origin");
		if (CORS == null || CORS.equals("")) {
			CORS = "*";
		}
		response.addHeader("Access-Control-Allow-Origin", CORS);
		response.addHeader("Vary", "Origin");
		response.addHeader("Access-Control-Allow-Credentials", "true");
	}

	public static void XMLHeaders(HttpServletRequest request, HttpServletResponse response) {
		DefaultHeader(request, response);
		response.addHeader("Content-Type", "application/xml; charset=utf-8");
	}

}
