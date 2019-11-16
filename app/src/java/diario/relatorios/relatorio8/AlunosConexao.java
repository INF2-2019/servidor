package diario.relatorios.relatorio8;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;
import utils.autenticador.*;

@WebServlet(name = "Relatorio8", urlPatterns = {"/diario/relatorios/relatorio8"})
public class AlunosConexao extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		DiarioAutenticador aut = new DiarioAutenticador(request, response);
		try {
			if (aut.cargoLogado() == DiarioCargos.ALUNO) {
				out.println(consulta((long) aut.idLogado()));
			} else {
				response.setStatus(403);
				out.println("<erro><mensagem>Você não tem permissão para fazer isso</mensagem></erro>");
			}
		} catch (SQLException ex) {
			response.setStatus(500);
			out.println("<erro><mensagem>Falha ao consultar banco de dados</mensagem></erro>");
		} catch (Exception e) {
			response.setStatus(500);
			out.println("<erro><mensagem>Erro severo</mensagem></erro>");
		}
	}

	public static double[] pegarNota(Connection con, int mat) throws SQLException {
		PreparedStatement prst = con.prepareStatement("SELECT * FROM `diario` WHERE `id-matriculas` = ?");
		prst.setInt(1, mat);
		int cont = 0;
		double notas[];
		ResultSet rs = prst.executeQuery();
		prst = con.prepareStatement("SELECT * FROM `diario` WHERE `id-matriculas` = ?");
		prst.setInt(1, mat);
		ResultSet cp = prst.executeQuery();
		while (rs.next()) {
			cont++;
		}
		notas = new double[cont];
		cont = 0;
		while (cp.next()) {
			notas[cont] = cp.getBigDecimal("nota").doubleValue();
			cont++;
		}
		return notas;
	}

	public static int[] procuraMatricula(Connection con, Long id) throws SQLException {
		PreparedStatement prst = con.prepareStatement("SELECT * FROM `matriculas` WHERE `id-alunos` = ?");
		prst.setLong(1, id);
		int cont = 0;
		int matriculas[];
		ResultSet rs = prst.executeQuery();
		prst = con.prepareStatement("SELECT * FROM `matriculas` WHERE `id-alunos` = ?");
		prst.setLong(1, id);
		ResultSet cp = prst.executeQuery();
		while (rs.next()) {
			cont++;
		}
		matriculas = new int[cont];
		cont = 0;
		while (cp.next()) {
			matriculas[cont] = cp.getInt("id");
			cont++;
		}
		return matriculas;
	}

	public static String consulta(Long id) throws SQLException {
		Connection con = ConnectionFactory.getDiario();
		if (con != null) {
			int mat[];
			double notas[][];
			double grade[];
			mat = procuraMatricula(con, id);
			if (mat.length != 0) {
				for (int i = 0; i < mat.length; i++) {
					grade = pegarNota(con, mat[i]);
					if (grade.length != 0) {
						for (int c = 0; c < grade.length; c++) {
							notas = new double[mat.length][grade.length];
							notas[i][c] = grade[c];
							if (notas[i][c] < 60) {
								return "<erro><mensagem>Aluno não foi aprovado</mensagem></erro>";
							}
						}
					} else {
						return "<erro><mensagem>O aluno ainda não teve sua nota registrada em alguma das disciplinas</mensagem></erro>";
					}
				}
			} else {
				return "<erro><mensagem>Aluno não está matriculado em nada</mensagem></erro>";
			}
			PreparedStatement prst = con.prepareStatement("SELECT * FROM `alunos` WHERE `id` = ?");
			prst.setLong(1, id);
			ResultSet rs = prst.executeQuery();
			rs.next();
			String nome = rs.getString("nome");
			con.close();
			return "<sucesso><mensagem>" + nome + "</mensagem></sucesso>";
		} else {
			throw new SQLException();
		}
	}

}
