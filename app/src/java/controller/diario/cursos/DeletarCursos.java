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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
>>>>>>> cursos
import java.util.Map;

@WebServlet(name = "Deletar", urlPatterns = "/diario/cursos/deletar")
public class DeletarCursos extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		Connection conexao = ConnectionFactory.getDiario();
		CursoRepository cursoRep = new CursoRepository(conexao);

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "text/xml; charset=utf-8");

		Map<String, String> filtros = cursoRep.definirMap(request);

		try {
			boolean sucesso = cursoRep.deletar(filtros);
			if(sucesso)
				System.out.println("Deletado.");
			else
				System.out.println("Não deletado.");
		} catch (NumberFormatException excecaoFormatoErrado) {
			System.err.println("Número inteiro inválido para o parâmetro. Erro: "+excecaoFormatoErrado.toString());
		} catch (SQLException excecaoSQL) {
			System.err.println("Busca SQL inválida. Erro: "+excecaoSQL.toString());
		}
	}

}
