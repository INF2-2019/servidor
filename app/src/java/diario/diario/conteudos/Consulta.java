package diario.diario.conteudos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;
import utils.Headers;

/**
 *
 * @author Juan
 */
@WebServlet(urlPatterns = {"/diario/diario/conteudo/consulta"})
public class Consulta extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        
        PrintWriter out = response.getWriter();
        Headers.XMLHeaders(response);
        
        try{
            ConteudosParametros p = new ConteudosParametros();
            p.setParametros(request);
            p.obrigatorios("disciplina");
            

            
            ArrayList<ConteudosModel> resultado = new ArrayList<ConteudosModel>();
            if(p.existe("tipo")){
                if("conteudo".equals(p.getTipo())){
                    resultado = ConteudosRepository.consulta(p);
                    out.print(ConteudosView.consultaConteudo(resultado));
                } else if("atividade".equals(p.getTipo())){
                    resultado = ConteudosRepository.consultaAtividade(p);
                    out.print(ConteudosView.consulta(resultado));
                }else 
                    throw new ErroException("Erro inesperado!");
            } else {
                resultado = ConteudosRepository.consulta(p);
                out.print(ConteudosView.consulta(resultado));
            }
            
            
        }catch(Exception e){
            if(e instanceof ErroException && ((ErroException)e).causa!=null)
                out.print(ConteudosView.erro(e.getMessage(),((ErroException)e).causa));
            else
                out.print(ConteudosView.erro("Erro inesperado!",e.getMessage()));
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
