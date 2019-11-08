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
        
        String parametro_falta = ChecaParametro.parametroFaltante(request, "conteudo", "matricula");
        if (parametro_falta != null) {
            out.print(RespostaXML.erro("Erro com '" + parametro_falta + "'", "O parametro '" + parametro_falta + "' é obrigatório!"));
            return;
        }
        
        Boolean is_atividade;
        
        if(!ChecaParametro.parametroExiste(request, "tipo")){
            out.print(RespostaXML.erro("Erro relativo ao tipo", "É obrigatório especificar um tipo: conteudo ou atividade"));
            return;
        } else {
            String tipo = request.getParameter("tipo");
            if("conteudo".equals(tipo))
                is_atividade = false;
            else if("atividade".equals(tipo))
                is_atividade = true;
            else{
                out.print(RespostaXML.erro("Erro relativo ao tipo", "O tipo deve ser: conteudo ou atividade"));
                return;
            }
        }
        
        if (!ChecaParametro.parametroEInteiro(request, "conteudo")) {
            out.print(RespostaXML.erro("'conteudo' não é inteiro", "Falha no formato do parametro 'conteudo'"));
            return;
        }

        if (!ChecaParametro.parametroEInteiro(request, "matricula")) {
            out.print(RespostaXML.erro("'matricula' não é inteiro", "Falha no formato do parametro 'matricula'"));
            return;
        }
        
        String id_conteudo = request.getParameter("conteudo"),
                id_matricula = request.getParameter("matricula"),
                nota_string = request.getParameter("nota"),
                falta_string = request.getParameter("falta");
        
        if (ChecaParametro.parametroExiste(request, "falta")){
            if (!ChecaParametro.parametroEInteiro(request, "falta")) {
                out.print(RespostaXML.erro("Falta deve ser inteiro", "Falha no formato do parametro 'falta'"));
                return;
            } else {
                int falta = Integer.parseInt(falta_string);
                if(falta<0){
                    out.print(RespostaXML.erro("Falta não pode ser negativa!", "O campo falta aceita apenas numeros positivos"));
                    return;
                }
                
            }
        } else if(!is_atividade){
            out.print(RespostaXML.erro("Lance alguma falta!", "O parametro 'falta' é obrigatório no tipo conteudo!"));
            return;
        }

        if (ChecaParametro.parametroExiste(request, "nota") && is_atividade) {
            if (!ChecaParametro.parametroEDecimal(request, "nota")) {
                out.print(RespostaXML.erro("A nota não está no formato adequado!", "Falha no formato do parametro 'nota'"));
                return;
            } else {
                Double nota = Double.valueOf(nota_string);
                if(nota<0.0){
                    out.print(RespostaXML.erro("A nota não pode ser negativa!", "O campo nota aceita apenas numeros positivos"));
                    return;
                }
            }
        }else if(is_atividade){
            out.print(RespostaXML.erro("Lance alguma nota!", "O parametro 'nota' é obrigatório no tipo atividade!"));
            return;
        }
        
        if(!is_atividade){
            nota_string = "0.0";
        }

        // Query SQL de inserção na tabela DESCARTES
        String query = "INSERT INTO diario(`id-conteudos`,`id-matriculas`, faltas, nota) VALUES ("+id_conteudo+", "+id_matricula+", "+falta_string+", "+nota_string+")";
        
        try {
            // Conecta e executa Query SQL
            Connection conexao = ConnectionFactory.getDiario();
            
            if(conexao==null){
                out.print(RespostaXML.erro("Falha na conexão!","Falha em tentar conectar com o banco de dados"));
                return;
            }
            
            PreparedStatement st = conexao.prepareStatement(query);

            int r = st.executeUpdate();

            st.close();
            conexao.close();
            
            String mensagem = "";
            if(is_atividade) mensagem =  "Nota lançada com sucesso!";
            else mensagem =  "Falta lançada com sucesso!";
            
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
