package controller.diario.cursos;

import model.diario.CursoModel;
import repository.diario.CursoRepository;
import utils.ConnectionFactory;
import utils.Headers;
import views.RenderException;
import views.View;
import views.diario.cursos.CursoConsultaView;
import views.utils.ErroView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "ConsultarCursos", urlPatterns = "/diario/cursos/consultar")
public class ConsultarCursos extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			Headers.XMLHeaders(response);
			Connection conexao = ConnectionFactory.getDiario();

			PrintWriter out = response.getWriter();

			if(conexao == null){
				System.err.println("Falha ao conectar ao bd"); // Adicionar XML de erro
				return;
			}

			CursoRepository cursoRep = new CursoRepository(conexao);

			Set<CursoModel> resultado;

			try {
				resultado = cursoRep.consultar(definirFiltros(request));
				View cursoConsultaView = new CursoConsultaView(resultado);
				cursoConsultaView.render(out);
			} catch(NumberFormatException excecaoFormatoErrado) {
				response.setStatus(400);
				System.err.println("Número inteiro inválido para o parâmetro. Erro: "+excecaoFormatoErrado.toString());

				View erroView = new ErroView(excecaoFormatoErrado);
				try {
					erroView.render(out);
				} catch (RenderException e) {
					response.setStatus(500);
					throw new ServletException(e);
				}
			} catch(SQLException excecaoSQL) {
				response.setStatus(400);
				System.err.println("Busca SQL inválida. Erro: "+excecaoSQL.toString());

				View erroView = new ErroView(excecaoSQL);
				try {
					erroView.render(out);
				} catch (RenderException e) {
					throw new ServletException(e);
				}
			} catch (RenderException e){
				throw new ServletException(e);
			}

			try	{
				conexao.close();
			} catch(SQLException erro) {
				System.err.println("Erro ao fechar banco de dados. Erro: "+erro.toString());
			}

    }

	public Map<String, String> definirFiltros(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();

		// definir os valores do map condicionalmente, conforme a requisição
		if (req.getParameter("departamento") != null) {
			dados.put("id-depto", req.getParameter("departamento"));
		}

		if (req.getParameter("nome") != null) {
			dados.put("nome", req.getParameter("nome"));
		}

		if (req.getParameter("horas") != null) {
			dados.put("horas-total", req.getParameter("horas"));
		}

		if (req.getParameter("modalidade") != null) {
			dados.put("modalidade", req.getParameter("modalidade"));
		}

		return dados;
	}
}
