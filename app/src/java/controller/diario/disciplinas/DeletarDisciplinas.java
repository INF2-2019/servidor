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
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository.diario.DisciplinaRepository;
import utils.ConnectionFactory;

/**
 *
 * @author User
 */
@WebServlet(name = "DeletarDisciplinas", urlPatterns = {"/diario/disciplinas/deletar"})
public class DeletarDisciplinas extends HttpServlet {

     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conexao = ConnectionFactory.getDiario();
        DisciplinaRepository DisciplinaRep = new DisciplinaRepository(conexao);
        Map<String, String> filtros = new HashMap<String, String>();
        if (request.getParameter("turma") != null) {
			filtros.put("id-turmas", request.getParameter("turma"));
		}

		if (request.getParameter("nome") != null) {
			filtros.put("nome", request.getParameter("nome"));
		}

		if (request.getParameter("horas") != null) {
			filtros.put("carga-horaria-min", request.getParameter("horas"));
		}
                try{
                    DisciplinaRep.DeletarDisciplina(filtros);
                }catch(SQLException excecaoSQL){
		
                }
       
    }

}





