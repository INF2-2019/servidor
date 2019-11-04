package biblioteca.emprestimos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import biblioteca.emprestimos.model.EmprestimoModel;
import biblioteca.emprestimos.repository.EmprestimoRepository;
import biblioteca.emprestimos.views.AlunoException;
import biblioteca.emprestimos.views.EmprestimoConsultaView;
import utils.ConnectionFactory;
import biblioteca.emprestimos.views.RenderException;
import biblioteca.emprestimos.views.View;
import biblioteca.emprestimos.views.ErroView;
import java.text.ParseException;
import java.util.HashSet;
import utils.Headers;
import utils.autenticador.BibliotecaAutenticador;
import utils.autenticador.BibliotecaCargos;

@WebServlet(name = "ConsultarEmprestimos", urlPatterns = {"/biblioteca/emprestimos/consultar"})
public class ConsultarEmprestimos extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(response);
		Connection conexao = ConnectionFactory.getBiblioteca();
		PrintWriter out = response.getWriter();

		BibliotecaAutenticador autenticador = new BibliotecaAutenticador(request, response);

		if ((autenticador.cargoLogado() != BibliotecaCargos.ADMIN) || (autenticador.cargoLogado() != BibliotecaCargos.OPERADOR)) {
			response.setStatus(403);
			View erroView = new ErroView(new Exception("O usuario não tem permisão para essa operação"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}
		if (conexao == null) {
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
		try {
			Map<String, String> filtros = EmprestimoModel.definirMap(request);
			resultado = new HashSet<>();
			resultado = emprestimoRep.consultar(filtros);
			View EmprestimoConsultaView = new EmprestimoConsultaView(resultado);
			EmprestimoConsultaView.render(out);
		} catch (NumberFormatException excecaoFormatoErrado) {
			response.setStatus(400);
			View erroView = new ErroView(excecaoFormatoErrado);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		} catch (SQLException excecaoSQL) {
			response.setStatus(500);
			View erroView = new ErroView(excecaoSQL);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			try {
				conexao.close();
			} catch (SQLException erro) {
				response.setStatus(500);
				erroView = new ErroView(new Exception("Falha ao realizar a pesquisa no banco de dados."));
				try {
					erroView.render(out);
				} catch (RenderException e) {
					throw new ServletException(e);
				}
				return;
			}
		} catch (RenderException ex) {
			throw new ServletException(ex);
		} catch (ParseException ex) {
			response.setStatus(400);
			ErroView erroView = new ErroView(new Exception("Alguma informação fornecida está desformatada."));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		} catch (AlunoException ex) {
			response.setStatus(400);
			ErroView erroView = new ErroView(ex);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

	}
}
