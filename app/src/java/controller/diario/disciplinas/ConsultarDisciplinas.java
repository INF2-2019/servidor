
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.diario.disciplinas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.diario.DisciplinaModel;
import repository.diario.DisciplinaRepository;
import utils.ConnectionFactory;
import utils.Headers;
import views.RenderException;
import views.View;
import views.diario.disciplinas.DisciplinaConsultaView;

/**
 *
 * @author User
 */
@WebServlet(name = "ConsultarDisciplinas", urlPatterns = {"/diario/disciplinas/consultar"})
public class ConsultarDisciplinas extends HttpServlet {

 protected void doGet(HttpServletRequest request, HttpServletResponse response){
			Headers.XMLHeaders(response);
			Connection conexao = ConnectionFactory.getDiario();

			if(conexao == null){
				System.err.println("Falha ao conectar ao bd"); // Adicionar XML de erro
				return;
			}

			DisciplinaRepository DisciplinaRep = new DisciplinaRepository(conexao);

			Set<DisciplinaModel> resultado;
			Map<String, String> filtros = definirMap(request); // criando um Map para armazenar os filtros de maneira pratica

			try {
				PrintWriter out = response.getWriter();
				resultado = DisciplinaRep.consultar(filtros); // Executa consulta
				View cursoConsultaView = new DisciplinaConsultaView(resultado);
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
			try{
				conexao.close();
			} catch(SQLException erro) {
				System.err.println("Erro ao fechar banco de dados. Erro: "+erro.toString());
			}

    }
  public Map<String, String> definirMap(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();

		// definir os valores do map condicionalmente, conforme a requisição
		if (req.getParameter("turma") != null) {
			dados.put("id-turmas", req.getParameter("turma"));
		}

		if (req.getParameter("nome") != null) {
			dados.put("nome", req.getParameter("nome"));
		}

		if (req.getParameter("horas") != null) {
			dados.put("carga-horaria-min", req.getParameter("horas"));
		}

		return dados;
	}
}





