package biblioteca.emprestimos;

import biblioteca.emprestimos.model.EmprestimoModel;
import biblioteca.emprestimos.repository.EmprestimoRepository;
import biblioteca.emprestimos.views.AlunoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import biblioteca.emprestimos.repository.EmprestimoRepository;
import utils.ConnectionFactory;
import utils.Headers;
import biblioteca.emprestimos.views.RenderException;
import biblioteca.emprestimos.views.View;
import biblioteca.emprestimos.views.SucessoView;
import biblioteca.emprestimos.views.ErroView;
import biblioteca.emprestimos.views.InacessivelException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "InserirEmprestimos", urlPatterns = {"/biblioteca/emprestimos/inserir"})
public class InserirEmprestimos extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Connection conexao = ConnectionFactory.getBiblioteca();
        EmprestimoRepository emprestimoRep = new EmprestimoRepository(conexao);
        Headers.XMLHeaders(response);
        PrintWriter out = response.getWriter();

        if (conexao == null) {
            View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
            try {
                erroView.render(out);
            } catch (RenderException e) {
                throw new ServletException(e);
            }
            return;
        }

        try {
            Map<String, String> dados = EmprestimoModel.definirMap(request);
            emprestimoRep.inserir(dados);
            View sucessoView = new SucessoView("Inserido com sucesso.");
            sucessoView.render(out);
        } catch (NumberFormatException excecaoFormatoErrado) {
            response.setStatus(400);
            System.err.println("Número inteiro inválido para o parâmetro. Erro: " + excecaoFormatoErrado.toString());

            View erroView = new ErroView(excecaoFormatoErrado);
            try {
                erroView.render(out);
            } catch (RenderException e) {
                throw new ServletException(e);
            }
        } catch (SQLException excecaoSQL) {
            response.setStatus(500);
            System.err.println("Busca SQL inválida. Erro: " + excecaoSQL.toString());

            View erroView = new ErroView(excecaoSQL);
            try {
                erroView.render(out);
            } catch (RenderException e) {
                throw new ServletException(e);
            }
        } catch (RenderException e) {
            throw new ServletException(e);
        } catch (ParseException ex) {
            Logger.getLogger(InserirEmprestimos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InacessivelException e) {
            response.setStatus(500);
            System.err.println("Problema ao pegar o livro. Erro: " + e.toString());

            View erroView = new ErroView(e);
            try {
                erroView.render(out);
            } catch (RenderException i) {
                throw new ServletException(i);
            }
        } catch (AlunoException ex) {
            Logger.getLogger(InserirEmprestimos.class.getName()).log(Level.SEVERE, null, ex);
            View erroView = new ErroView(ex);
            try {
                erroView.render(out);
            } catch (RenderException i) {
                throw new ServletException(i);
            }
        }
    }
}
