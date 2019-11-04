package diario.turmas.controllers;

import diario.turmas.repository.TurmasRepository;
import static diario.turmas.views.Views.retornaErro;
import static diario.turmas.views.Views.retornaSucesso;
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
import utils.autenticador.DiarioAutenticador;
import static utils.autenticador.DiarioCargos.*;

@WebServlet(name = "DeletaTurma", urlPatterns = {"/diario/turmas/deletar"})
public class DeletaTurma extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Headers.XMLHeaders(res);
        res.setContentType("text/xml;charset=UTF-8");
        
        DiarioAutenticador aut = new DiarioAutenticador(req, res);
        if(aut.cargoLogado() != ADMIN){
            res.setStatus(403);
            return;
        }
        
        Connection c = ConnectionFactory.getDiario();
        TurmasRepository tr = new TurmasRepository(c);
        
        String id = req.getParameter("id");
        
        PrintWriter out = res.getWriter();
        try{
			if(id == null){
				out.println(retornaErro("Parâmetros insufucientes"));
			}
			else if(tr.deletaTurma(id)){
                out.println(retornaSucesso("Turma "+id+" removida com sucesso."));
            }
            else{
                out.println(retornaErro("Erro ao tentar remover a turma "+id));
            }
        }
        catch(SQLException e){
            out.println(retornaErro("Erro ao tentar se conectar com o banco de dados. Exceção: "+e));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para deletar uma turma";
    }
}
