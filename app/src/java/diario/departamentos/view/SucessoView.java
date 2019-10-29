package diario.departamentos.view;

public class SucessoView {
	
	public static String sucesso(String msg) {
		String xml =
				"<sucesso>" +
					"<mensagem>" + msg + "</mensagem>" +
				"</sucesso>";
		return xml;
	}
	
}
