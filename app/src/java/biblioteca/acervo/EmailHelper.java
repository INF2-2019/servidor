package biblioteca.acervo;

import org.apache.commons.mail.*;

/**
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class EmailHelper {

	private static final String ORIGEM = "jonascareca176@gmail.com";
	private static final String SENHA = "carecasecreto";
	private static final String ASSUNTO = "Item excluído do acervo";
	private static final String MENSAGEM_CORPO
			= "Comunicamos que um item que você reservou foi excluído do nosso acervo.\n"
			+ "Pedimos desculpas pelo inconveniente mas temos que atualizar nosso acervo"
			+ " frequentemente para agradar a todos os alunos :) \n\n"
			+ "Atenciosamente,\nSistema de Controle de Biblioteca.\n\n\nNão responda a este email";

	public static void enviarEmail(String destinatario, String nome)
			throws EmailException {

		MultiPartEmail email = new MultiPartEmail();
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator(ORIGEM, SENHA));
		email.setDebug(true);
		email.setHostName("smtp.gmail.com");
		email.setFrom(ORIGEM);
		email.setSubject(ASSUNTO);

		email.setMsg("Olá, " + nome + "\n\n" + MENSAGEM_CORPO);
		email.addTo(destinatario);
		email.setTLS(true);

		email.send();

	}
}
