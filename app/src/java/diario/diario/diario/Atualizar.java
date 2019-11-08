package diario.diario.diario;

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
@WebServlet(urlPatterns = {"/diario/diario/diario/atualizar"})
public class Atualizar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        /*
            conteudo:int - id do conteudo a ser alterado
            matricula:int - id da matricula a ser alterado
            ? falta:int - quantidade de faltas do aluno
            ? nota:Double - nota do aluno
        */
        
        PrintWriter out = response.getWriter();
        Headers.XMLHeaders(response);
        
        String parametro_falta = ChecaParametro.parametroFaltante(request, "conteudo", "matricula");
        if (parametro_falta != null) {
            out.print(RespostaXML.erro("Erro com '" + parametro_falta + "'", "O parametro '" + parametro_falta + "' é obrigatório!"));
            return;
        }

        String query = "UPDATE diario SET ";
        List<String> modificacoes = new ArrayList<String>();

        if (ChecaParametro.parametroExiste(request, "falta")) {
            if (!ChecaParametro.parametroEInteiro(request, "falta")) {
                out.print(RespostaXML.erro("'falta' deve ser inteiro!", "Falha no formato do parametro 'falta'"));
                return;
            } else {
                modificacoes.add("faltas="+request.getParameter("falta"));
            }
        }
        
        
        if (ChecaParametro.parametroExiste(request, "nota")) {
            if (!ChecaParametro.parametroEDecimal(request, "nota")) {
                out.print(RespostaXML.erro("'nota' deve ser decimal!", "Falha no formato do parametro 'nota'"));
                return;
            } else {
                modificacoes.add("nota="+request.getParameter("nota"));
            }
        }

        try {
                
            if(modificacoes.size()==0){
                out.print(RespostaXML.erro("Nenhuma alteração detectada!","Nenhum parametro de modificação recebido"));
                return;
            }
            query += String.join(",", modificacoes) + " WHERE `id-conteudos`="+request.getParameter("conteudo")+" AND `id-matriculas`="+request.getParameter("matricula");
            
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
