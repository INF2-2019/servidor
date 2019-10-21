/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.disciplinas;

import java.io.IOException;
import java.io.PrintWriter;
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
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import diario.disciplinas.model.DisciplinaModel;
import diario.disciplinas.repository.DisciplinaRepository;
import utils.ConnectionFactory;
import utils.Headers;
import diario.disciplinas.views.RenderException;
import diario.disciplinas.views.View;
import diario.disciplinas.views.DisciplinaConsultaView;
import diario.disciplinas.views.ErroView;
import java.util.HashSet;
import utils.Headers;
/**
 *
 * @author User
 */
@WebServlet(name = "ConsultarDisciplinas", urlPatterns = {"/diario/disciplinas/consultar"})
public class ConsultarDisciplinas extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Headers.XMLHeaders(response);
			Connection conexao = ConnectionFactory.getDiario();
                        PrintWriter out = response.getWriter();
			if(conexao == null){
				System.err.println("Falha ao conectar ao bd");
				View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
				try {
					erroView.render(out);
				} catch (RenderException e) {
					throw new ServletException(e);
				}
				return;
			}

			DisciplinaRepository DisciplinaRep = new DisciplinaRepository(conexao);

			Set<DisciplinaModel> resultado;
			Map<String, String> filtros = definirMap(request); // criando um Map para armazenar os filtros de maneira pratica
                        try {
				if(request.getParameterMap().containsKey("id")){
					resultado = new HashSet<>();
                                        
					resultado.add(DisciplinaRep.consultarId(request.getParameter("id")));
				} else {
					resultado = DisciplinaRep.consultar(definirMap(request));
				}
                                
                                
                                View DisciplinaConsultaView = new DisciplinaConsultaView(resultado);
				DisciplinaConsultaView.render(out);
			} catch(NumberFormatException excecaoFormatoErrado) {
				response.setStatus(400);
				System.err.println("Número inteiro inválido para o parâmetro. Erro: "+excecaoFormatoErrado.toString());

				View erroView = new ErroView(excecaoFormatoErrado);
				try {
					erroView.render(out);
				} catch (RenderException e) {
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
