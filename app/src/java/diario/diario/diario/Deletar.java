package diario.diario.diario;

import diario.diario.diario.views.DiarioView;
import diario.diario.diario.views.ExcecaoPadrao;
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
@WebServlet(urlPatterns = {"/diario/diario/diario/deletar"})
public class Deletar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	/*
            ? especifico:String[conteudo,atividade] - String que filtra deletar entre conteudo e atividade
                especifico = "conteudo" - deleta apenas conteudo
                especifico = "atividade" - deleta apenas atividade
            ? conteudo:int - id do conteudo a ser deletado
            ? matricula:int - id da matricula a ser deletado
	 */

	PrintWriter out = response.getWriter();
	Headers.XMLHeaders(response);

	try {
	    DiarioParametros p = new DiarioParametros();
	    p.setParametros(request);

	    boolean tem_matricula = p.existe("matricula"),
		    tem_conteudo = p.existe("conteudo");

	    if (tem_matricula && tem_conteudo) {
		DiarioRepository.remover(p.getIdConteudo(), p.getIdMatricula());
	    } else if (tem_matricula) {
		DiarioRepository.removerPorMatricula(p.getIdMatricula());
	    } else if (tem_conteudo) {
		DiarioRepository.removerPorConteudo(p.getIdConteudo());
	    } else {
		throw new ExcecaoPadrao("Nenhum parametro selecionado!", "Ao menos um dos parametros deve estar presente: 'matricula' ou 'conteudo'");
	    }

	    String xml = DiarioView.sucesso("Deletado com sucesso!");
	    out.print(xml);
	} catch (Exception e) {
	    if (e instanceof ExcecaoPadrao && ((ExcecaoPadrao) e).causa != null) {
		out.print(DiarioView.erro(e.getMessage(), ((ExcecaoPadrao) e).causa));
	    } else {
		out.print(DiarioView.erro("Erro inesperado!", e.getMessage()));
	    }
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
