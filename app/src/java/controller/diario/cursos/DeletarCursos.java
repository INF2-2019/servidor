package controller.diario.cursos;

import repository.diario.CursoRepository;
import utils.ConnectionFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Deletar", urlPatterns = "/diario/cursos/deletar")
public class DeletarCursos extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		Connection conexao = ConnectionFactory.getDiario();
		CursoRepository cursoRep = new CursoRepository(conexao);

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "text/xml; charset=utf-8");

		Map<String, String> filtros = definirFiltros(request);


	}

	private Map<String, String> definirFiltros(HttpServletRequest req) {
		Map<String, String> filtros = new HashMap<>();

		// definir os valores do map condicionalmente, conforme a requisição
		if (req.getParameter("departamento") != null) {
			filtros.put("id-depto", req.getParameter("departamento"));
		}

		if (req.getParameter("nome") != null) {
			filtros.put("nome", req.getParameter("nome"));
		}

		if (req.getParameter("horas") != null) {
			filtros.put("horas-total", req.getParameter("horas"));
		}

		if (req.getParameter("modalidade") != null) {
			filtros.put("modalidade", req.getParameter("modalidade"));
		}

		return filtros;
	}
}
