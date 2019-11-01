package diario.professores;

import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;
import utils.ConnectionFactory;
import utils.Hasher;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jonata Novais Cirqueira
 */
@WebServlet(name = "LogarProfessor", urlPatterns = "/diario/professores/logar")
public class LogarProfessor extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws IOException {

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.addHeader("Content-Type", "application/xml; charset=utf-8");

		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getDiario()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			String siape, senha;
			boolean manter;
			siape = requisicao.getParameter("siape");
			senha = requisicao.getParameter("senha");
			manter = requisicao.getParameter("manter") != null;

			if (siape == null) {
				throw new ExcecaoParametrosIncorretos("'siape' é um parâmetro obrigatório");
			}
			if (senha == null) {
				throw new ExcecaoParametrosIncorretos("'senha' é um parâmetro obrigatório");
			}

			// Valida credenciais
			PreparedStatement ps = conexao.prepareStatement("SELECT `senha` FROM `professores` WHERE `id` = ?");
			ps.setInt(1, Integer.parseInt(siape));
			ResultSet professor = ps.executeQuery();
			if (!professor.first() || !Hasher.validar(senha, professor.getString("senha"))) {
				throw new ExcecaoParametrosIncorretos("Credenciais inválidas");
			}

			DiarioAutenticador autenticador = new DiarioAutenticador(requisicao, resposta);
			autenticador.logar(Integer.parseInt(siape), DiarioCargos.PROFESSOR, manter);

			saida.println("<sucesso>");
			saida.println("  <mensagem>Logado com sucesso</mensagem>");
			saida.println("</sucesso>");

		} catch (Exception e) {

			saida.println("<erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</erro>");

		}

	}

	@Override
	public String getServletInfo() {
		return "Servlet destinado a log-in dos Professores";
	}

}
