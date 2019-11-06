package diario.diario.conteudos;

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
@WebServlet(urlPatterns = {"/diario/diario/conteudo/consulta"})
public class Consulta extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        
        PrintWriter out = response.getWriter();
        Headers.XMLHeaders(response);
        
        String query;
        query = "SELECT * FROM conteudos";
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
                out.print(RespostaXML.erro("'especifico' não esta formatado corretamente", "O 'especifico' pode ser ou 'conteudo' ou 'atividade'"));
                return;
            }
        }
        
        /* Parametro obrigatório a todos menos o ADMIN */
        if (ChecaParametro.parametroExiste(request, "disciplina")) {
            if (!ChecaParametro.parametroEInteiro(request, "disciplina")) {
                out.print(RespostaXML.erro("'disciplina' deve ser inteiro!", "Falha no formato do parametro 'disciplina'"));
                return;
            } else {
                filtro.add("`id-disciplinas`="+request.getParameter("disciplina"));
            }
        } else {
            out.print(RespostaXML.erro("Acesso negado!","O acesso a toda tabela é um recurso exclusivo do Administrador"));
            return;
        }
        
        /* Parametro opcional */
        if (ChecaParametro.parametroExiste(request, "etapa")) {
            if (!ChecaParametro.parametroEInteiro(request, "etapa")) {
                out.print(RespostaXML.erro("'etapa' deve ser inteiro!", "Falha no formato do parametro 'etapa'"));
                return;
            } else {
                filtro.add("`id-etapas`="+request.getParameter("etapa"));
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

            ResultSet resultado = st.executeQuery();
            if (mostra_valor) {
                out.print(RespostaXML.retornaSet(resultado, "id-etapas", "id-disciplinas", "conteudos", "data", "valor"));
            } else {
                out.print(RespostaXML.retornaSet(resultado, "id-etapas", "id-disciplinas", "conteudos", "data"));
            }

            st.close();
            conexao.close();
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
