package biblioteca.reservas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import biblioteca.reservas.model.ReservaModel;
import biblioteca.reservas.views.AlunoException;
import biblioteca.reservas.views.ExcecaoEmprestimoCadastrado;
import biblioteca.reservas.views.ExcecaoreservaExistente;
import java.util.SortedMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReservaRepository {

	private Connection con;
	SimpleDateFormat simpleFormat;

	public ReservaRepository(Connection con) {
		this.con = con;
		simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
	}

	public void deletar(String id) throws SQLException {
		String sql;
		int idParsed = Integer.parseUnsignedInt(id);
		sql = "DELETE FROM `reservas` WHERE `id` = ?";
		PreparedStatement stat;
		stat = con.prepareStatement(sql);
		stat.setInt(1, idParsed);
		stat.executeUpdate();
	}

	public void emprestar(String id) throws SQLException, ParseException, ExcecaoEmprestimoCadastrado {
		// atualiza o registro na tabela
		String sql;
		int idParsed = Integer.parseUnsignedInt(id);
		sql = "SELECT * FROM `reservas` WHERE `id`= ?";
		java.util.Date dataEmp = new java.util.Date(new Date().getTime());
		int tempoEspera = 0;
		PreparedStatement stat;
		stat = con.prepareStatement(sql);
		stat.setInt(1, idParsed);
		ResultSet resultadoBusca = stat.executeQuery();
		resultadoBusca.next();
		ReservaModel reserva = resultSetParaDisciplina(resultadoBusca);
		
		PreparedStatement ps = con.prepareStatement("SELECT * FROM `emprestimos` WHERE `id-acervo` = ? AND `data-devolucao`= '1970-01-01'");
		ps.setInt(1, reserva.getIdAcervo());
		ResultSet busca = ps.executeQuery();
		while (busca.next()) {
			throw new ExcecaoEmprestimoCadastrado("Este livro, atualmente, já está emprestado");
		}

		if (!dataEmp.after(reserva.getDataReserva())) {
			tempoEspera = 0;
		} else {
			long tempoEmprestimo = dataEmp.getTime() - reserva.getDataReserva().getTime();
			int days = (int) (tempoEmprestimo / 86400000);
			if (days != 0) {
			   days-=1;
			}
			tempoEspera = days;
		}
		sql = "UPDATE `reservas` SET `tempo-espera` = ?, `emprestou` = ? WHERE `id` = ?";
		stat = con.prepareStatement(sql);
		stat.setInt(1, tempoEspera);
		stat.setBoolean(2, true);
		stat.setInt(3, idParsed);
		stat.executeUpdate();

		// cria um registro na tabela emprestimos
		Date dataEmprestimo = new Date(new Date().getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(dataEmprestimo);
		c.add(Calendar.DATE, +7);
		Date dataPrevDevol = c.getTime();
		Date dataDevolucao = new Date(0);
		double multa = 0.00;
		 ps = con.prepareStatement("INSERT INTO `emprestimos` (`id-alunos`, `id-acervo`, `data-emprestimo`, `data-prev-devol`, `data-devolucao`, `multa`) VALUES (?, ?, ?, ?, ?, ?)");
		ps.setLong(1, reserva.getIdAlunos());
		ps.setInt(2, reserva.getIdAcervo());
		ps.setDate(3, new java.sql.Date(dataEmprestimo.getTime()));
		ps.setDate(4, new java.sql.Date(dataPrevDevol.getTime()));
		ps.setDate(5, new java.sql.Date(dataDevolucao.getTime()));
		ps.setDouble(6, multa);
		ps.executeUpdate();
	}

	public boolean inserir(Map<String, String> valores) throws NumberFormatException, SQLException, ParseException, ExcecaoreservaExistente, AlunoException {
		
                PreparedStatement ps;
                ResultSet resultadoBusca;
		if (valores.size() < 2) {
			return false;
		}

		long idAlunos = 0L;
                String SidAlunos = "";
                
		if (valores.containsKey("id-alunos")) {
                    SidAlunos = valores.get("id-alunos");
                    if(SidAlunos.length()!=11) throw new AlunoException("Número inválido para um CPF.");
                    idAlunos = Long.parseUnsignedLong(valores.get("id-alunos"));
		}
                else throw new AlunoException("O id(CPF) do aluno é obrigatório");
                //Verifica a existência do aluno
                ps = con.prepareStatement("SELECT * FROM `alunos` WHERE `id` = ? ");
                ps.setLong(1, idAlunos);
                resultadoBusca = ps.executeQuery();
                if(!resultadoBusca.next()) throw new AlunoException("Não existe esse aluno.");
                
		int idAcervo = 0;

		if (valores.containsKey("id-acervo")) {
			idAcervo = Integer.parseUnsignedInt(valores.get("id-acervo"));
		}
                //Verifica a existência do acervo
                ps = con.prepareStatement("SELECT * FROM `acervo` WHERE `id` = ? ");
                ps.setInt(1, idAcervo);
                resultadoBusca = ps.executeQuery();
                if(!resultadoBusca.next()) throw new AlunoException("Não existe esse acervo.");
                
		ps = con.prepareStatement("SELECT * FROM `reservas` WHERE `id-acervo` = ? AND `tempo-espera`= ?");
		ps.setInt(1, idAcervo);
		ps.setInt(2, 0);
		resultadoBusca = ps.executeQuery();

		while (resultadoBusca.next()) {
			throw new ExcecaoreservaExistente("Ja existe uma reserva sobre o item no Acervo");
		}

		int tempoEspera = 0;

		if (valores.containsKey("tempo-espera")) {
			idAcervo = Integer.parseUnsignedInt(valores.get("tempo-espera"));
		}
		Date dataReserva = new Date(new Date().getTime());

		if (valores.containsKey("data-reserva")) {
			dataReserva = simpleFormat.parse(valores.get("data-reserva"));
		}
		boolean emprestou = false;

		PreparedStatement busca = con.prepareStatement("SELECT * FROM `emprestimos` WHERE `id-acervo` = ? AND `data-devolucao`= '1970-01-01'");
		busca.setInt(1, idAcervo);
		resultadoBusca = busca.executeQuery();
		while (resultadoBusca.next()) {
			emprestou = true;
		}

		ps = con.prepareStatement("INSERT INTO `reservas` (`id-aluno`, `id-acervo`, `data-reserva`,`tempo-espera`,`emprestou`) VALUES (?, ?, ?, ?, ?)");

		ps.setLong(1, idAlunos);
		ps.setInt(2, idAcervo);
		ps.setDate(3, new java.sql.Date(dataReserva.getTime()));
		ps.setInt(4, tempoEspera);
		ps.setBoolean(5, emprestou);

		int sucesso = ps.executeUpdate();

		return sucesso != 0;

	}

	public boolean atualizar(Map<String, String> filtros, String id) throws SQLException, NumberFormatException, ParseException {
		int idParsed = Integer.parseUnsignedInt(id);
		if (filtros.containsKey("id-alunos")) {
			Long.parseUnsignedLong(filtros.get("id-alunos"));
		}

		if (filtros.containsKey("id-acervo")) {
			Integer.parseUnsignedInt(filtros.get("id-acervo"));
		}

		if (filtros.containsKey("tempo-espera")) {
			Integer.parseUnsignedInt(filtros.get("tempo-espera"));
		}

		if (filtros.containsKey("data-reserva")) {
			simpleFormat.parse(filtros.get("data-reserva"));
		}
		if (filtros.containsKey("emprestou")) {
			Boolean.parseBoolean(filtros.get("data-reserva"));
		}
		ReservaModel reserva = consultarId(Integer.toString(idParsed));
		Object[] vals = reserva.retornarValoresRestantes(filtros);
		String[] keys = {"id", "id-alunos", "id-acervo", "tempo-espera", "data-reserva", "emprestou"};
		Map<String, Object> valores = new LinkedHashMap<>();

		for (int i = 0; i < keys.length; i++) {
			valores.put(keys[i], vals[i]);
		}
		return atualizarPorId(valores);
	}

	public boolean atualizarPorId(Map<String, Object> parametros) throws NumberFormatException, SQLException, ParseException {
		int id = Integer.parseUnsignedInt(parametros.get("id").toString());
		long idAluno = Long.parseUnsignedLong(parametros.get("id-alunos").toString());
		int idAcervo = Integer.parseUnsignedInt(parametros.get("id-acervo").toString());
		int tempoEspera = Integer.parseUnsignedInt(parametros.get("tempo-espera").toString());
		Date dataReserva = simpleFormat.parse(parametros.get("data-reserva").toString());
		Boolean emprestou = Boolean.parseBoolean(parametros.get("emprestou").toString());
		PreparedStatement ps = con.prepareStatement("UPDATE `reservas` SET `id-aluno` = ?, `id-acervo` = ?, `tempo-espera` = ?, `data-reserva` = ?, `emprestou` = ?  WHERE `id` = ?");
		ps.setLong(1, idAluno);
		ps.setInt(2, idAcervo);
		ps.setInt(3, tempoEspera);
		ps.setDate(4, new java.sql.Date(dataReserva.getTime()));
		ps.setBoolean(5, emprestou);
		ps.setInt(6, id);

		int sucesso = ps.executeUpdate();

		return sucesso != 0;
	}

	public Set<ReservaModel> consultar(Map<String, String> filtros) throws NumberFormatException, SQLException, ParseException {
		String sql;
		Set<ReservaModel> disciplinaResultado = new LinkedHashSet<>();
		sql = "SELECT * FROM `reservas` ORDER BY `id`";
		long idAluno = -1L;
                int idAcervo = -1, tempoEspera = -1;
		boolean emprestou = false;
		java.sql.Date dataReserva = new java.sql.Date(0);
		if (filtros.containsKey("id-alunos")) {
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
			idAluno = Long.parseUnsignedLong(filtros.get("id-alunos"));
		}
		if (filtros.containsKey("tempo-espera")) {
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
			idAluno = Integer.parseUnsignedInt(filtros.get("tempo-espera"));
		}

		if (filtros.containsKey("id-acervo")) {
			// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem
			idAcervo = Integer.parseUnsignedInt(filtros.get("id-acervo"));
		}
		if (filtros.containsKey("data-reserva")) {
			dataReserva = new java.sql.Date(simpleFormat.parse(filtros.get("data-reserva")).getTime());
		}
		if (filtros.containsKey("emprestou")) {
			emprestou = Boolean.parseBoolean(filtros.get("emprestou"));
		}
		ResultSet resultadoBusca = con.prepareCall(sql).executeQuery();

		boolean adicionar;
		while (resultadoBusca.next()) {
			adicionar = true;

			ReservaModel reserva = resultSetParaDisciplina(resultadoBusca);
			if (filtros.containsKey("id-alunos")) {
				if (idAluno != reserva.getIdAlunos()) {
					adicionar = false;
				}
			}
			if (filtros.containsKey("nome")) {
				if (idAcervo != reserva.getIdAcervo()) {
					adicionar = false;
				}
			}
			if (filtros.containsKey("tempo-espera")) {
				if (tempoEspera != reserva.getTempoEspera()) {
					adicionar = false;
				}
			}
			if (filtros.containsKey("data-reserva")) {
				if (dataReserva != reserva.getDataReserva()) {
					adicionar = false;
				}
			}
			if (filtros.containsKey("emprestou")) {
				if (emprestou != reserva.isEmprestou()) {
					adicionar = false;
				}
			}
			if (adicionar) {
				disciplinaResultado.add(reserva);
			}
		}

		return disciplinaResultado;
	}

	private ReservaModel resultSetParaDisciplina(ResultSet res) throws SQLException, ParseException {
		int id = res.getInt("id");
		long idAlunos = res.getLong("id-aluno");
		int idAcervo = res.getInt("id-acervo");
		int tempoEspera = res.getInt("tempo-espera");
		Date dataReserva = simpleFormat.parse(res.getDate("data-reserva").toString());
		dataReserva.setTime(dataReserva.getTime() + 86400000);
		boolean emprestou = res.getBoolean("emprestou");
		return new ReservaModel(id, idAlunos, idAcervo, tempoEspera, dataReserva, emprestou);
	}

	public ReservaModel consultarId(String idStr) throws NumberFormatException, SQLException, ParseException {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM `reservas` WHERE `id` = ?");
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
