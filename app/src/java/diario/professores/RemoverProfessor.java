package diario.professores;

import diario.professores.services.ExcecaoNaoAutorizado;
import diario.professores.services.ExcecaoParametrosIncorretos;
import utils.ConnectionFactory;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "DeletarProfessor", urlPatterns = "/diario/professores/deletar")
/**
 * <h1>Servlet de Remoção de Professor</h1>
 * Servlet dedicado para deletar registros na tablea 'professores'
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class RemoverProfessor extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
		throws IOException {

		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getDiario()) {

			DiarioAutenticador autenticador = new DiarioAutenticador(requisicao, resposta);
			if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
				throw new ExcecaoNaoAutorizado("Você não tem permissão para realizar esta operação");
			}

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			String id = requisicao.getParameter("id");
			if (id == null) {
				throw new ExcecaoParametrosIncorretos("O parâmetro 'id' é obrigatório para deletar registros");
			}

			PreparedStatement ps = conexao.prepareStatement("DELETE FROM `professores` WHERE `id` = ?");
			ps.setInt(1, Integer.parseInt(id));
			ps.execute();
			ps.close();
			conexao.close();

			saida.println("<sucesso>");
			saida.println("  <mensagem>Registro deletado com sucesso</mensagem>");
			saida.println("</sucesso>");

		} catch (ExcecaoParametrosIncorretos e) {
			resposta.setStatus(422);
			saida.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		} catch (ExcecaoNaoAutorizado e) {
			resposta.setStatus(403);
			saida.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		} catch (Exception e) {
			resposta.setStatus(500);
			saida.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		}

	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado para deletar registros na tablea 'professores'";
	}

}
