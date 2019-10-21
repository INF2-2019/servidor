package diario.Disciplinas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import diario.Disciplinas.Repository.DisciplinaRepository;
import utils.ConnectionFactory;
import utils.Headers;
import views.RenderException;
import views.View;
import diario.Disciplinas.views.SucessoView;


import diario.Disciplinas.views.DisciplinaConsultaView;
import diario.cursos.view.ErroView;

/**
 *
 * @author User
 */
@WebServlet(name = "DeletarDisciplinas", urlPatterns = {"/diario/disciplinas/deletar"})
public class DeletarDisciplinas extends HttpServlet {

     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Headers.XMLHeaders(response);
		Connection conexao = ConnectionFactory.getDiario();

		PrintWriter out = response.getWriter();

		if (conexao == null) {
			System.err.println("Falha ao conectar ao bd"); // Adicionar XML de erro
			return;
		}

		DisciplinaRepository DisciplinaRep = new DisciplinaRepository(conexao);

                String id = request.getParameter("id");
                 try {
			DisciplinaRep.Deletar(id);
			View sucessoView = new SucessoView("Deletado com sucesso.");
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
               
    }



