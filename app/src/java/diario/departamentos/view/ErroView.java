package diario.departamentos.view;

public class ErroView {

	public static String erro(String msg, String causa) {
		String xml =
				"<erro>" +
					"<mensagem>" + msg + "</mensagem>" +
					"<causa>" + causa + "</causa>" +
				"</erro>";
		return xml;
	}

}
