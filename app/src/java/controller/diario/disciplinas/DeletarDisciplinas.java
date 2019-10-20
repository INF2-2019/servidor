
package controller.diario.disciplinas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        response.addHeader("Access-Control-Allow-Origin", "*");
	response.addHeader("Content-Type", "text/xml; charset=utf-8");
        
        
        String id = request.getParameter("id");
            try{
                DisciplinaRep.DeletarDisciplina(id);
            } catch (SQLException ex) {
                     System.out.println(ex.toString());
            }
               
    }

}





