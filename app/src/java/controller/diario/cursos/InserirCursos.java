package controller.diario.cursos;

import repository.diario.CursoRepository;
import utils.ConnectionFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name = "Inserir", urlPatterns = "/diario/cursos/inserir")
public class InserirCursos extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
		Connection conexao = ConnectionFactory.getDiario();
		CursoRepository cursoRep = new CursoRepository(conexao);

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "text/xml; charset=utf-8");

		Map<String, String> dados = definirFiltros(request);

		try {
			if(cursoRep.inserir(dados))
				System.out.println("Inserido!");
			else
				System.out.println("Não foi inserido.");
		} catch (NumberFormatException excecaoFormatoErrado) {
			System.err.println("Número inteiro inválido para o parâmetro. Erro: "+excecaoFormatoErrado.toString());
		} catch (SQLException excecaoSQL) {
			System.err.println("Busca SQL inválida. Erro: "+excecaoSQL.toString());
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
