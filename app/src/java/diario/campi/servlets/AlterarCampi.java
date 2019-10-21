package diario.campi.servlets;

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
import diario.campi.repository.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import utils.ConnectionFactory;
import utils.Headers;

/**
 *
 * @author User
 */
@WebServlet(name = "AlterarCampi", urlPatterns = {"/diario/campi/alterar"})
public class AlterarCampi extends HttpServlet {

     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conexao = ConnectionFactory.getDiario();
        CampiRepository Rep = new CampiRepository(conexao);
        
        Headers.XMLHeaders(response);
        
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String cidade = request.getParameter("cidade");
        String uf = request.getParameter("uf");
            try{
                if(Rep.alterarCampi(id, nome, cidade, uf))
                    System.out.println("Alterado!");
                else
                    System.out.println("Não foi possível alterar");
            } catch (SQLException ex) {
                     System.out.println(ex.toString());
            } catch (NumberFormatException ex) {
             Logger.getLogger(AlterarCampi.class.getName()).log(Level.SEVERE, null, ex);
         } catch (TransformerException ex) {
             Logger.getLogger(AlterarCampi.class.getName()).log(Level.SEVERE, null, ex);
         } catch (ParserConfigurationException ex) {
             Logger.getLogger(AlterarCampi.class.getName()).log(Level.SEVERE, null, ex);
         }
               
    }

}
