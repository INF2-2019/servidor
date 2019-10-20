package controller.diario.cursos;

import model.diario.CursoModel;
import repository.diario.CursoRepository;
import utils.ConnectionFactory;
import utils.Headers;
import views.RenderException;
import views.View;
import views.diario.cursos.CursoConsultaView;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "ConsultarCursos", urlPatterns = "/diario/cursos/consultar")
public class ConsultarCursos extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
			Headers.XMLHeaders(response);
			Connection conexao = ConnectionFactory.getDiario();

			if(conexao == null){
				System.err.println("Falha ao conectar ao bd"); // Adicionar XML de erro
				return;
			}

			CursoRepository cursoRep = new CursoRepository(conexao);

			Set<CursoModel> resultado;
			Map<String, String> filtros = cursoRep.definirMap(request); // criando um Map para armazenar os filtros de maneira pratica

			try {
				PrintWriter out = response.getWriter();
				resultado = cursoRep.consultar(filtros); // Executa consulta
				View cursoConsultaView = new CursoConsultaView(resultado);
				cursoConsultaView.render(out);
			} catch(NumberFormatException excecaoFormatoErrado) {
				System.err.println("Número inteiro inválido para o parâmetro. Erro: "+excecaoFormatoErrado.toString());
			} catch(SQLException excecaoSQL) {
				System.err.println("Busca SQL inválida. Erro: "+excecaoSQL.toString());
			} catch (IOException e) {
				System.err.println("Não foi possível mostrar o resultado, erro ao pegar Writer. Erro: "+e.toString());
			}catch (RenderException e){
				System.err.println("Não foi possível criar documento ou XML string com o resultado. Erro: "+ e.toString());
			}

			try	{
				conexao.close();
			} catch(SQLException erro) {
				System.err.println("Erro ao fechar banco de dados. Erro: "+erro.toString());
			}

    }
}
