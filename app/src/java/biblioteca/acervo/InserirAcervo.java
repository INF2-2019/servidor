/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.acervo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;

@WebServlet(name="inserir", urlPatterns = {"/biblioteca/acervo/inserir"})
/**
 *<h1> Servlet de Inserção de Obras</h1>
 * Servlet para a requisição de inclusão de registro nas tabelas 'acervo','livros','midias','periodicos' e 'academicos'
 * @author Indra Matsiendra
 */
public class InserirAcervo extends HttpServlet {
  
    private static final String[] paramsAcervo = {"id", "id-campi", "nome", "tipo", "local", "ano", "editora", "paginas"};
    private static final String[] paramsLivros = {"idObra", "edicao", "isbn"};
    private static final String[] paramsAcademicos = {"idObra", "programa"};
    private static final String[] paramsMidias = {"idObra", "tempo", "subtipo"};
    private static final String[] paramsPeriodicos = {"idObra", "periodicidade", "mes", "volume", "subtipo", "issn"};
    /*id-acervo nas 4 outras tabelas equivale ao 'id' em paramsAcervo*/
    
    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        
        // Validação dos parâmetros:
        
        for(String param : paramsAcervo) {
            if (requisicao.getParameter(param)== null) {
		resposta.setStatus(400);
                return;
            }
	}
        switch(requisicao.getParameter("tipo"))
        {
            case "academicos":
                for (String param : paramsAcademicos) {
                    if (requisicao.getParameter(param) == null) {
                        resposta.setStatus(400);
                        return;
                    }
                }
                break;
            case "livros":
                for (String param : paramsLivros) {
                    if (requisicao.getParameter(param) == null) {
                        resposta.setStatus(400);
                        return;
                    }
                }
                break;
            case "midias":
                for (String param : paramsMidias) {
                    if (requisicao.getParameter(param) == null) {
                        resposta.setStatus(400);
                        return;
                    }
                }
                break;
            case "periodicos":
                for (String param : paramsPeriodicos) {
                    if (requisicao.getParameter(param) == null) {
                        resposta.setStatus(400);
                        return;
                    }
                }
                break;
        }
        
	resposta.addHeader("Access-Control-Allow-Origin", "*");
	resposta.setContentType("text/xml;charset=UTF-8");
        
        
        PrintWriter saida = resposta.getWriter();
		saida.println("<root>");
		try (Connection conexao = ConnectionFactory.getBiblioteca()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}
                        
                        String id = requisicao.getParameter(paramsAcervo[0]),
                                idCampi = requisicao.getParameter(paramsAcervo[1]),
				nome = requisicao.getParameter(paramsAcervo[2]),
				tipo = requisicao.getParameter(paramsAcervo[3]),
				local = requisicao.getParameter(paramsAcervo[4]),
				ano = requisicao.getParameter(paramsAcervo[5]),
                                editora = requisicao.getParameter(paramsAcervo[6]),
                                paginas = requisicao.getParameter(paramsAcervo[7]);

			String clausulaSql = "INSERT INTO `acervo` VALUES ('"
					+ id +"', '"+ idCampi +"', '"+ nome +"', '"+ tipo +"', '"+ local+
					"', '"+ano+"', '"+ editora + "', '" + paginas + "')";
			conexao.createStatement().executeUpdate(clausulaSql);
                         
                        String idObra,subtipo;
                        switch(tipo)
                        {
                            case "academicos":
				idObra = requisicao.getParameter(paramsAcademicos[0]);
                                String  programa = requisicao.getParameter(paramsAcademicos[1]);
                                clausulaSql = "INSERT INTO `academicos` VALUES ('"
					+ idObra +"', '"+ id +"', '"+ programa +"')";
                                conexao.createStatement().executeUpdate(clausulaSql);
                                break;
                            case "livros":
				idObra = requisicao.getParameter(paramsLivros[0]);
                                String  edicao = requisicao.getParameter(paramsLivros[1]),
                                        isbn = requisicao.getParameter(paramsLivros[2]);
                                clausulaSql = "INSERT INTO `livros` VALUES ('"
					+ idObra +"', '"+ id +"', '"+ edicao +"', '"+ isbn + "')";
                                conexao.createStatement().executeUpdate(clausulaSql);
                                break;
                            case "midias":
				idObra = requisicao.getParameter(paramsMidias[0]);
                                String  tempo = requisicao.getParameter(paramsMidias[1]);
                                subtipo = requisicao.getParameter(paramsMidias[2]);
                                clausulaSql = "INSERT INTO `midias` VALUES ('"
					+ idObra +"', '"+ id +"', '"+ tempo +"', '"+ subtipo + "')";
                                conexao.createStatement().executeUpdate(clausulaSql);
                                break;
                            case "periodicos":
				idObra = requisicao.getParameter(paramsPeriodicos[0]);
                                String  periodicidade = requisicao.getParameter(paramsPeriodicos[1]),
                                        mes = requisicao.getParameter(paramsPeriodicos[2]),
                                        volume = requisicao.getParameter(paramsPeriodicos[3]),
                                        issn = requisicao.getParameter(paramsPeriodicos[5]);
                                subtipo=requisicao.getParameter(paramsPeriodicos[4]);
                                clausulaSql = "INSERT INTO `periodicos` VALUES ('"
					+ idObra +"', '"+ id +"', '"+ periodicidade +"', '"+ mes + "', '"+ volume +"', '"+
                                        subtipo+"', '"+issn+"')";
                                conexao.createStatement().executeUpdate(clausulaSql);
                                break;
                        }
                        
			conexao.close();

			saida.println("<info>");
			saida.println("  <erro>false</erro>");
			saida.println("	 <mensagem>Registro inserido com sucesso</mensagem>");
			saida.println("</info>");
                        
                }catch(Exception e) {
			saida.println("<info>");
			saida.println("  <erro>true</erro>");
			saida.println("  <mensagem>" + e.getLocalizedMessage() + "</mensagem>");
			saida.println("</info>");
		} finally {
			saida.println("</root>");
		}
    }
    @Override
    public String getServletInfo() {
        return "Servlet dedicado à inserção de registro de obras no BD";
    }

}
