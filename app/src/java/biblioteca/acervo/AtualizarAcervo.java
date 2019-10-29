
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
 *Servlet dedicado a atualizar registros do acervo e outras tabelas da biblioteca
 * 
 * @author Indra Matsiendra
 * @author Jonata Novais
 */
@WebServlet(name = "atualizar", urlPatterns = {"/biblioteca/acervo/atualizar"})
public class AtualizarAcervo extends HttpServlet {

        private static final String[] paramsAcervo = {"id", "id-campi", "nome", "tipo", "local", "ano", "editora", "paginas"};
        private static final String[] paramsLivros = {"id-obra", "edicao", "isbn"};
        private static final String[] paramsAcademicos = {"id-obra", "programa"};
        private static final String[] paramsMidias = {"id-obra", "tempo", "subtipo"};
        private static final String[] paramsPeriodicos = {"id-obra", "periodicidade", "mes", "volume", "subtipo", "issn"};
	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws IOException {

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.setContentType("text/xml;charset=UTF-8");

		PrintWriter saida = resposta.getWriter();

		try (Connection conexao = ConnectionFactory.getBiblioteca()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}
			validarParametros(requisicao.getParameterMap(),requisicao.getParameter("tipo"));
                        /*verificando se o id já existe no acervo paa ser alterado*/
                        validarID(requisicao.getParameter("id"),conexao);
                        
                        /*MODIFICAÇÃO DO ACERVO:*/
			String statement = "UPDATE `acervo` SET "
					+ "`id` = ? , `id-campi` = ? ,`nome` = ? , `tipo` = ? , `local` = ?, "
					+ "`ano` = ? , `editora` = ? , `paginas` = ? WHERE `id` = ?";
			PreparedStatement ps = conexao.prepareStatement(statement);
			ps.setInt(1, Integer.parseInt(requisicao.getParameter("id")));
			ps.setString(2, requisicao.getParameter("id-campi"));
			ps.setString(3,requisicao.getParameter("nome"));
			ps.setString(4, requisicao.getParameter("tipo"));
			ps.setString(5, requisicao.getParameter("local"));
			ps.setInt(6, Integer.parseInt(requisicao.getParameter("ano")));
			ps.setString(7, requisicao.getParameter("editora"));
			ps.setString(8, requisicao.getParameter("paginas"));
                        ps.setInt(9, Integer.parseInt(requisicao.getParameter("id")));
			ps.execute();
			ps.close();
                        
                        /*MODIFICAÇÃO DA TABELAS DA OBRA:*/
                        switch(requisicao.getParameter("tipo"))
                        {
                            case "academicos":
                                    atualizarAcademico(Integer.parseInt(requisicao.getParameter("id")),requisicao,conexao);
                                break;
                            case "livros":
                                    atualizarLivro(Integer.parseInt(requisicao.getParameter("id")),requisicao,conexao);
                                break;
                            case "midias":
                                    atualizarMidia(Integer.parseInt(requisicao.getParameter("id")),requisicao,conexao);
                                break;
                            case "periodicos":
                                    atualizarPeriodico(Integer.parseInt(requisicao.getParameter("id")),requisicao,conexao);
                                break;
			
                        }
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
            * relativos ao tipo*/
            
            for(String param : paramsAcervo)
            {    
                if(!parametros.keySet().contains(param)) 
                {
                        throw new ExcecaoParametrosIncorretos("Um dos parâmetros esperados não foi passado: " + param);
                }
            }
            switch(tipo)
            {
                case "academicos":
                        for(String pa : paramsAcademicos)
                        {    
                            if(!parametros.containsKey(pa)) 
                            {
                                throw new ExcecaoParametrosIncorretos("Um dos parâmetros esperados não foi passado: " + pa);
                            }
                        }
                    break;
                case "livros":
                    for(String pl : paramsLivros)
                    {    
                        if(!parametros.containsKey(pl)) 
                        {
                            throw new ExcecaoParametrosIncorretos("Um dos parâmetros esperados não foi passado: " + pl);
                        }
                    }
                    /*verificando se a quantidade de parametros é condizente:*/
                    if (tipo.equals("academicos") && parametros.size() < paramsAcervo.length+paramsAcademicos.length) {
                        throw new ExcecaoParametrosIncorretos("Parâmetros insuficientes");
                    }
                    break;
                case "midias":
                    for(String pm : paramsMidias)
                    {    
                        if(!parametros.containsKey(pm)) 
                        {
                            throw new ExcecaoParametrosIncorretos("Um dos parâmetros esperados não foi passado: " + pm);
                        }
                    }
                    break;
                case "periodicos":
                    for(String pp : paramsPeriodicos)
                    {    
                        if(!parametros.containsKey(pp)) 
                        {
                            throw new ExcecaoParametrosIncorretos("Um dos parâmetros esperados não foi passado: " + pp);
                        }
                    }
                    break;
            }
            /*verificando se a quantidade de parametros é condizente:*/
            if (tipo.equals("academicos") && parametros.size() < paramsAcervo.length+paramsAcademicos.length) {
		throw new ExcecaoParametrosIncorretos("Parâmetros insuficientes");
            }
            else if (tipo.equals("livros") && parametros.size() < paramsAcervo.length+paramsLivros.length) {
		throw new ExcecaoParametrosIncorretos("Parâmetros insuficientes");
            }
            else if (tipo.equals("midias") && parametros.size() < paramsAcervo.length+paramsMidias.length) {
		throw new ExcecaoParametrosIncorretos("Parâmetros insuficientes");
            }
            else if (tipo.equals("periodicos") && parametros.size() < paramsAcervo.length+paramsPeriodicos.length) {
		throw new ExcecaoParametrosIncorretos("Parâmetros insuficientes");
            }
        }
        public static void validarID(String id, Connection con)
			throws SQLException, ExcecaoParametrosIncorretos {
            
                        PreparedStatement ps = con.prepareStatement("SELECT * FROM `acervo` WHERE `id`= ? ");
                        ps.setString(1, id);
                        ps.execute();
                        ResultSet resultado = ps.getResultSet();
                        if (!resultado.first()) 
                        {
                            throw new ExcecaoParametrosIncorretos("ID não encontrado");
                        }
	}
        private void atualizarAcademico(int idAcervo, HttpServletRequest requisicao, Connection conexao)
			throws SQLException, NumberFormatException {
		PreparedStatement ps = conexao.prepareStatement( "UPDATE `academicos` SET `id-obra` = ? , `id-acervo` = ? ,`programa` = ?  WHERE `id-acervo` = ?");
		ps.setInt(1, Integer.parseInt(requisicao.getParameter("id-obra")));
		ps.setInt(2, idAcervo);
		ps.setString(3, requisicao.getParameter("programa"));
		ps.setInt(4, idAcervo);
		ps.execute();
		ps.close();
	}

	private void atualizarLivro(int idAcervo, HttpServletRequest requisicao, Connection conexao)
			throws SQLException, NumberFormatException {
		PreparedStatement ps = conexao.prepareStatement("UPDATE `livros` SET `id-obra` = ? , `id-acervo` = ? ,`edicao` = ? ,`isbn` = ?  WHERE `id-acervo` = ?");
		ps.setInt(1, Integer.parseInt(requisicao.getParameter("id-obra")));
		ps.setInt(2, idAcervo);
		ps.setInt(3, Integer.parseInt(requisicao.getParameter("edicao")));
		ps.setLong(4, Long.parseLong(requisicao.getParameter("isbn")));
		ps.setInt(5, idAcervo);
		ps.execute();
		ps.close();
	}

	private void atualizarMidia(int idAcervo, HttpServletRequest requisicao, Connection conexao)
			throws SQLException, NumberFormatException {
		PreparedStatement ps = conexao.prepareStatement("UPDATE `midias` SET `id-obra` = ? , `id-acervo` = ? ,`tempo` = ? ,`subtipo` = ?  WHERE `id-acervo` = ?");
		ps.setInt(1, Integer.parseInt(requisicao.getParameter("id-obra")));
		ps.setInt(2, idAcervo);
		ps.setString(3, requisicao.getParameter("tempo")); // o formato deverá ser adaptado no front-end
		ps.setString(4, requisicao.getParameter("subtipo").toUpperCase());
		ps.setInt(5, idAcervo);
		ps.execute();
		ps.close();
	}

	private void atualizarPeriodico(int idAcervo, HttpServletRequest requisicao, Connection conexao)
			throws SQLException, NumberFormatException {
		PreparedStatement ps = conexao.prepareStatement("UPDATE `periodicos` SET "
				+ "`id-acervo`=?, `periodicidade`=?, `mes`=?, `volume`=?, `subtipo`=?, `issn`=? WHERE `id-acervo`=?");
		ps.setInt(1, idAcervo);
		ps.setString(2, requisicao.getParameter("periodicidade"));
		ps.setString(3, requisicao.getParameter("mes"));
		ps.setInt(4, Integer.parseInt(requisicao.getParameter("volume")));
		ps.setString(5, requisicao.getParameter("subtipo"));
		ps.setInt(6, Integer.parseInt(requisicao.getParameter("issn")));
		ps.setInt(7, idAcervo);
		ps.execute();
		ps.close();
	}
	@Override
	public String getServletInfo() {
		return "Servlet dedicado a atualizar registros do acervo e outras tabelas da biblioteca";
	}

}
