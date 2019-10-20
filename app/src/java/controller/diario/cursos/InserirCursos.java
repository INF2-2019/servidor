package controller.diario.cursos;

import repository.diario.CursoRepository;
import utils.ConnectionFactory;
<<<<<<< HEAD
=======
import utils.Conversores;
import view.diario.cursos.CursoView;
>>>>>>> cursos

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
<<<<<<< HEAD
import java.sql.Connection;
import java.sql.SQLException;
=======
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
>>>>>>> cursos
import java.util.Map;

@WebServlet(name = "Inserir", urlPatterns = "/diario/cursos/inserir")
public class InserirCursos extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
		Connection conexao = ConnectionFactory.getDiario();
		CursoRepository cursoRep = new CursoRepository(conexao);

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "text/xml; charset=utf-8");

		Map<String, String> dados = cursoRep.definirMap(request);

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
}
