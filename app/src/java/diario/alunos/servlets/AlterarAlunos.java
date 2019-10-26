package diario.alunos.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import diario.alunos.repository.*;
import diario.cursos.view.ErroView;
import diario.cursos.view.RenderException;
import diario.cursos.view.SucessoView;
import diario.cursos.view.View;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import utils.ConnectionFactory;
import utils.Headers;

/**
 *
 * @author User
 */
@WebServlet(name = "AlterarAlunos", urlPatterns = {"/diario/alunos/alterar"})
public class AlterarAlunos extends HttpServlet {

     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conexao = ConnectionFactory.getDiario();
        AlunosRepository rep = new AlunosRepository(conexao);
        PrintWriter out = response.getWriter();
        Headers.XMLHeaders(response);
		
		if (rep.checarAutorizacaoADM(request, response)) {
        
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			String sexo = request.getParameter("sexo");
			String nascimento = request.getParameter("nascimento");
			String logradouro = request.getParameter("logradouro");
			String numero = request.getParameter("numero");
			String complemento = request.getParameter("complemento");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String cep = request.getParameter("cep");		
			String uf = request.getParameter("uf");
			String foto = request.getParameter("foto");

			try {
				rep.alterarAlunos(id, nome, email, senha, sexo, nascimento, logradouro, numero, complemento, bairro, cidade, cep, uf, foto);
				View sucessoView = new SucessoView("Atualizado com sucesso.");
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
				response.setStatus(400);
				System.err.println("Busca SQL inválida. Erro: " + excecaoSQL.toString());

				View erroView = new ErroView(excecaoSQL);
				try {
					erroView.render(out);
				} catch (RenderException e) {
					throw new ServletException(e);
				}
			} catch (RenderException e) {
				throw new ServletException(e);
			} catch (NoSuchAlgorithmException | ParseException ex) {
				Logger.getLogger(AlterarAlunos.class.getName()).log(Level.SEVERE, null, ex);
			}
			 
		} else {
			response.setStatus(401);
			out.println("<erro><mensagem>Voce nao tem permissao para fazer isso</mensagem></erro>");
		} 	
            
               
    }

}