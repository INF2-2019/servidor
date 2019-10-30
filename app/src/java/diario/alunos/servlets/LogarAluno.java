package diario.alunos.servlets;

import diario.alunos.repository.AlunosRepository;
import diario.campi.view.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConnectionFactory;
import utils.Headers;
import utils.autenticador.DiarioAutenticador;

/**
 *
 * @author User
 */
@WebServlet(name = "LogarAluno", urlPatterns = {"/diario/alunos/logar"})
public class LogarAluno extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conexao = ConnectionFactory.getDiario();
		AlunosRepository rep = new AlunosRepository(conexao);
		PrintWriter out = response.getWriter();
		String senha = request.getParameter("senha");
		String id = request.getParameter("id");
		Headers.XMLHeaders(response);

		try {
			if(rep.logarAluno(request, response, id, senha)) {
				
			}
			else
				out.println("<erro><mensagem>Senha incorreta</mensagem></erro>");
			conexao.close();
		} catch(SQLException ex) {
			out.println("<erro><mensagem>Usuario inexistente</mensagem></erro>");
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(LogarAluno.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvalidKeySpecException ex) {
			Logger.getLogger(LogarAluno.class.getName()).log(Level.SEVERE, null, ex);
		}
             
    }

}
