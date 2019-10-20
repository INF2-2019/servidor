package controller.diario.campi;

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
import repository.diario.CampiRepository;
import utils.ConnectionFactory;
import utils.Headers;

/**
 *
 * @author User
 */
@WebServlet(name = "DeletarCampi", urlPatterns = {"/diario/campi/deletar"})
public class DeletarCampi extends HttpServlet {

     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conexao = ConnectionFactory.getDiario();
        CampiRepository Rep = new CampiRepository(conexao);
        
        Headers.XMLHeaders(response);
        
        
        String id = request.getParameter("id");
            try{
                Rep.deletarCampi(id);
            } catch (SQLException ex) {
                     System.out.println(ex.toString());
            }
               
    }

}
