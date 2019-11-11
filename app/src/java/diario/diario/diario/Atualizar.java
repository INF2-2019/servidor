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

	try {
	    DiarioParametros p = new DiarioParametros();
	    p.setParametros(request);
	    p.obrigatorios("conteudo", "matricula");

	    boolean tem_falta = p.existe("falta"),
		    tem_nota = p.existe("nota");

	    if (tem_falta && tem_nota) {
		DiarioRepository.atualizar(p.getIdConteudo(), p.getIdMatricula(), p.getFalta(), p.getNota());
	    } else if (tem_nota) {
		DiarioRepository.atualizar(p.getIdConteudo(), p.getIdMatricula(), p.getNota());
	    } else if (tem_falta) {
		DiarioRepository.atualizar(p.getIdConteudo(), p.getIdMatricula(), p.getFalta());
	    } else {
		throw new ExcecaoPadrao("Nenhuma alteração detectada!", "Nenhum parametro de modificação recebido");
	    }

	    String xml = DiarioView.sucesso("Atualizado com sucesso!");
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
