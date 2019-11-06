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
@WebServlet(urlPatterns = {"/diario/diario/conteudo/inserir"})
public class Inserir extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        
        PrintWriter out = response.getWriter();
        Headers.XMLHeaders(response);
        
        String parametro_falta = ChecaParametro.parametroFaltante(request, "etapa", "disciplina", "conteudo", "data");
        if (parametro_falta != null) {
            out.print(RespostaXML.erro("Erro com '" + parametro_falta + "'", "O parametro '" + parametro_falta + "' é obrigatório!"));
            return;
        }

        if (!ChecaParametro.parametroEInteiro(request, "etapa")) {
            out.print(RespostaXML.erro("'etapa' não é inteiro", "Falha no formato do parametro 'etapa'"));
            return;
        }

        if (!ChecaParametro.parametroEInteiro(request, "disciplina")) {
            out.print(RespostaXML.erro("'disciplina' não é inteiro", "Falha no formato do parametro 'disciplina'"));
            return;
        }

        if (!ChecaParametro.parametroEData(request, "data")) {
            out.print(RespostaXML.erro("'data' não esta formatada corretamente", "Falha no formato do parametro 'data'"));
            return;
        }

        String id_etapas_string = request.getParameter("etapa"),
                id_disciplinas_string = request.getParameter("disciplina"),
                conteudos = request.getParameter("conteudo"),
                datas = request.getParameter("data"),
                valor_string = request.getParameter("valor");

        int id_etapas = Integer.parseInt(id_etapas_string),
                id_disciplinas = Integer.parseInt(id_disciplinas_string);

        Date date = Date.valueOf(datas);

        Double valor = 0.0;
        if (ChecaParametro.parametroExiste(request, "valor")) {
            if (!ChecaParametro.parametroEDecimal(request, "valor")) {
                out.print(RespostaXML.erro("'valor' não esta formatado corretamente", "Falha no formato do parametro 'valor'"));
                return;
            } else {
                if(valor<0.0){
                    out.print(RespostaXML.erro("O valor não pode ser negativo!", "O campo valor aceita apenas numeros positivos"));
                    return;
                }
                valor = Double.valueOf(valor_string);
            }
        }
        
        if (!ChecaParametro.parametroNaoVazio(request, "conteudo")) {
            out.print(RespostaXML.erro((valor>0.0? "Atividade":"Conteudo")+" não pode estar vazio!", "O parametro 'conteudo' não pode ser vazio"));
            return;
        }

        // Query SQL de inserção na tabela DESCARTES
        String query = "INSERT INTO conteudos(`id-etapas`,`id-disciplinas`, conteudos , data, valor) VALUES (?,?,?,?,?)";
        
        try {
            // Conecta e executa Query SQL
            Connection conexao = ConnectionFactory.getDiario();
            
            if(conexao==null){
                out.print(RespostaXML.erro("Falha na conexão!","Falha em tentar conectar com o banco de dados"));
                return;
            }
            
            PreparedStatement st = conexao.prepareStatement(query);

            st.setInt(1, id_etapas); //id-etapas
            st.setInt(2, id_disciplinas); //id-disciplinas
            st.setString(3, conteudos); // conteudos
            st.setDate(4, date); //data
            st.setDouble(5, valor); // valor

            int r = st.executeUpdate();

            st.close();
            conexao.close();
            
            String mensagem = "";
            if(valor>0.0) mensagem =  "Atividade adicionada com sucesso!";
            else mensagem =  "Conteudo adicionado com sucesso!";
            
            String xml = RespostaXML.sucesso(mensagem);
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
