package diario.diario.diario;

import diario.diario.utils.ErroException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(urlPatterns = {"/diario/diario/diario/consulta"})
public class Consulta extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        /*
            [tipo:String[conteudo,atividade]] - String que filtra consulta entre conteudo e atividade 
                tipo = "conteudo" - mostra apenas conteudo
                tipo = "atividade" - mostra apenas atividade
            conteudo:int - id do conteudo a ser consultado
            [matricula:int] - id da matricula a ser consultado 
        */
        
        PrintWriter out = response.getWriter();
        Headers.XMLHeaders(response);
        
        try{
            DiarioParametros p = new DiarioParametros();
            p.setParametros(request);
            p.obrigatorios("tipo");
            
            boolean tem_conteudo = p.existe("conteudo"),
                    tem_matricula = p.existe("matricula"),
                    e_atividade = p.getTipo().equals("atividade");
            
            ArrayList<DiarioModel> resultado;
            if(tem_conteudo && tem_matricula){
                resultado = DiarioRepository.consulta(p.getIdConteudo(), p.getIdMatricula());
                out.print(DiarioView.consulta(resultado));
                
            } else if(tem_conteudo){
                resultado = DiarioRepository.consultaPorConteudo(p.getIdConteudo());
                out.print(DiarioView.consultaSemNota(resultado));
                
            } else if(tem_matricula){
                resultado = DiarioRepository.consultaPorMatricula(p.getIdMatricula(),e_atividade);
                out.print(DiarioView.consultaSemFalta(resultado));
                
            } else
                throw new ErroException("Acesso negado!","O acesso a toda tabela é um recurso exclusivo do Administrador");
            
            
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
