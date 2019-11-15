package diario.professoresDisciplinas;

import diario.professoresDisciplinas.Model.ProfessoresDisciplinasModel;
import diario.professoresDisciplinas.Repository.ProfessoresDisciplinasRepository;
import diario.professoresDisciplinas.View.ErroView;
import diario.professoresDisciplinas.View.ProfDisConsultaView;
import diario.professoresDisciplinas.View.RenderException;
import diario.professoresDisciplinas.View.View;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "ConsultaProfessoresDisciplinas", urlPatterns = {"/diario/professoresdisciplinas/consultar"})
public class ConsultaProfessoresDisciplinas extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getDiario();
		PrintWriter out = response.getWriter();
		DiarioAutenticador autenticador = new DiarioAutenticador(request, response);

		if (autenticador.cargoLogado() == DiarioCargos.CONVIDADO) {
			response.setStatus(403);
			View erroView = new ErroView(new Exception("O usuario não tem permisão para essa operação"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}
		if (conexao == null) {
			System.err.println("Falha ao conectar ao bd");
			View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		ProfessoresDisciplinasRepository disciplinaRep = new ProfessoresDisciplinasRepository(conexao);

		Set<ProfessoresDisciplinasModel> resultado;
		try {
			Map<String, String> filtros = ProfessoresDisciplinasModel.definirMap(request); // criando um Map para armazenar os filtros de maneira pratica
			resultado = new HashSet<>();
			resultado = disciplinaRep.consultar(ProfessoresDisciplinasModel.definirMap(request));
			View DisciplinaConsultaView = new ProfDisConsultaView(resultado);
			DisciplinaConsultaView.render(out);
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
			response.setStatus(500);
			System.err.println("Busca SQL inválida. Erro: " + excecaoSQL.toString());
			View erroView = new ErroView(excecaoSQL);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			try {
				conexao.close();
			} catch (SQLException erro) {
				System.err.println("Erro ao fechar banco de dados. Erro: " + erro.toString());
			}
		} catch (RenderException ex) {
			throw new ServletException(ex);
		}
	}
}
