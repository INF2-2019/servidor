package diario.turmas.controllers;

import diario.turmas.models.TurmaModel;
import static diario.turmas.views.Views.retornaConsulta;
import static diario.turmas.views.Views.retornaErro;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;
import utils.Headers;
import utils.autenticador.DiarioAutenticador;
import static utils.autenticador.DiarioCargos.CONVIDADO;

@WebServlet(name = "ConsultaTurmas", urlPatterns = {"/diario/turmas/consultar"})
public class ConsultaTurmas extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Headers.XMLHeaders(res);
        res.setContentType("text/xml;charset=UTF-8");
        
        Connection con = ConnectionFactory.getDiario();
        
        DiarioAutenticador aut = new DiarioAutenticador(req, res);;;
        if(aut.cargoLogado() == CONVIDADO){
            res.setStatus(403);
            return;
        }
        
        PrintWriter out = res.getWriter();
        try{
            String sql = "SELECT * FROM turmas";
			if(req.getParameter("id") != null) sql += " WHERE id="+req.getParameter("id")+",";
            PreparedStatement prepared = con.prepareStatement(sql);
            ResultSet rs = prepared.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            ArrayList<TurmaModel> arr = new ArrayList<>();
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i+=3) {
                    arr.add(new TurmaModel(rs.getInt(i), rs.getInt(i + 1), rs.getString(i + 2)));
                }
            }
            out.printf(retornaConsulta(arr));
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
        return "Mostra todas as turmas em XML";
    }

}
