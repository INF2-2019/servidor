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
import repository.diario.DisciplinaRepository;
import utils.ConnectionFactory;
import utils.Headers;
import views.RenderException;
import views.View;
import views.sucesso.SucessoView;

/**
 *
 * @author User
 */
@WebServlet(name = "InserirDisciplinas", urlPatterns = {"/diario/disciplinas/inserir"})
public class InserirDisciplinas extends HttpServlet {
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Connection conexao = ConnectionFactory.getDiario();
		DisciplinaRepository disciplinaRep = new DisciplinaRepository(conexao);

		Headers.XMLHeaders(response);
		Map<String, String> dados = definirMap(request);

		try {
                    PrintWriter out = response.getWriter();
                    disciplinaRep.inserir(dados);
                View Sucesso = new SucessoView("inserido");
                Sucesso.render(out);
			
		} catch (NumberFormatException excecaoFormatoErrado) {
			System.err.println("Número inteiro inválido para o parâmetro. Erro: "+excecaoFormatoErrado.toString());
		} catch (SQLException excecaoSQL) {
			System.err.println("Busca SQL inválida. Erro: "+excecaoSQL.toString());
		} catch (RenderException ex) {
        Logger.getLogger(InserirDisciplinas.class.getName()).log(Level.SEVERE, null, ex);
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




