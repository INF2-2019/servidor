package diario.diario.diario;

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
@WebServlet(urlPatterns = {"/diario/diario/diario/deletar"})
public class Deletar extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        /*
            [especifico:String[conteudo,atividade]] - String que filtra deletar entre conteudo e atividade 
                especifico = "conteudo" - deleta apenas conteudo
                especifico = "atividade" - deleta apenas atividade
            conteudo:int - id do conteudo a ser deletado
            [matricula:int] - id da matricula a ser deletado
        */
        
        PrintWriter out = response.getWriter();
        Headers.XMLHeaders(response);
        
        String query;
        query = "DELETE FROM diario";
        List<String> filtro = new ArrayList<String>();

        boolean mostra_valor = true;
        
        /* Parametro opcional */
        if (ChecaParametro.parametroExiste(request, "especifico")) {
            String oq = request.getParameter("especifico");
            if ("conteudo".equals(oq)) {
                filtro.add("valor=0");
                mostra_valor = false;
            } else if ("atividade".equals(oq)) {
                filtro.add("valor>0");
            } else {
                out.print(RespostaXML.erro("'especifico' não esta formatado corretamente", "O 'especifico' pode ser 'conteudo' ou 'atividade'"));
                return;
            }
        }
        
        /* Parametro obrigatório a todos menos o ADMIN */
        if (ChecaParametro.parametroExiste(request, "conteudo")) {
            if (!ChecaParametro.parametroEInteiro(request, "conteudo")) {
                out.print(RespostaXML.erro("O ID do conteudo deve ser inteiro!", "Falha no formato do parametro 'conteudo'"));
                return;
            } else {
                filtro.add("`id-conteudos`="+request.getParameter("conteudo"));
            }
        } else {
            out.print(RespostaXML.erro("Especifique o conteudo!","Para deletar deve se especificar ao menos um id-conteudo"));
            return;
        }
        
        /* Parametro opcional */
        if (ChecaParametro.parametroExiste(request, "matricula")) {
            if (!ChecaParametro.parametroEInteiro(request, "matricula")) {
                out.print(RespostaXML.erro("'matricula' deve ser inteiro!", "Falha no formato do parametro 'matricula'"));
                return;
            } else {
                filtro.add("`id-matriculas`="+request.getParameter("matricula"));
            }
        }
        
        query+= " WHERE "+String.join(" AND ", filtro);
                  
        try {
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
