package diario.alunos.servlets;

import diario.campi.view.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.ConnectionFactory;
import utils.Headers;

/**
 *
 * @author User
 */
@WebServlet(name = "ConsultarAlunos", urlPatterns = {"/diario/alunos/consultar"})
public class ConsultarPorId extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String xml = "";
		Headers.XMLHeaders(response);
		String id = request.getParameter("id");

		Connection conexao = ConnectionFactory.getDiario();
		try {

			PreparedStatement ps = conexao.prepareStatement("SELECT * FROM `alunos` WHERE `id` = ?");
			int idParsed = Integer.parseUnsignedInt(id);
			ps.setInt(1, idParsed);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
			xml += viewConsulta.XMLAlunoCompleto(rs.getInt("id"), rs.getString("nome"),  rs.getString("email"), rs.getString("sexo"), rs.getDate("nascimento"), rs.getString("logradouro"), rs.getInt("numero"), rs.getString("complemento"), rs.getString("bairro"),rs.getString("cidade"), rs.getInt("cep"),rs.getString("uf"), rs.getString("foto"));
			}
			out.println(xml);
			conexao.close();
		} catch(SQLException ex) {
			out.println("<erro>Falha ao consultar alunos do banco de dados</erro>");
		}
		
    }

}
