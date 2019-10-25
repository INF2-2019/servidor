package diario.alunos.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import diario.alunos.repository.*;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
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
        AlunosRepository Rep = new AlunosRepository(conexao);
        
        Headers.XMLHeaders(response);
        
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
			 if(Rep.alterarAlunos(id, nome, email, senha, sexo, nascimento, logradouro, numero, complemento, bairro, cidade, cep, uf, foto))
				 System.out.println("Alterado!");
			 else
				 System.out.println("Não foi possível alterar");
		 } catch (NumberFormatException ex) {
			 Logger.getLogger(AlterarAlunos.class.getName()).log(Level.SEVERE, null, ex);
		 } catch (SQLException ex) {
			 Logger.getLogger(AlterarAlunos.class.getName()).log(Level.SEVERE, null, ex);
		 } catch (NoSuchAlgorithmException ex) {
			 Logger.getLogger(AlterarAlunos.class.getName()).log(Level.SEVERE, null, ex);
		 } catch (ParseException ex) {
			 Logger.getLogger(AlterarAlunos.class.getName()).log(Level.SEVERE, null, ex);
		 }
            
               
    }

}