package controller.diario.disciplinas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Header;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import diario.Disciplinas.Repository.DisciplinaRepository;
import utils.ConnectionFactory;
import utils.Headers;
import views.RenderException;
import views.View;
import diario.Disciplinas.views.SucessoView;
import diario.cursos.view.ErroView;

/**
 *
 * @author User
 */
@WebServlet(name = "InserirDisciplinas", urlPatterns = {"/diario/disciplinas/inserir"})
public class InserirDisciplinas extends HttpServlet {
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		Connection conexao = ConnectionFactory.getDiario();
		DisciplinaRepository disciplinaRep = new DisciplinaRepository(conexao);
                
		Headers.XMLHeaders(response);
                PrintWriter out = response.getWriter();

		if (conexao == null) {
				View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}
		Map<String, String> dados = definirMap(request);

		try {
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
		} catch (RenderException e) {
			throw new ServletException(e);
		}
    }

	  public Map<String, String> definirMap(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();

		// definir os valores do map condicionalmente, conforme a requisição
		if (req.getParameter("turma") != null) {
			dados.put("id-turmas", req.getParameter("turma"));
		}

		if (req.getParameter("nome") != null) {
			dados.put("nome", req.getParameter("nome"));
		}

		if (req.getParameter("horas") != null) {
			dados.put("carga-horaria-min", req.getParameter("horas"));
		}

		return dados;
	}

}

