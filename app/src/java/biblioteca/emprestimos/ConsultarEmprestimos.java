package biblioteca.emprestimos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import biblioteca.emprestimos.model.EmprestimoModel;
import biblioteca.emprestimos.repository.EmprestimoRepository;
import biblioteca.emprestimos.views.EmprestimoConsultaView;
import utils.ConnectionFactory;
import biblioteca.emprestimos.views.RenderException;
import biblioteca.emprestimos.views.View;
import biblioteca.emprestimos.views.SucessoView;
import biblioteca.emprestimos.views.ErroView;
import java.text.ParseException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Headers;

@WebServlet(name = "ConsultarEmprestimos", urlPatterns = {"/biblioteca/emprestimos/consultar"})
public class ConsultarEmprestimos extends HttpServlet {

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

		EmprestimoRepository emprestimoRep = new EmprestimoRepository(conexao);

		Set<EmprestimoModel> resultado;
		Map<String, String> filtros = EmprestimoModel.definirMap(request); // criando um Map para armazenar os filtros de maneira pratica
		try {
			resultado = new HashSet<>();
			resultado = emprestimoRep.consultar(filtros);
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
			response.setStatus(400);
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
			Logger.getLogger(ConsultarEmprestimos.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
