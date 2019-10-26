package diario.departamentos.view;

public class ErroView {
	
	public static String erro(String msg, Exception ex) {
		String xml =
				"<erro>" +
					"<mensagem>" + msg + "</mensagem>" +
					"<causa>" + ex + "</causa>" +
				"</erro>";
		return xml;
	}
	
}
