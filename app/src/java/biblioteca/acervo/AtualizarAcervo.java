
package biblioteca.acervo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;
import utils.Hasher;
/**
 *
 * @author Indra Matsiendra
 * @author Amanda
 * @author Nikolas
 * @author Jonata
 */
@WebServlet(name = "atualizar", urlPatterns = {"/biblioteca/acervo/atualizar"})
public class AtualizarAcervo extends HttpServlet {

        private static final String[] paramsAcervo = {"id", "id-campi", "nome", "tipo", "local", "ano", "editora", "paginas"};
        private static final String[] paramsLivros = {"idObra", "edicao", "isbn"};
        private static final String[] paramsAcademicos = {"idObra", "programa"};
        private static final String[] paramsMidias = {"idObra", "tempo", "subtipo"};
        private static final String[] paramsPeriodicos = {"idObra", "periodicidade", "mes", "volume", "subtipo", "issn"};
	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws IOException {

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.setContentType("text/xml;charset=UTF-8");

		PrintWriter saida = resposta.getWriter();

		try (Connection conexao = ConnectionFactory.getDiario()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}
                    System.out.println("gt7gu");
			validarParametros(requisicao.getParameterMap(),requisicao.getParameter("tipo"));
                        System.out.println("re");
                        /*verificando se o id já existe no acervo paa ser alterado*/
                        validarID(requisicao.getParameter("id"),conexao);
                        System.out.println("YYY");
                        
                        /*MODIFICAÇÃO DO ACERVO:*/
			String statement = "UPDATE `acervo` SET "
					+ "id = ? , id-campi = ? ,nome = ? , tipo = ? , local = ?, "
					+ "ano = ? , editora = ? , paginas = ? WHERE id = ?";
			PreparedStatement ps = conexao.prepareStatement(statement);
System.out.println("TESTE2");
			ps.setInt(1, Integer.parseInt(requisicao.getParameter("id")));
			ps.setString(2, requisicao.getParameter("id-campi"));
			ps.setString(3,requisicao.getParameter("nome"));
			ps.setString(4, requisicao.getParameter("tipo"));
			ps.setString(5, requisicao.getParameter("local"));
			ps.setInt(6, Integer.parseInt(requisicao.getParameter("ano")));
			ps.setString(7, requisicao.getParameter("editora"));
			ps.setString(8, requisicao.getParameter("paginas"));
                        ps.setInt(9, Integer.parseInt(requisicao.getParameter("id")));
			ps.close();
			ps.execute();
                        
                        /*MODIFICAÇÃO DA TABELAS DA OBRA:*/
                        statement = "UPDATE `"+requisicao.getParameter("tipo")+"` SET ";System.out.println("TESTE1");
                        switch(requisicao.getParameter("tipo"))
                        {
                            case "academicos":
                                    statement+="id-obra ='"+requisicao.getParameter("idObra")+"' , id-acervo='"+requisicao.getParameter("id")+"' , programa='"
                                            +requisicao.getParameter("programa")+"' WHERE id='"+requisicao.getParameter("id")+"'";
                                    conexao.createStatement().executeUpdate(statement);System.out.println("TESTE3");
                                break;
                            case "livros":
                                    statement+="id-obra ='"+requisicao.getParameter("idObra")+"' , id-acervo='"+requisicao.getParameter("id")+"' , edicao='"
                                            +requisicao.getParameter("edicao")+"' , isbn='"+requisicao.getParameter("isbn")+"' WHERE id='"+requisicao.getParameter("id")+"'";
                                    conexao.createStatement().executeUpdate(statement);
                                break;
                            case "periodicos":
                                    statement+="id-obra ='"+requisicao.getParameter("idObra")+"' , id-acervo='"+requisicao.getParameter("id")+"' , periodicidade='"
                                            +requisicao.getParameter("periodicidade")+"' , mes='"+requisicao.getParameter("mes")+"', volume='"+requisicao.getParameter("volume")
                                            +"' , subtipo='"+requisicao.getParameter("subtipo")+"' , issn='"+requisicao.getParameter("issn")
                                            +"' WHERE id='"+requisicao.getParameter("id")+"'";
                                    conexao.createStatement().executeUpdate(statement);
                                break;
                            case "midias":
                                    statement+="id-obra ='"+requisicao.getParameter("idObra")+"' , id-acervo='"+requisicao.getParameter("id")+"' , tempo='"
                                            +requisicao.getParameter("tempo")+"', subtipo='"+requisicao.getParameter("subtipo")+"' WHERE id='"+requisicao.getParameter("id")+"'";
                                    conexao.createStatement().executeUpdate(statement);
                                break;
			
                        }System.out.println("TESTE4");
			conexao.close();

			saida.println("<sucesso>");
			saida.println("  <mensagem>Registro alterado com sucesso</mensagem>");
			saida.println("</sucesso>");

		} catch (Exception e) {

			saida.println("<erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</erro>");

		}

	}

	private void validarParametros(Map<String, String[]> parametros,String tipo) throws ExcecaoParametrosIncorretos {
		/*quando a pessoa alterar um dado do acervo terá também a opcao de alterar da tabela dependente 
                * do tipo da obra, entao os primeiros parametros passados sao relativos ao acervo e os seguintes
                *relativos ao tipo*/
            if (parametros.size() < paramsAcervo.length) {
		throw new ExcecaoParametrosIncorretos("Parâmetros insuficientes");
            }
            for (Map.Entry<String, String[]> iterador : parametros.entrySet()) {
		boolean valido = false;
		for (String param : paramsAcervo) {
                    String chave=iterador.getKey();        
			if (chave.equals(param)) {
                            valido = true;
                            /*if("tipo".equals(chave))
                            {
                                tipo=iterador.getValue();
                            }*/
			}
                        if (!valido) {
                            throw new ExcecaoParametrosIncorretos("Parâmetro desconhecido: " + iterador.getKey());
                        }
		}
                switch(tipo)
                {
                    case "academicos":
                        for (String param : paramsAcademicos) {
                        valido = false;
			if (iterador.getKey().equals(param)) {
                            valido = true;
			}
                        if (!valido) {
                            throw new ExcecaoParametrosIncorretos("Parâmetro desconhecido: " + iterador.getKey());
                        }
                        }
                        break;
                    case "livros":
                        for (String param : paramsLivros) { 
                            valido = false;System.out.println("aaaaaaa");
                            if (iterador.getKey().equals(param)) {System.out.println("d");
                                valido = true;
                            }
                            if (!valido) {System.out.println("ee");
                                throw new ExcecaoParametrosIncorretos("Parâmetro desconhecido: " + iterador.getKey());
                            }System.out.println("aaaaaaa");
                        }
                        break;
                    case "periodicos":
                        for (String param : paramsPeriodicos) { 
                        valido = false;
			if (iterador.getKey().equals(param)) {
                            valido = true;
			}
                        if (!valido) {
                            throw new ExcecaoParametrosIncorretos("Parâmetro desconhecido: " + iterador.getKey());
                        }
                        }
                        break;
                    case "midias":
                        for (String param : paramsMidias) { 
                        valido = false;
			if (iterador.getKey().equals(param)) {
                            valido = true;
			}
                        if (!valido) {
                            throw new ExcecaoParametrosIncorretos("Parâmetro desconhecido: " + iterador.getKey());
                        }
                        }
                        break;
                }
            }
        }
        public static void validarID(String id, Connection con)
			throws SQLException, ExcecaoParametrosIncorretos {
            
                        PreparedStatement ps = con.prepareStatement("SELECT * FROM `acervo` WHERE id= ? ");
                        ps.setString(1, id);
                        ps.execute();
                        ResultSet resultado = ps.getResultSet();
                        if (!resultado.first()) 
                        {
                            throw new ExcecaoParametrosIncorretos("ID não encontrado");
                        }
	}

	@Override
	public String getServletInfo() {
		return "Servlet dedicado a atualizar registros do acervo e outras tabelas da biblioteca";
	}

}
