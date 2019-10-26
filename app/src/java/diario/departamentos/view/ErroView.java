package diario.departamentos.view;

public class ErroView {
	
	public static String erro(String msg, Exception ex) {
		String xml =
				"<sucesso>" +
					"<mensagem>" + msg + "</mensagem>" +
					"<causa>" + ex + "</causa>" +
				"</sucesso>";
		return xml;
	}
	
}
