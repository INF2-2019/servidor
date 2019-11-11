package biblioteca.descartes;

import biblioteca.descartes.views.ExcecaoPadrao;
import biblioteca.descartes.views.SucessoView;
import biblioteca.descartes.views.ErroView;
import biblioteca.descartes.views.ExcecaoNaoAutorizado;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.ConnectionFactory;
import utils.Headers;
import utils.autenticador.BibliotecaAutenticador;
import utils.autenticador.BibliotecaCargos;

@WebServlet(urlPatterns = {"/biblioteca/descartes/inserir"})
public class Inserir extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	PrintWriter out = response.getWriter();
        Headers.XMLHeaders(response);

	try {
            BibliotecaAutenticador autenticador = new BibliotecaAutenticador(request, response);
            if (autenticador.cargoLogado() == BibliotecaCargos.CONVIDADO) {
                throw new ExcecaoNaoAutorizado("Você não tem permissão para essa operação");
            }
            
            Connection conexao = ConnectionFactory.getBiblioteca();
            DescartesRepository repositorio = new DescartesRepository(conexao);
            DescartesParametros parametros = new DescartesParametros(request);
            parametros.obrigatorios("acervo","data","funcionario","motivacao");

            if(repositorio.inserir(parametros)){
                SucessoView view = new SucessoView();
                view.render(out);
            } else {
                ErroView view = new ErroView();
                view.render(out);
            }

            conexao.close();
	} catch (SQLException e) {
            response.setStatus(500);
            ErroView erro = new ErroView("Erro no banco de dados!", e.getMessage());
            erro.render(out);
            e.printStackTrace();
	} catch(ExcecaoNaoAutorizado e){
            response.setStatus(403);
            ErroView erro = new ErroView(e.mensagem, e.causa);
            erro.render(out);
            e.printStackTrace();
        } catch(ExcecaoPadrao e){
            response.setStatus(400);
            ErroView erro = new ErroView(e.mensagem, e.causa);
            erro.render(out);
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
	processRequest(request, response);
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
	processRequest(request, response);
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
