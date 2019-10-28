package biblioteca.acervo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;
import utils.Headers;

/**
 *
 * @author Jonata Novais Cirqueira
 * @author Nikolas Victor Mota
 */
public class DeletarAcervo extends HttpServlet {

    @Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws IOException {

		resposta.addHeader("Access-Control-Allow-Origin", "*");
		resposta.addHeader("Content-Type", "application/xml; charset=utf-8");

		PrintWriter saida = resposta.getWriter();
		try (Connection conexao = ConnectionFactory.getDiario()) {

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			String id = requisicao.getParameter("id");
			if (id == null) {
				throw new ExcecaoParametrosIncorretos("O parâmetro 'id' é obrigatório para deletar registros");
			}
                        
                        if(haEmprestimos(id, conexao)) {
                            throw new ExcecaoParametrosIncorretos("Este registro não pode ser deletado porque está emprestado");
                        }
                        ResultSet alunos = alunosQueReservaram(id, conexao);
                        while(alunos.next()) {
                            EmailHelper.enviarEmail(alunos.getString("email"), alunos.getString("nome"));
                        }

			PreparedStatement ps = conexao.prepareStatement("DELETE FROM `acervo` WHERE `id` = ?");
			ps.setInt(1, Integer.parseInt(id));
			ps.execute();
			ps.close();
			conexao.close();

			saida.println("<sucesso>");
			saida.println("  <mensagem>Registro deletado com sucesso</mensagem>");
			saida.println("</sucesso>");

		} catch (Exception e) {

			saida.println("<erro>");
			saida.println("  <mensagem>" + e.getMessage() + "</mensagem>");
			saida.println("</erro>");

		}

	}
        
        private boolean haEmprestimos(String id, Connection con)
                throws SQLException, NumberFormatException {
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM `emprestimos` WHERE `id-alunos` = ?");
            ps.setInt(1, Integer.parseInt(id));
            ResultSet resultado = ps.executeQuery();
            ps.close();
            return resultado.first();
            
        }
        
        private ResultSet alunosQueReservaram(String id, Connection con) 
                throws SQLException, NumberFormatException {
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM `reservas` WHERE `id-acervo` = ?");
            ps.setInt(1, Integer.parseInt(id));
            ResultSet resultado = ps.executeQuery();
            ps.close();
            return resultado;
            
        }
    
}
