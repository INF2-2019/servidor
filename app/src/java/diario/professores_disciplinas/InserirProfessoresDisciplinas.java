package diario.professores_disciplinas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;
import utils.Headers;
import diario.professores_disciplinas.View.RenderException;
import diario.professores_disciplinas.View.ErroView;
import diario.professores_disciplinas.View.SucessoView;
import diario.professores_disciplinas.View.View;
import diario.professores_disciplinas.Model.ProfessoresDisciplinasModel;
import diario.professores_disciplinas.Repository.ProfessoresDisciplinasRepository;
import diario.professores_disciplinas.View.ExcecaoConteudoVinculado;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

@WebServlet(name = "InserirProfessoresDisciplinas", urlPatterns = {"/diario/professoresdisciplinas/inserir"})
public class InserirProfessoresDisciplinas extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Connection conexao = ConnectionFactory.getDiario();
		ProfessoresDisciplinasRepository disciplinaRep = new ProfessoresDisciplinasRepository(conexao);
		PrintWriter out = response.getWriter();
		Headers.XMLHeaders(response);
		DiarioAutenticador autenticador = new DiarioAutenticador(request, response);

		if (autenticador.cargoLogado() != DiarioCargos.ADMIN) {
			response.setStatus(403);
			View erroView = new ErroView(new Exception("O usuario nao tem permisao para essa operacao"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}
		if (conexao == null) {
			View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		try {
			Map<String, String> dados = ProfessoresDisciplinasModel.definirMap(request);
			disciplinaRep.inserir(dados);
			View sucessoView = new SucessoView("Inserido com sucesso.");
			sucessoView.render(out);
		} catch (NumberFormatException excecaoFormatoErrado) {
			response.setStatus(400);
			System.err.println("Número inteiro inválido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());

			View erroView = new ErroView(excecaoFormatoErrado);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (SQLException excecaoSQL) {
			response.setStatus(400);
			System.err.println("Busca SQL inválida. Erro: " + excecaoSQL.toString());

			View erroView = new ErroView(excecaoSQL);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (ExcecaoConteudoVinculado ex) {
			response.setStatus(400);
			View erroView = new ErroView(ex);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (RenderException ex) {
			throw new ServletException(ex);
		}
	}
}
