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
@WebServlet(urlPatterns = {"/diario/diario/conteudo/atualizar"})
public class Atualizar extends HttpServlet {

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

        String query = "UPDATE conteudos SET ";
        List<String> modificacoes = new ArrayList<String>();

        if (ChecaParametro.parametroExiste(request, "etapa")) {
            if (!ChecaParametro.parametroEInteiro(request, "etapa")) {
                out.print(RespostaXML.erro("'etapa' deve ser inteiro!", "Falha no formato do parametro 'etapa'"));
                return;
            } else {
                modificacoes.add("`id-etapas`="+request.getParameter("etapa"));
            }
        }
        
        if (ChecaParametro.parametroExiste(request, "disciplina")) {
            if (!ChecaParametro.parametroEInteiro(request, "disciplina")) {
                out.print(RespostaXML.erro("'disciplina' deve ser inteiro!", "Falha no formato do parametro 'disciplina'"));
                return;
            } else {
                modificacoes.add("`id-disciplinas`="+request.getParameter("disciplina"));
            }
        }
        
        if (ChecaParametro.parametroExiste(request, "conteudo")) {
            if (!ChecaParametro.parametroNaoVazio(request, "conteudo")) {
                out.print(RespostaXML.erro("'conteudo' não pode estar vazio!", "O parametro 'conteudo' não pode ser vazio"));
                return;
            } else {
                modificacoes.add("conteudos=\""+request.getParameter("conteudo")+"\"");
            }
        }
        
        if (ChecaParametro.parametroExiste(request, "data")) {
            if (!ChecaParametro.parametroEData(request, "data")) {
                out.print(RespostaXML.erro("'data' não esta formatada corretamente", "Falha no formato do parametro 'data'"));
                return;
            } else {
                modificacoes.add("data=\""+request.getParameter("data")+"\"");
            }
        }
        
        if (ChecaParametro.parametroExiste(request, "valor")) {
            if (!ChecaParametro.parametroEInteiro(request, "valor")) {
                out.print(RespostaXML.erro("'valor' deve ser decimal!", "Falha no formato do parametro 'valor'"));
                return;
            } else {
                modificacoes.add("valor="+request.getParameter("valor"));
            }
        }

        try {
                
            if(modificacoes.size()==0){
                out.print(RespostaXML.erro("Nenhuma alteração detectada!","Nenhum parametro de modificação recebido"));
                return;
            }
            query += String.join(",", modificacoes) + " WHERE id="+request.getParameter("id");
            
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

            String xml = RespostaXML.sucesso("Atualizado com sucesso!");
            out.print(xml);
        } catch (SQLException e) {
            out.print(RespostaXML.erro("Erro na conexão!", e.getMessage()));
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
            Logger.getLogger(Atualizar.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Atualizar.class.getName()).log(Level.SEVERE, null, ex);
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
