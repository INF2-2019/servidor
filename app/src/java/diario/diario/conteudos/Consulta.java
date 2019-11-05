package diario.diario.conteudos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
        
        String query;
        query = "SELECT * FROM conteudos";

        boolean mostra_valor = true;

        if (ChecaParametro.parametroExiste(request, "especifico")) {
            String oq = request.getParameter("especifico");
            if ("conteudo".equals(oq)) {
                query += " WHERE valor=0";
                mostra_valor = false;
            } else if ("atividade".equals(oq)) {
                query += " WHERE valor>0";
            } else {
                out.print(RespostaXML.erro("'especifico' não esta formatado corretamente", "O 'especifico' pode ser ou 'conteudo' ou 'atividade'"));
                return;
            }
        }
                  
        try {
            Connection conexao = ConnectionFactory.getDiario();
            PreparedStatement st = conexao.prepareStatement(query);

            ResultSet resultado = st.executeQuery();
            if (mostra_valor) {
                out.print(RespostaXML.retornaSet(resultado, "id-etapas", "id-disciplinas", "conteudos", "data", "valor"));
            } else {
                out.print(RespostaXML.retornaSet(resultado, "id-etapas", "id-disciplinas", "conteudos", "data"));
            }

            st.close();
            conexao.close();
        } catch (SQLException e) {
            out.print(RespostaXML.erro("Erro no banco de dados!", e.getMessage()));
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
