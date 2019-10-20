package controller.diario.cursos;

import model.diario.cursos.Curso;
import repository.diario.CursoRepository;
import utils.ConnectionFactory;
import view.diario.cursos.CursoView;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "Consultar", urlPatterns = "/diario/cursos/consultar")
public class Consultar extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
		Connection conexao = ConnectionFactory.getDiario();
		CursoRepository cursoRep = new CursoRepository(conexao);

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-type", "utf-8");

		Set<Curso> resultado;
		Map<String, String> filtros = new HashMap<>(); // criando um Map para armazenar os filtros de maneira pratica

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
			String xmlRetorno = CursoView.converterCursoParaXML(resultado);

			response.getWriter().print(xmlRetorno);
			System.out.println(resultado);
		} catch(NumberFormatException excecaoFormatoErrado) {
			System.err.println("Número inteiro inválido para o parâmetro. Erro: "+excecaoFormatoErrado.toString());
		} catch(SQLException excecaoSQL) {
			System.err.println("Busca SQL inválida. Erro: "+excecaoSQL.toString());
		} catch (IOException e) {
			System.err.println("Não foi possível mostrar o resultado, erro ao pegar Writer. Erro: "+e.toString());
		} catch (TransformerException | ParserConfigurationException e) {
			System.err.println("Não foi possível criar documento ou XML string com o resultado. Erro: "+e.toString());
		}

		try	{
			conexao.close();
		} catch(SQLException erro) {
			System.err.println("Erro ao fechar banco de dados. Erro: "+erro.toString());
		}
    }

}