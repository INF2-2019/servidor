package diario.professores;

import diario.professores.services.ExcecaoNaoAutorizado;
import diario.professores.services.ExcecaoParametrosIncorretos;
import diario.professores.services.Validacao;
import utils.ConnectionFactory;
import utils.Hasher;
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

@WebServlet(name = "InserirProfessor", urlPatterns = "/diario/professores/inserir")
/**
 * <h1> Servlet de Inserção de Professor</h1>
 * Servlet para a requisição de indlusão de registro na tabela 'professores'
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class InsercaoProfessor extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
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

			Validacao.validarParametros(requisicao.getParameterMap());
			Validacao.validarDepartamento(requisicao.getParameter("id-depto"), conexao);

			PreparedStatement ps = conexao.prepareStatement("INSERT INTO `professores` VALUES (?, ?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.parseInt(requisicao.getParameter("id")));
			ps.setInt(2, Integer.parseInt(requisicao.getParameter("id-depto")));
			ps.setString(3, requisicao.getParameter("nome"));
			ps.setString(4, Hasher.hash(requisicao.getParameter("senha")));
			ps.setString(5, requisicao.getParameter("email"));
			ps.setString(6, requisicao.getParameter("titulacao"));

			ps.execute();
			ps.close();
			conexao.close();

			saida.println("<sucesso>");
			saida.println("  <mensagem>Professor cadastrado com sucesso</mensagem>");
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
		return "Servlet dedicado à inserção de registro de professores no BD";
	}

}
