package controller.diario.cursos;

import model.diario.curso.Curso;
import repository.diario.CursoRepository;
import utils.ConnectionFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "Consultar", urlPatterns = "/diario/cursos/consultar")
public class Consultar extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
		Connection conexao = ConnectionFactory.getDiario();
		System.out.println(conexao.toString());
		CursoRepository cursoRep = new CursoRepository(conexao);

		Set<Curso> resultado = new HashSet<>();
		Map<String, String> filtros = new HashMap<String, String>(); // criando um Map para armazenar os filtros de maneira pratica

		// definir os valores do map condicionalmente, conforme a requisição
		if (request.getParameter("departamento") != null) {
			filtros.put("id-depto", request.getParameter("departamento"));
		}

		if (request.getParameter("nome") != null) {
			filtros.put("nome", request.getParameter("nome"));
		}

		if (request.getParameter("horas") != null) {
			filtros.put("horas-total", request.getParameter("horas"));
		}

		if (request.getParameter("modalidade") != null) {
			filtros.put("modalidade", request.getParameter("modalidade"));
		}

		try {
			resultado = cursoRep.realizarBusca(filtros);
			System.out.println(resultado);
		} catch(NumberFormatException excecaoFormatoErrado) {
			System.err.println("Número inteiro inválido para o parâmetro. Erro: "+excecaoFormatoErrado.toString());
		} catch(SQLException excecaoSQL) {
			System.err.println("Busca SQL inválida. Erro: "+excecaoSQL.toString());
		}

		try	{
			conexao.close();
		} catch(SQLException erro) {
			System.err.println("Erro ao fechar banco de dados. Erro: "+erro.toString());
		}
    }

}
