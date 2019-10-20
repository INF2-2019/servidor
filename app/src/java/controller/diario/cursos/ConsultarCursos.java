package controller.diario.cursos;

import model.diario.CursoModel;
import repository.diario.CursoRepository;
import utils.ConnectionFactory;
import utils.Conversores;
import view.diario.cursos.CursoView;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "ConsultarCursos", urlPatterns = "/diario/cursos/consultar")
public class ConsultarCursos extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
		Connection conexao = ConnectionFactory.getDiario();
		CursoRepository cursoRep = new CursoRepository(conexao);

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "text/xml; charset=utf-8");

		Set<CursoModel> resultado;
		Map<String, String> filtros = cursoRep.definirMap(request); // criando um Map para armazenar os filtros de maneira pratica

		try {

			resultado = cursoRep.consultar(filtros); // Executa consulta
			String xmlRetorno = CursoView.setParaXML(resultado); // Transforma em XML

			response.getWriter().print(xmlRetorno);

		} catch (NumberFormatException excecaoFormatoErrado) {
			System.err.println("Número inteiro inválido para o parâmetro. Erro: "+excecaoFormatoErrado.toString());

			try {
				String xmlErro = Conversores.converterDocumentEmXMLString(CursoView.criarErroXML(excecaoFormatoErrado));
				response.getWriter().print(xmlErro);
			} catch (IOException e) {
				System.err.println("Não foi possível retornar XML à página. Erro: "+e.toString());
			} catch (ParserConfigurationException e) {
				System.err.println("Não foi possível criar XML de erro. Erro: "+e.toString());
			}

		} catch (SQLException excecaoSQL) {
			System.err.println("Busca SQL inválida. Erro: "+excecaoSQL.toString());

			try {
				String xmlErro = Conversores.converterDocumentEmXMLString(CursoView.criarErroXML(excecaoSQL));
				response.getWriter().print(xmlErro);
			} catch (IOException e) {
				System.err.println("Não foi possível retornar XML à página. Erro: "+e.toString());
			} catch (ParserConfigurationException e) {
				System.err.println("Não foi possível criar XML de erro. Erro: "+e.toString());
			}

		} catch (IOException e) {
			System.err.println("Não foi possível mostrar o resultado, erro ao pegar Writer. Erro: "+e.toString());

			try {
				String xmlErro = Conversores.converterDocumentEmXMLString(CursoView.criarErroXML(e));
				response.getWriter().print(xmlErro);
			} catch (IOException ex) {
				System.err.println("Não foi possível retornar XML à página. Erro: "+ex.toString());
			} catch (ParserConfigurationException ex) {
				System.err.println("Não foi possível criar XML de erro. Erro: "+ex.toString());
			}

		} catch (TransformerException | ParserConfigurationException e) {
			System.err.println("Não foi possível criar documento ou XML string com o resultado. Erro: "+e.toString());

			try {
				String xmlErro = Conversores.converterDocumentEmXMLString(CursoView.criarErroXML(e));
				response.getWriter().print(xmlErro);
			} catch (IOException ex) {
				System.err.println("Não foi possível retornar XML à página. Erro: "+ex.toString());
			} catch (ParserConfigurationException ex) {
				System.err.println("Não foi possível criar XML de erro. Erro: "+ex.toString());
			}

		}

		try	{
			conexao.close();
		} catch(SQLException erro) {
			System.err.println("Erro ao fechar banco de dados. Erro: "+erro.toString());
		}
    }
}
