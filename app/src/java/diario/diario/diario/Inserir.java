package diario.diario.diario;

import diario.diario.utils.ErroException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(urlPatterns = {"/diario/diario/diario/inserir"})
public class Inserir extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        /*
            conteudo:int - id do conteudo a ser lançada a falta ou nota
            matricula:int - id da matricula a ser lançada a falta ou nota
            tipo:String[conteudo,atividade] - String que especifica se esta sendo lançado a um conteudo ou atividade,
                tipo = "conteudo" - permite que seja inserido a falta mas não a nota
                tipo = "atividade" - permite que seja inserido a nota
            falta:int - quantidade de faltas do aluno
            nota:Double - nota do aluno
        */
        
        PrintWriter out = response.getWriter();
        Headers.XMLHeaders(response);

        try{
            DiarioParametros p = new DiarioParametros();
            p.setParametros(request);
            p.obrigatorios("conteudo", "matricula","tipo");
            
            
            if(p.getTipo().equals("atividade")){
                p.obrigatorios("falta","nota");
                DiarioRepository.insere(p.getIdConteudo(), p.getIdMatricula(), p.getFalta(), p.getNota());
                out.print(DiarioView.sucesso("Nota lançada com sucesso!"));
                
            }
            else if(p.getTipo().equals("conteudo")){
                p.obrigatorios("falta");
                DiarioRepository.insere(p.getIdConteudo(), p.getIdMatricula(), p.getFalta());
                out.print(DiarioView.sucesso("Falta lançada com sucesso!"));
            } else {
                throw new ErroException("Erro inesperado!");
            }
            
        }catch(Exception e){
            if(e instanceof ErroException && ((ErroException)e).causa!=null)
                out.print(DiarioView.erro(e.getMessage(),((ErroException)e).causa));
            else
                out.print(DiarioView.erro("Erro inesperado!",e.getMessage()));
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
            Logger.getLogger(Inserir.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Inserir.class.getName()).log(Level.SEVERE, null, ex);
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
