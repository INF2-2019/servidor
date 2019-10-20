package utils;

import javax.servlet.http.HttpServletResponse;

public class Headers {

	public static void XMLHeaders(HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "application/xml; charset=utf-8");
	}

}
