package diario.transferencia.controller;

import diario.transferencia.repository.AlunoInativoException;
import diario.transferencia.repository.TransfereRepository;
import diario.transferencia.view.ErroView;
import diario.transferencia.view.RenderException;
import diario.transferencia.view.SucessoView;
import utils.ConnectionFactory;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "TransfereAluno", urlPatterns = {"/diario/transferencia/transfere"})
public class Transfere extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {


		try (PrintWriter out = response.getWriter()) {

			Exception excecao = null;

			try {

				DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
				if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
					throw new AutenticacaoException();
				}

				if (request.getParameter("cpf") == null) {
					throw new ParametrosIncorretosException("Falha ao receber CPF");
				}

				long cpf;
				try {
					cpf = Long.parseLong(request.getParameter("cpf"));
				} catch (NumberFormatException ex) {
					throw new ParametrosIncorretosException("CPF deve ser inteiro");
				}

				Connection con = ConnectionFactory.getDiario();
				if (con == null) {
					throw new SQLException("Falha ao conectar ao banco de dados");
				}

				TransfereRepository repo = new TransfereRepository(con);
				repo.transfere(cpf);

				SucessoView sucessoView = new SucessoView("Aluno transferido com sucesso");
				try {
					sucessoView.render(out);
				} catch (RenderException e) {
					throw new ServletException(e);
				}

			} catch (AutenticacaoException ex) {
				response.setStatus(403);
				excecao = ex;
			} catch (ParametrosIncorretosException ex) {
				response.setStatus(422);
				excecao = ex;
			} catch (AlunoInativoException ex) {
				response.setStatus(400);
				excecao = ex;
			} catch (SQLException ex) {
				response.setStatus(500);
				excecao = ex;
			} finally {
				if (excecao != null) {
					ErroView erroView = new ErroView(excecao);
					try {
						erroView.render(out);
					} catch (RenderException e) {
						throw new ServletException(e);
					}
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Servlet que transfere um aluno.";
	}

}
