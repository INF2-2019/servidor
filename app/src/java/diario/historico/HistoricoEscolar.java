package diario.historico;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

import utils.ConnectionFactory;

@WebServlet(name = "HistoricoEscolar", urlPatterns = {"/diario/HistoricoEscolar"})
public class HistoricoEscolar extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		try {
			DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
			DiarioCargos usuario = autenticador.cargoLogado();
			long cpf;

			Connection conexao = ConnectionFactory.getDiario();

			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			if (usuario == DiarioCargos.CONVIDADO || usuario == DiarioCargos.PROFESSOR) {
				throw new ExcecaoSemAutorizacao("Você não tem permissão para fazer isso");
			} else if (usuario == DiarioCargos.ALUNO) {
				cpf = (long) autenticador.idLogado();
			} else {
				if (request.getParameter("id") == null) {
					throw new ExcecaoSemCpf("Não foi recebido nenhum CPF");
				} else {
					cpf = Long.parseLong(request.getParameter("id"));
				}
			}

			ResultSet matriculas, aluno;

			PreparedStatement ps0 = conexao.prepareStatement("SELECT `nome` FROM `alunos` WHERE `id` = ?");
			ps0.setLong(1, cpf);
			aluno = ps0.executeQuery();
			aluno.next();
			String nomeDoAluno = aluno.getString("nome");

			PreparedStatement ps1 = conexao.prepareStatement("SELECT * FROM `matriculas` WHERE `id-alunos` = ?");
			ps1.setLong(1, cpf);
			matriculas = ps1.executeQuery();

			// adiciona zeros no início do CPF
			String idStr = Long.toString(cpf);
			String cpfPrint = "";

			for (int i = idStr.length(); i < 11; i++) {
				cpfPrint += "0";
			}
			cpfPrint += idStr;

			// começa o XML de resposta
			out.println("<historico>");
			out.println(" <cpf>" + cpfPrint + "</cpf>");
			out.println(" <nome>" + nomeDoAluno + "</nome>");
			out.println("  <disciplinas>");

			while (matriculas.next()) {
				int idMatricula = matriculas.getInt("id");
				ResultSet diario;

				PreparedStatement ps2 = conexao.prepareStatement("SELECT * FROM `diario` WHERE `id-matriculas` = ?");
				ps2.setInt(1, idMatricula);
				diario = ps2.executeQuery();

				while (diario.next()) {
					int idConteudo = diario.getInt("id-conteudos");
					ResultSet conteudos;

					PreparedStatement ps3 = conexao.prepareStatement("SELECT * FROM `conteudos` WHERE `id` "
							+ "= ?");
					ps3.setInt(1, idConteudo);
					conteudos = ps3.executeQuery();

					while (conteudos.next()) {
						int idDisciplina = conteudos.getInt("id-disciplinas");
						ResultSet disciplinas;

						PreparedStatement ps4 = conexao.prepareStatement("SELECT * FROM `disciplinas` WHERE "
								+ "`id` = ?");
						ps4.setInt(1, idDisciplina);
						disciplinas = ps4.executeQuery();

						while (disciplinas.next()) {
							out.println("   <disciplina>");
							out.println("    <matricula>" + diario.getInt("id-matriculas") + "</matricula>");
							out.println("    <ano>" + matriculas.getInt("ano") + "</ano>");
							out.println("    <nome>" + disciplinas.getString("nome") + "</nome>");
							out.println("    <conteudo>" + conteudos.getString("conteudos") + "</conteudo>");
							out.println("    <faltas>" + diario.getInt("faltas") + "</faltas>");
							out.println("    <valor>" + conteudos.getDouble("valor") + "</valor>");
							out.println("    <nota>" + diario.getDouble("nota") + "</nota>");
							out.println("   </disciplina>");
						}
					}

				}
			}
			out.println("  </disciplinas>");
			out.println("</historico>");

			conexao.close();

		} catch (SQLException e) {
			response.setStatus(500);
			out.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		} catch (ExcecaoSemCpf e) {
			response.setStatus(400);
			out.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		} catch (ExcecaoSemAutorizacao e) {
			response.setStatus(403);
			out.println("<erro><mensagem>" + e.getMessage() + "</mensagem></erro>");
		}
	}

}
