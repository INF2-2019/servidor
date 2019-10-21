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
import org.w3c.dom.Document;
import repository.diario.DisciplinaRepository;
import utils.ConnectionFactory;
import utils.Headers;
import views.RenderException;
import views.View;
import views.diario.cursos.CursoConsultaView;

import views.diario.sucesso.SucessoView;

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
        
        Headers.XMLHeaders(response);
        
         System.out.println("AAA");
        String id = request.getParameter("id");
            try{
                PrintWriter out = response.getWriter();
                DisciplinaRep.Deletar(id);
                View Sucesso = new SucessoView("deletado");
                Sucesso.render(out);
            } catch (SQLException ex) {
                
         } catch (RenderException ex) {
             Logger.getLogger(DeletarDisciplinas.class.getName()).log(Level.SEVERE, null, ex);
         }
               
    }

}
