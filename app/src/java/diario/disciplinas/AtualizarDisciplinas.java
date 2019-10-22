package diario.disciplinas;

import diario.disciplinas.repository.DisciplinaRepository;
import diario.disciplinas.views.SucessoView;
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
import java.util.SortedMap;
import java.util.TreeMap;
import diario.disciplinas.views.RenderException;
import diario.disciplinas.views.View;
import diario.disciplinas.views.ErroView;


@WebServlet(name = "AtualizarDisciplinas", urlPatterns = {"/diario/disciplinas/atualizar"})
public class AtualizarDisciplinas extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Headers.XMLHeaders(res);
        Connection con = ConnectionFactory.getDiario();
        PrintWriter out = res.getWriter();
        if (con == null) {
            View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
            try {
                erroView.render(out);
            } catch (RenderException e) {
                throw new ServletException(e);
            }
            return;
        }

        DisciplinaRepository DisciplinaRep = new DisciplinaRepository(con);
        String id = req.getParameter("id");
        SortedMap<String, String> filtros = definirMap(req);
        try {
            DisciplinaRep.atualizar(filtros, id);
            View sucessoView = new SucessoView("Atualizado com sucesso.");
            sucessoView.render(out);
        } catch (NumberFormatException excecaoFormatoErrado) {
            res.setStatus(400);
            System.err.println("Número inteiro inválido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());

            View erroView = new ErroView(excecaoFormatoErrado);
            try {
                erroView.render(out);
            } catch (RenderException e) {
                throw new ServletException(e);
            }
        } catch (SQLException excecaoSQL) {
            res.setStatus(400);
            System.err.println("Busca SQL inválida. Erro: " + excecaoSQL.toString());

            View erroView = new ErroView(excecaoSQL);
            try {
                erroView.render(out);
            } catch (RenderException e) {
                throw new ServletException(e);
            }
        } catch (RenderException e) {
            throw new ServletException(e);
        }

    }

    public SortedMap<String, String> definirMap(HttpServletRequest req) {
        SortedMap<String, String> dados = new TreeMap<String, String>();

        // definir os valores do map condicionalmente, conforme a requisição
        if (req.getParameter("turma") != null) {
            dados.put("id-turmas", req.getParameter("turma"));
        }

        if (req.getParameter("nome") != null) {
            dados.put("nome", req.getParameter("nome"));
        }

        if (req.getParameter("horas") != null) {
            dados.put("carga-horaria-min", req.getParameter("horas"));
        }

        return dados;
    }
}
