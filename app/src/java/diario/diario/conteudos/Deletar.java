package diario.diario.conteudos;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
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
@WebServlet(urlPatterns = {"/diario/diario/conteudo/deletar"})
public class Deletar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        
        PrintWriter out = response.getWriter();
        Headers.XMLHeaders(response);
        
        if (ChecaParametro.parametroExiste(request, "id")) {
            if (!ChecaParametro.parametroEInteiro(request, "id")) {
                out.print(RespostaXML.erro("'id' deve ser inteiro!", "Falha no formato do parametro 'id'"));
                return;
            }
        } else {
            out.print(RespostaXML.erro("ID é obrigatório!", "O parametro 'id' é obrigatório!"));
            return;
        }

        String query = "DELETE FROM conteudos WHERE id="+request.getParameter("id");
        
        try {
            // Conecta e executa Query SQL
            Connection conexao = ConnectionFactory.getDiario();
            
            if(conexao==null){
                out.print(RespostaXML.erro("Falha na conexão!","Falha em tentar conectar com o banco de dados"));
                return;
            }
            
            PreparedStatement st = conexao.prepareStatement(query);
            st.executeUpdate();
            st.close();
            conexao.close();

            String xml = RespostaXML.sucesso("Deletado com sucesso!");
            out.print(xml);
        } catch (SQLException e) {
            out.print(RespostaXML.erro("Erro na operação!", e.getMessage()));
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
            Logger.getLogger(Deletar.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Deletar.class.getName()).log(Level.SEVERE, null, ex);
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
