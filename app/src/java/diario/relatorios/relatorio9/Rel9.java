package diario.relatorios.relatorio9;

import utils.ConnectionFactory;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "Relatorio89", urlPatterns = {"/diario/relatorios/relatorio9"})
public class Rel9 extends HttpServlet {

	public static String[] procuraDisciplinas(Connection con, int idTurma) throws SQLException {
		PreparedStatement prst = con.prepareStatement("SELECT * FROM `disciplinas` WHERE `id-turmas` = ?");
		prst.setInt(1, idTurma);
		ResultSet rs = prst.executeQuery();
		prst = con.prepareStatement("SELECT * FROM `disciplinas` WHERE `id-turmas` = ?");
		prst.setInt(1, idTurma);
		ResultSet cp = prst.executeQuery();
		String[] nomesDis;
		int cont = 0;
		while (rs.next()) {
			cont++;
		}
		nomesDis = new String[cont];
		cont = 0;
		while (cp.next()) {
			nomesDis[cont] = cp.getString("nome");
			cont++;
		}
		return nomesDis;

	}

	public static int[] procuraCarga(Connection con, int idTurma) throws SQLException {
		PreparedStatement prst = con.prepareStatement("SELECT * FROM `disciplinas` WHERE `id-turmas` = ?");
		prst.setInt(1, idTurma);
		ResultSet rs = prst.executeQuery();
		prst = con.prepareStatement("SELECT * FROM `disciplinas` WHERE `id-turmas` = ?");
		prst.setInt(1, idTurma);
		ResultSet cp = prst.executeQuery();
		int cont = 0;
		int[] cargas;
		while (rs.next()) {
			cont++;
		}
		cargas = new int[cont];
		cont = 0;
		while (cp.next()) {
			cargas[cont] = cp.getInt("carga-horaria-min");
			cont++;
		}
		return cargas;
	}

	public static int[] procuraIdTurma(Connection con, int idCurso) throws SQLException {
		PreparedStatement prst = con.prepareStatement("SELECT * FROM `turmas` WHERE `id-cursos` = ?");
		prst.setInt(1, idCurso);
		ResultSet rs = prst.executeQuery();
		prst = con.prepareStatement("SELECT * FROM `turmas` WHERE `id-cursos` = ?");
		prst.setInt(1, idCurso);
		ResultSet cp = prst.executeQuery();
		int[] ids;
		int cont = 0;
		while (rs.next()) {
			cont++;
		}
		ids = new int[cont];
		cont = 0;
		while (cp.next()) {
			ids[cont] = cp.getInt("id");
			cont++;
		}
		return ids;
	}

	public static String[] procuraCursos(Connection con, int idDepto) throws SQLException {
		PreparedStatement prst = con.prepareStatement("SELECT * FROM `cursos` WHERE `id-depto` = ?");
		prst.setInt(1, idDepto);
		ResultSet rs = prst.executeQuery();
		prst = con.prepareStatement("SELECT * FROM `cursos` WHERE `id-depto` = ?");
		prst.setInt(1, idDepto);
		ResultSet cp = prst.executeQuery();
		String[] nomesCursos;
		int cont = 0;
		while (rs.next()) {
			cont++;
		}
		nomesCursos = new String[cont];
		cont = 0;
		while (cp.next()) {
			nomesCursos[cont] = cp.getString("nome");
			cont++;
		}
		return nomesCursos;
	}

	public static int[] procuraIdCurso(Connection con, int idDepto) throws SQLException {
		PreparedStatement prst = con.prepareStatement("SELECT * FROM `cursos` WHERE `id-depto` = ?");
		prst.setInt(1, idDepto);
		ResultSet rs = prst.executeQuery();
		prst = con.prepareStatement("SELECT * FROM `cursos` WHERE `id-depto` = ?");
		prst.setInt(1, idDepto);
		ResultSet cp = prst.executeQuery();
		int[] ids;
		int cont = 0;
		while (rs.next()) {
			cont++;
		}
		ids = new int[cont];
		cont = 0;
		while (cp.next()) {
			ids[cont] = cp.getInt("id");
			cont++;
		}
		return ids;
	}

	public static String consulta(int id) throws SQLException {
		Connection con = ConnectionFactory.getDiario();
		if (con != null) {
			PreparedStatement prst = con.prepareStatement("SELECT * FROM `professores` WHERE `id` = ?");
			prst.setInt(1, id);
			ResultSet rs = prst.executeQuery();
			rs.next();
			int[] idCursos;
			String[] nomeCursos;
			int[][] idTurmas;
			int[] cargas;
			String[] nomeDis;
			String xml = "";
			idCursos = procuraIdCurso(con, rs.getInt("id-depto"));
			nomeCursos = procuraCursos(con, rs.getInt("id-depto"));
			xml += XmlProf.xmlProf(id, rs.getString("nome"));
			if (idCursos.length != 0) {
				for (int i = 0; i < idCursos.length; i++) {
					xml = XmlProf.xmlCurso(xml, nomeCursos[i]);
					int[] idT;
					idT = procuraIdTurma(con, idCursos[i]);
					if (idT.length != 0) {
						for (int c = 0; c < idT.length; c++) {
							idTurmas = new int[idCursos.length][idT.length];
							idTurmas[i][c] = idT[c];
							nomeDis = procuraDisciplinas(con, idTurmas[i][c]);
							cargas = procuraCarga(con, idTurmas[i][c]);
							if (nomeDis.length != 0) {
								xml = XmlProf.xmlFinal(xml, nomeDis, cargas);
							} else {
								return "<erro><mensagem>Professor tem alguma turma sem disciplinas vinculadas a mesma</mensagem></erro>";
							}
						}
					} else {
						return "<erro><mensagem>Professor tem algum curso sem turmas vinculadas ao mesmo</mensagem></erro>";
					}
					xml += "</disciplinas>";
					xml += "</curso>";
				}
			} else {
				return "<erro><mensagem>Professor não leciona nenhum curso</mensagem></erro>";
			}
			xml += "</cursos>";
			xml += "</professor>";
			con.close();
			return xml;
		} else {
			throw new SQLException();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		DiarioAutenticador aut = new DiarioAutenticador(request, response);
		try {
			if (aut.cargoLogado() == DiarioCargos.PROFESSOR) {
				out.println(consulta((int) aut.idLogado()));
			} else if (aut.cargoLogado() == DiarioCargos.ADMIN) {
				out.println(consulta(Integer.parseInt(request.getParameter("id"))));
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

}
