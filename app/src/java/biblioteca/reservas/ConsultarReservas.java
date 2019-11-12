package biblioteca.reservas;

import biblioteca.reservas.model.ReservaModel;
import biblioteca.reservas.repository.ReservaRepository;
import biblioteca.reservas.views.ErroView;
import biblioteca.reservas.views.RenderException;
import biblioteca.reservas.views.ReservaConsultaView;
import biblioteca.reservas.views.View;
import utils.ConnectionFactory;
import utils.Headers;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "ConsultarReservas", urlPatterns = {"/biblioteca/reservas/consultar"})
public class ConsultarReservas extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Headers.XMLHeaders(request, response);
		Connection conexao = ConnectionFactory.getBiblioteca();
		PrintWriter out = response.getWriter();
		BibliotecaAutenticador autenticador = new BibliotecaAutenticador(request, response);

		if (autenticador.cargoLogado() == BibliotecaCargos.CONVIDADO) {
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
			System.err.println("Falha ao conectar ao bd");
			View erroView = new ErroView(new Exception("Não foi possível conectar ao banco de dados"));
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
			return;
		}

		ReservaRepository reservaRep = new ReservaRepository(conexao);

		Set<ReservaModel> resultado;
		Map<String, String> filtros = definirMap(request); // criando um Map para armazenar os filtros de maneira pratica
		try {
			resultado = new HashSet<>();
			resultado = reservaRep.consultar(definirMap(request));
			View DisciplinaConsultaView = new ReservaConsultaView(resultado);
			DisciplinaConsultaView.render(out);
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
			response.setStatus(400);
			System.err.println("Algum dado está com seu tipo errado. Erro: " + ex.toString());

			View erroView = new ErroView(ex);
			try {
				erroView.render(out);
			} catch (RenderException e) {
				throw new ServletException(e);
			}
		}
	}

	public Map<String, String> definirMap(HttpServletRequest req) {
		Map<String, String> dados = new LinkedHashMap<>();

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
