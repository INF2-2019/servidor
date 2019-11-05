package biblioteca.emprestimos.repository;

import biblioteca.emprestimos.model.EmprestimoModel;
import biblioteca.emprestimos.views.AlunoException;
import biblioteca.emprestimos.views.InacessivelException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class EmprestimoRepository {

	private Connection con;
	SimpleDateFormat simpleFormat;

	public EmprestimoRepository(Connection con) {
		this.con = con;
		simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
	}

	public void deletar(String id) throws SQLException, ParseException {
		String sql;
		PreparedStatement stat;
		int idParsed = Integer.parseUnsignedInt(id);
		sql = "DELETE FROM `emprestimos` WHERE `id` = ?";

		stat = con.prepareStatement(sql);
		stat.setInt(1, idParsed);
		stat.executeUpdate();
	}

	public double devolver(String id) throws SQLException, ParseException {
		String sql;
		int idParsed = Integer.parseUnsignedInt(id);
		double multa = 0.00;
		sql = "SELECT * FROM `emprestimos` WHERE `id`= ?";

		java.util.Date dataDevolucao = new java.util.Date();
		PreparedStatement stat = con.prepareStatement(sql);
		stat.setInt(1, idParsed);
		ResultSet resultadoBusca = stat.executeQuery();
		resultadoBusca.next();
		EmprestimoModel emprestimo = resultSetParaDisciplina(resultadoBusca);

		if (!dataDevolucao.after(emprestimo.getDataPrevDevol())) {
			multa = 0.00;
		} else {
			long tempoEmprestimo = dataDevolucao.getTime() - emprestimo.getDataPrevDevol().getTime();
			int days = (int) (tempoEmprestimo / 86400000) - 1;
			multa = days * EmprestimoModel.multaPerDay;
		}

		sql = "UPDATE `emprestimos` SET `data-devolucao` = ?, `multa` = ? WHERE `id` = ?";

		stat = con.prepareStatement(sql);
		stat.setDate(1, new java.sql.Date(dataDevolucao.getTime()));
		stat.setDouble(2, multa);
		stat.setInt(3, idParsed);
		stat.executeUpdate();

		return multa;
	}


	public boolean inserir(Map<String, String> valores) throws NumberFormatException, SQLException, ParseException, InacessivelException, AlunoException {

                PreparedStatement ps;
                ResultSet resultadoBusca;

		long idAlunos = 00000000000L;
                String SidAlunos;
		if (valores.containsKey("id-alunos")) {
			SidAlunos = (valores.get("id-alunos"));
                        if(SidAlunos.length()!=11) throw new AlunoException("Número inválido para um CPF.");
                        idAlunos = Long.parseLong(SidAlunos);
		}
                else throw new AlunoException("O id(CPF) do aluno é obrigatório");
               Connection conDiario = utils.ConnectionFactory.getDiario();
                ps = conDiario.prepareStatement("SELECT * FROM `alunos` WHERE `id` = ?");
                ps.setLong(1, idAlunos);
                resultadoBusca = ps.executeQuery();

                if(!resultadoBusca.next()) throw new AlunoException("Não existe esse aluno.");

		int idAcervo = 0;

		if (valores.containsKey("id-acervo")) {
			idAcervo = Integer.parseUnsignedInt(valores.get("id-acervo"));
		}

                ps = con.prepareStatement("SELECT * FROM `acervo` WHERE `id` = ? ");
                ps.setInt(1, idAcervo);
                resultadoBusca = ps.executeQuery();

                if(!resultadoBusca.next()) throw new AlunoException("Não existe esse acervo.");


		ps = con.prepareStatement("SELECT * FROM `emprestimos` WHERE `id-acervo` = ? AND `data-devolucao`= '1970-01-01'");
		ps.setInt(1, idAcervo);
		resultadoBusca = ps.executeQuery();
		while (resultadoBusca.next()) {
			throw new InacessivelException("Este livro, atualmente, já está emprestado");
		}

		Date dataEmprestimo = new Date(new Date().getTime());

		if (valores.containsKey("data-emprestimo")) {
			dataEmprestimo = simpleFormat.parse(valores.get("data-emprestimo"));
		}

		Date dataPrevDevol = new Date(dataEmprestimo.getYear(), dataEmprestimo.getMonth(), dataEmprestimo.getDate() + EmprestimoModel.tempoEmprestimo);

		if (valores.containsKey("data-prev-devol")) {
			dataPrevDevol = simpleFormat.parse(valores.get("data-prev-devol"));
		}

		Date dataDevolucao = new Date(0);

		if (valores.containsKey("data-devolucao")) {
			dataDevolucao = simpleFormat.parse(valores.get("data-devolucao"));
		}

		double multa = 0.00;

		if (valores.containsKey("multa")) {
			multa = Double.parseDouble(valores.get("multa"));
		}
		ps = con.prepareStatement("INSERT INTO `emprestimos` (`id-alunos`, `id-acervo`, `data-emprestimo`, `data-prev-devol`, `data-devolucao`, `multa`) VALUES (?, ?, ?, ?, ?, ?)");
		ps.setLong(1, idAlunos);
		ps.setInt(2, idAcervo);
		ps.setDate(3, new java.sql.Date(dataEmprestimo.getTime()));
		ps.setDate(4, new java.sql.Date(dataPrevDevol.getTime()));
		ps.setDate(5, new java.sql.Date(dataDevolucao.getTime()));
		ps.setDouble(6, multa);

		int sucesso = ps.executeUpdate();

		return sucesso != 0;

	}

	public boolean atualizar(SortedMap<String, String> filtros, String id) throws SQLException, NumberFormatException, ParseException, AlunoException {
		int idParsed = Integer.parseUnsignedInt(id);
		if (filtros.containsKey("id-alunos")) {
			 Long.parseLong(filtros.get("id-alunos"));
		}

		if (filtros.containsKey("id-acervo")) {
			Integer.parseUnsignedInt(filtros.get("id-acervo"));
		}

		if (filtros.containsKey("data-emprestimo")) {
			Date.parse(filtros.get("data-emprestimo"));
		}

		if (filtros.containsKey("data-prev-devol")) {
			Date.parse(filtros.get("data-pre-devol"));
		}

		if (filtros.containsKey("data-devolucao")) {
			Date.parse(filtros.get("data-devolucao"));
		}

		if (filtros.containsKey("multa")) {
			Double.parseDouble(filtros.get("multa"));
		}

		EmprestimoModel emprestimo = consultarId(Integer.toString(idParsed));
		Object[] vals = emprestimo.retornarValoresRestantes(filtros);
		String[] keys = {"id", "id-alunos", "id-acervo", "data-emprestimo", "data-prev-devol", "data-devolucao", "multa"};
		Map<String, Object> valores = new LinkedHashMap<>();

		for (int i = 0; i < keys.length; i++) {
			valores.put(keys[i], vals[i]);
		}
		return atualizarPorId(valores);
	}

	public boolean atualizarPorId(Map<String, Object> parametros) throws NumberFormatException, SQLException, ParseException, AlunoException {
		int id = Integer.parseUnsignedInt(parametros.get("id").toString());
		long idAlunos = Long.parseUnsignedLong(parametros.get("id-alunos").toString());
		int idAcervo = Integer.parseUnsignedInt(parametros.get("id-acervo").toString());

		Date dataEmprestimo = (Date) (parametros.get("data-emprestimo"));

		Date dataPrevDevol = (Date) (parametros.get("data-prev-devol"));

		Date dataDevolucao = (Date) (parametros.get("data-devolucao"));
		double multa = Double.parseDouble(parametros.get("multa").toString());

		Connection conDiario = utils.ConnectionFactory.getDiario();
		PreparedStatement ps = conDiario.prepareStatement("SELECT * FROM `alunos` WHERE `id` = ? ");
                ps.setLong(1, idAlunos);
                ResultSet resultadoBusca = ps.executeQuery();
                if(!resultadoBusca.next()) throw new AlunoException("Não existe esse aluno.");
                ps = con.prepareStatement("SELECT * FROM `acervo` WHERE `id` = ? ");
                ps.setInt(1, idAcervo);
                resultadoBusca = ps.executeQuery();
                if(!resultadoBusca.next()) throw new AlunoException("Não existe esse acervo.");

                ps = con.prepareStatement("UPDATE `emprestimos` SET `id-alunos` = ?, `id-acervo` = ?, `data-emprestimo` = ?, `data-prev-devol` = ?, `data-devolucao` = ?, `multa` = ? WHERE `id` = ?");
		ps.setLong(1, idAlunos);
		ps.setInt(2, idAcervo);
		ps.setDate(3, new java.sql.Date(dataEmprestimo.getTime()));
		ps.setDate(4, new java.sql.Date(dataPrevDevol.getTime()));
		ps.setDate(5, new java.sql.Date(dataDevolucao.getTime()));
		ps.setDouble(6, multa);
		ps.setInt(7, id);
		int sucesso = ps.executeUpdate();

		return sucesso != 0;
	}

	public Set<EmprestimoModel> consultar(Map<String, String> filtros) throws NumberFormatException, SQLException, ParseException {
		String sql;
		Set<EmprestimoModel> emprestimoResultado = new LinkedHashSet<>();
		sql = "SELECT * FROM `emprestimos` ORDER BY `id`";
		int idAcervo = -1;
                Long idAlunos = -1L;
		java.sql.Date dataEmprestimo = new java.sql.Date(0), dataPrevDevol = new java.sql.Date(0), dataDevolucao = new java.sql.Date(0);
		double multa = -1;

		if (filtros.containsKey("id-alunos")) {
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
			idAlunos = Long.parseLong(filtros.get("id-alunos"));
		}

		if (filtros.containsKey("id-acervo")) {
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem
			idAcervo = Integer.parseUnsignedInt(filtros.get("id-acervo"));
		}

		if (filtros.containsKey("data-emprestimo")) {
			dataEmprestimo = new java.sql.Date(simpleFormat.parse(filtros.get("data-emprestimo")).getTime());
		}

		if (filtros.containsKey("data-prev-devol")) {
			dataPrevDevol = new java.sql.Date(simpleFormat.parse(filtros.get("data-prev-devol")).getTime());
		}

		if (filtros.containsKey("data-devolucao")) {
			dataDevolucao = new java.sql.Date(simpleFormat.parse(filtros.get("data-devolucao")).getTime());
		}

		if (filtros.containsKey("multa")) {
			multa = Double.parseDouble(filtros.get("multa"));
		}

		ResultSet resultadoBusca = con.prepareCall(sql).executeQuery();

		boolean adicionar;
		while (resultadoBusca.next()) {
			adicionar = true;

			EmprestimoModel emprestimo = resultSetParaDisciplina(resultadoBusca);
			if (filtros.containsKey("id-alunos")) {
				if (idAlunos != emprestimo.getIdAlunos()) {
					adicionar = false;
				}
			}

			if (filtros.containsKey("id-acervo")) {
				if (idAcervo != emprestimo.getIdAcervo()) {
					adicionar = false;
				}
			}

			if (filtros.containsKey("data-emprestimo")) {
				if (dataEmprestimo != emprestimo.getDataEmprestimo()) {
					adicionar = false;
				}
			}

			if (filtros.containsKey("data-prev-devol")) {
				if (dataPrevDevol != emprestimo.getDataPrevDevol()) {
					adicionar = false;
				}
			}

			if (filtros.containsKey("data-devolucao")) {
				if (dataDevolucao != emprestimo.getDataDevolucao()) {
					adicionar = false;
				}
			}

			if (filtros.containsKey("multa")) {
				if (multa != emprestimo.getMulta()) {
					adicionar = false;
				}
			}
			if (adicionar) {
				emprestimoResultado.add(emprestimo);
			}
		}

		return emprestimoResultado;
	}

	private EmprestimoModel resultSetParaDisciplina(ResultSet res) throws SQLException, ParseException {

		int id = res.getInt("id");
		Long idAlunos = res.getLong("id-alunos");
		int idAcervo = res.getInt("id-acervo");

		Date dataEmprestimo = simpleFormat.parse(res.getDate("data-emprestimo").toString());
		dataEmprestimo.setTime(dataEmprestimo.getTime() + 86400000);
		Date dataPrevDevol = simpleFormat.parse(res.getDate("data-prev-devol").toString());
		dataPrevDevol.setTime(dataPrevDevol.getTime() + 86400000);
		Date dataDevolucao = simpleFormat.parse(res.getDate("data-devolucao").toString());
		dataDevolucao.setTime(dataDevolucao.getTime() + 86400000);
		double multa = res.getDouble("multa");

		return new EmprestimoModel(id, idAlunos, idAcervo, dataEmprestimo, dataPrevDevol, dataDevolucao, multa);
	}

	public EmprestimoModel consultarId(String idStr) throws NumberFormatException, SQLException, ParseException {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM `emprestimos` WHERE `id` = ?");

		// Se id não for um inteiro sem sinal, joga a exceção NumberFormatException
		int id = Integer.parseUnsignedInt(idStr);

		ps.setInt(1, id);

		ResultSet resultado = ps.executeQuery();

		if (resultado.next()) {
			return resultSetParaDisciplina(resultado);
		}
		return null;

	}

}
