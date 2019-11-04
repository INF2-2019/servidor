package biblioteca.emprestimos;

import biblioteca.emprestimos.model.EmprestimoModel;
import biblioteca.emprestimos.views.RenderException;
import biblioteca.emprestimos.views.View;
//import biblioteca.emprestimos.views.SucessoView;
import biblioteca.emprestimos.views.ErroView;
import biblioteca.emprestimos.repository.EmprestimoRepository;
import biblioteca.emprestimos.views.EmprestimoConsultaView;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;
import utils.Headers;

@WebServlet(name = "ConsultarPorId", urlPatterns = {"/biblioteca/emprestimos/consultarporid"})
public class ConsultarPorId extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(response);
		Connection conexao = ConnectionFactory.getBiblioteca();
		PrintWriter out = response.getWriter();
		if (conexao == null) {
			System.err.println("Falha ao conectar ao bd");
			View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}
		EmprestimoRepository disciplinaRep = new EmprestimoRepository(conexao);
		Set<EmprestimoModel> resultado;
		try {
			resultado = new HashSet<>();
			resultado.add(disciplinaRep.consultarId(request.getParameter("id")));

			View EmprestimoConsultaView = new EmprestimoConsultaView(resultado);
			EmprestimoConsultaView.render(out);

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
			try {
				conexao.close();
			} catch (SQLException erro) {
				System.err.println("Erro ao fechar banco de dados. Erro: " + erro.toString());
			}
		} catch (RenderException ex) {
			throw new ServletException(ex);
		} catch (ParseException ex) {
			Logger.getLogger(ConsultarPorId.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
