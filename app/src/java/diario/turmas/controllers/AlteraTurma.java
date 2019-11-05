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

@WebServlet(name = "AlteraTurma", urlPatterns = {"/diario/turmas/alterar"})
public class AlteraTurma extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Headers.XMLHeaders(res);

        PrintWriter out = res.getWriter();

        DiarioAutenticador aut = new DiarioAutenticador(req, res);// Autenticação de usuário
        if (aut.cargoLogado() != ADMIN) {
            res.setStatus(403);
            out.println(retornaErro("Você não tem permissão para realizar essa ação."));
            return;
        }

        Connection c = ConnectionFactory.getDiario();
        TurmasRepository tr = new TurmasRepository(c);

        String id = req.getParameter("id"),
                idCursos = req.getParameter("idCursos"),
                nome = req.getParameter("nome");

        try {
            if (id == null || idCursos == null || nome == null) {// Erro para algum parâmetro faltando
                out.println(retornaErro("Parâmetros insufucientes"));
            } else if (tr.alteraTurma(id, idCursos, nome)) { // Sucesso
                out.println(retornaSucesso("Turma " + id + " alterada com sucesso."));
            } else { // Erro de alteração de turma
                out.println(retornaErro("Erro ao tentar alterar a turma " + id));
            }
        } catch (SQLException e) {
            out.println(retornaErro("Erro ao tentar se conectar com o banco de dados. Exceção: " + e));
        } catch (NumberFormatException e) {
            out.println(retornaErro("Tipo de parâmetro inválido. Exceção: " + e));
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
