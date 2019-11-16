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

	Connection conexao = ConnectionFactory.getDiario();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		try {
			DiarioAutenticador autenticador = new DiarioAutenticador(request, response);
			DiarioCargos usuario = autenticador.cargoLogado();

			/*if (usuario == DiarioCargos.CONVIDADO) {
				response.setStatus(403);
				return;
			}*/
			if (conexao == null) {
				throw new SQLException("Impossível se conectar ao banco de dados");
			}

			if (request.getParameter("id") == null) {
				throw new Exception("Não foi recebido nenhum CPF");
			} else {
				long cpf = Long.parseLong(request.getParameter("id"));
				ResultSet matriculas, aluno;

				PreparedStatement ps0 = conexao.prepareStatement("SELECT `nome` FROM `alunos` WHERE `id` = ?");
				ps0.setLong(1, cpf);
				aluno = ps0.executeQuery();
				aluno.next();
				String nomeDoAluno = aluno.getString("nome");

				PreparedStatement ps1 = conexao.prepareStatement("SELECT `id` FROM `matriculas` WHERE `id-alunos` = ?");
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
								out.println("  <item>");
								out.println("   <matricula>" + diario.getInt("id-matriculas") + "</matricula>");
								out.println("   <conteudo>" + conteudos.getString("conteudos") + "</conteudo>");
								out.println("   <disciplina>" + disciplinas.getString("nome") + "</disciplina>");
								out.println("   <faltas>" + diario.getInt("faltas") + "</faltas>");
								out.println("   <nota>" + diario.getDouble("nota") + "</nota>");
								out.println("  </item>");
							}
						}
					}
				}

				out.println("</historico>");
			}

		} catch (Exception e) {

		}
	}

}
