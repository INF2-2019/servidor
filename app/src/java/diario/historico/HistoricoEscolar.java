package diario.historico;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;
import utils.ConnectionFactory;

@WebServlet(name = "HistoricoEscolar", urlPatterns = {"diario/HistoricoEscolar"})
public class HistoricoEscolar extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		try (Connection conexao = ConnectionFactory.getDiario()) {
			DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
			DiarioCargos usuario = autenticador.cargoLogado();

			if (usuario == DiarioCargos.CONVIDADO) {
				response.setStatus(403);
				return;
			}

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			if (request.getParameter("id") == null) {
				throw new Exception("Não foi recebido nenhum CPF");
			} else {
				Long cpf = Long.parseLong(request.getParameter("id"));
				ResultSet matriculas;

				PreparedStatement ps1 = conexao.prepareStatement("SELECT `id` FROM `matriculas` WHERE `id-alunos` = ?");
				ps1.setLong(1, cpf);
				matriculas = ps1.executeQuery();

				System.out.println(matriculas);
			}

		} catch (Exception e) {

		}
	}

}
