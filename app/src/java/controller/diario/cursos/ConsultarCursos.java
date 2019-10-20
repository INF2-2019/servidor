package controller.diario.cursos;

import model.diario.CursoModel;
import repository.diario.CursoRepository;
import utils.ConnectionFactory;
<<<<<<< refs/remotes/origin/master
import utils.Headers;
import views.RenderException;
import views.View;
import views.diario.cursos.CursoConsultaView;
=======
import utils.Conversores;
import view.diario.cursos.CursoView;
>>>>>>> Adicionado XML de erro

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
			resultado = cursoRep.consultar(filtros); // Executa consulta
			String xmlRetorno = CursoView.setParaXML(resultado); // Transforma em XML

			response.getWriter().print(xmlRetorno);

<<<<<<< refs/remotes/origin/master
    }

    private Map<String, String> definirFiltros(HttpServletRequest req) {
    	Map<String, String> filtros = new HashMap<>();

		// definir os valores do map condicionalmente, conforme a requisição
		if (req.getParameter("departamento") != null) {
			filtros.put("id-depto", req.getParameter("departamento"));
=======
		} catch (NumberFormatException excecaoFormatoErrado) {
			System.err.println("Número inteiro inválido para o parâmetro. Erro: "+excecaoFormatoErrado.toString());
		} catch (SQLException excecaoSQL) {
			System.err.println("Busca SQL inválida. Erro: "+excecaoSQL.toString());
		} catch (IOException e) {
			System.err.println("Não foi possível mostrar o resultado, erro ao pegar Writer. Erro: "+e.toString());
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
