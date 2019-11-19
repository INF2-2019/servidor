package diario.relatorios.relatorio9;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ConnectionFactory;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

@WebServlet(name = "Relatorio9", urlPatterns = {"/diario/relatorios/relatorio9"})
public class Rel9 extends HttpServlet {

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
			out.println("<erro><mensagem>Erro no banco de dados</mensagem></erro>");
		} catch (Exception e) {
			response.setStatus(500);
			out.println("<erro><mensagem>Erro severo</mensagem></erro>");
		}
	}


	public static int[] procuraCarga(Connection con, int idDis[]) throws SQLException {
		int[] cargas;
		cargas = new int[idDis.length];
		PreparedStatement prst;
		ResultSet rs;
		for (int i = 0; i < idDis.length; i++) {
			prst = con.prepareStatement("SELECT * FROM `disciplinas` WHERE `id` = ?");
			prst.setInt(1, idDis[i]);
			rs = prst.executeQuery();
			rs.next();
			cargas[i] = rs.getInt("carga-horaria-min");
		}
		return cargas;
	}

	public static String procuraCursos(Connection con, int idDis) throws SQLException {
		PreparedStatement prst = con.prepareStatement("SELECT * FROM `disciplinas` WHERE `id` = ?");
		prst.setInt(1, idDis);
		ResultSet rs = prst.executeQuery();
		rs.next();
		int idT = rs.getInt("id-turmas");
		prst = con.prepareStatement("SELECT * FROM `turmas` WHERE `id` = ?");
		prst.setInt(1, idT);
		rs = prst.executeQuery();
		rs.next();
		int idC = rs.getInt("id-cursos");
		prst = con.prepareStatement("SELECT * FROM `cursos` WHERE `id` = ?");
		prst.setInt(1, idC);
		rs = prst.executeQuery();
		rs.next();
		String curso = rs.getString("nome");
		return curso;
	}
	
	public static String[] procuraDisciplinas(Connection con, int idDis[]) throws SQLException {
		String nomeDis[] = new String [idDis.length];
		PreparedStatement prst;
		ResultSet rs;
		for (int i = 0; i < idDis.length; i++) {
			prst = con.prepareStatement("SELECT * FROM `disciplinas` WHERE `id` = ?");
			prst.setInt(1, idDis[i]);
			rs = prst.executeQuery();
			rs.next();
			nomeDis[i] = rs.getString("nome");
		}
		return nomeDis;
	}
	
	public static int[] procuraIdDisciplinas(Connection con, int idProf) throws SQLException {
		PreparedStatement prst = con.prepareStatement("SELECT * FROM `prof_disciplinas` WHERE `id-professores` = ?");
		prst.setInt(1, idProf);
		ResultSet rs = prst.executeQuery();
		prst = con.prepareStatement("SELECT * FROM `prof_disciplinas` WHERE `id-professores` = ?");
		prst.setInt(1, idProf);
		ResultSet cp = prst.executeQuery();
		int[] ids;
		int cont = 0;
		while (rs.next()) {
			cont++;
		}
		ids = new int[cont];
		cont = 0;
		while (cp.next()) {
			ids[cont] = cp.getInt("id-disciplinas");
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
			int idDis[];
			idDis = procuraIdDisciplinas(con, id);
			String nomeCursos[] = new String[idDis.length];
			int cargas[];
			String nomeDis[];
			String cursoDis[][] = new String [idDis.length][3];
			nomeDis = procuraDisciplinas(con, idDis);
			cargas = procuraCarga(con, idDis);
			for (int i = 0; i < idDis.length; i++) {
				nomeCursos[i] = procuraCursos(con, idDis[i]);
				cursoDis[i][0] = nomeCursos[i];
				cursoDis[i][1] = nomeDis[i];
				cursoDis[i][2] = Integer.toString(cargas[i]);
			}
			sortStrings(cursoDis, idDis.length);
			String xml = "";
			xml += XmlProf.xmlProf(id, rs.getString("nome"));
			int j = 0;
			for (int i = 0; i < idDis.length;i++, j++) {
			if (i != 0) {	
				if (!cursoDis[i][0].equals(cursoDis[i-1][0])) {
					xml += "</disciplinas>";
					xml += "</curso>";
					xml = XmlProf.xmlCurso(xml, cursoDis[i][0]);
				}
			} else {
				xml = XmlProf.xmlCurso(xml, cursoDis[i][0]);
			}
				System.out.println(xml);
				xml = XmlProf.xmlFinal(xml, cursoDis[i][1], cursoDis[i][2]);
			}
			xml += "</disciplinas>";
			xml += "</curso>";
			xml += "</cursos>";
			xml += "</professor>";
			System.out.println(xml);
			con.close();
			return xml;
		} else {
			throw new SQLException();
		}
	}
	public static void sortStrings(String[][] arr, int n)  
    { 
        String temp; 
		String temp2;
		String temp3;
        // Sorting strings using bubble sort 
        for (int j = 0; j < n - 1; j++) 
        { 
            for (int i = j + 1; i < n; i++)  
            { 
                if (arr[j][0].compareTo(arr[i][0]) > 0) 
                { 
                    temp = arr[j][0];
					temp2 = arr[j][1];
					temp3 = arr[j][2];
                    arr[j][0] = arr[i][0];
					arr[j][1] = arr[i][1];
					arr[j][2] = arr[i][2];
                    arr[i][0] = temp;
					arr[i][1] = temp2; 
					arr[i][2] = temp3; 
                } 
            } 
        } 
    }
	

}


