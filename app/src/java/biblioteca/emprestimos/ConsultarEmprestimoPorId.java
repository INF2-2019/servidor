package biblioteca.emprestimos;

import biblioteca.emprestimos.model.EmprestimoModel;
import biblioteca.emprestimos.repository.EmprestimoRepository;
import biblioteca.emprestimos.views.EmprestimoConsultaView;
import biblioteca.emprestimos.views.ErroView;
import biblioteca.emprestimos.views.RenderException;
import biblioteca.emprestimos.views.View;
import utils.ConnectionFactory;
import utils.autenticador.BibliotecaAutenticador;
import utils.autenticador.BibliotecaCargos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "ConsultarEmprestimoPorId", urlPatterns = {"/biblioteca/emprestimos/consultarporid"})
public class ConsultarEmprestimoPorId extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conexao = ConnectionFactory.getBiblioteca();
		PrintWriter out = response.getWriter();

		BibliotecaAutenticador autenticador = new BibliotecaAutenticador(request, response);
		if ((autenticador.cargoLogado() != BibliotecaCargos.ADMIN) && (autenticador.cargoLogado() != BibliotecaCargos.OPERADOR)) {
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
			response.setStatus(500);
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
				erroView = new ErroView(new Exception("Erro ao fechar banco de dados."));
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
			View erroView = new ErroView(new Exception("Alguma informação fornecida está desformatada."));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

	}
}
