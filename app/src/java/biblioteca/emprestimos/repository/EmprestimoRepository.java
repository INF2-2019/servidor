package biblioteca.emprestimos.repository;

import biblioteca.emprestimos.model.EmprestimoModel;
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
//import diario.disciplinas.model.DisciplinaModel;
import java.util.SortedMap;

public class EmprestimoRepository {

    private Connection con;

    public EmprestimoRepository(Connection con) {
	this.con = con;
    }

    public void deletar(String id) throws SQLException {
	String sql;
	int idParsed = Integer.parseUnsignedInt(id);
	sql = "DELETE FROM `emprestimos` WHERE `id` = ?";

	PreparedStatement stat = con.prepareStatement(sql);
	stat.setInt(1, idParsed);
	stat.executeUpdate();
    }
    // TODO: Adequar ao sistema de datas

    public boolean inserir(Map<String, String> valores) throws NumberFormatException, SQLException, ParseException {

	if (valores.size() != 6) {
	    return false;
	}

	int idAlunos = 0;

	if (valores.containsKey("id-alunos")) {
	    idAlunos = Integer.parseUnsignedInt(valores.get("id-alunos"));
	}

	int idAcervo = 0;

	if (valores.containsKey("id-acervo")) {
	    idAcervo = Integer.parseUnsignedInt(valores.get("id-acervo"));
	}

	SimpleDateFormat simpleFormat = new SimpleDateFormat("dd-mm-yyyy");

	Date dataEmprestimo = new Date();
	String dataE = simpleFormat.format(dataEmprestimo);
	dataEmprestimo = simpleFormat.parse(dataE);

	if (valores.containsKey("data-emprestimo")) {
	    dataEmprestimo = simpleFormat.parse(valores.get("data-emprestimo"));
	}

	Date dataPrevDevol = new Date(dataEmprestimo.getYear(), dataEmprestimo.getMonth(), dataEmprestimo.getDate() + 7);
	String dataPD = simpleFormat.format(dataPrevDevol);
	dataEmprestimo = simpleFormat.parse(dataPD);

	if (valores.containsKey("data-prev-devol")) {
	    dataPrevDevol = simpleFormat.parse(valores.get("data-prev-devol"));
	}

	Date dataDevolucao = new Date(0, 0, 0);
	String dataD = simpleFormat.format(dataDevolucao);
	dataEmprestimo = simpleFormat.parse(dataD);

	if (valores.containsKey("data-devolucao")) {
	    dataDevolucao = simpleFormat.parse(valores.get("data-devolucao"));
	}

	double multa = 0.00;

	if (valores.containsKey("multa")) {
	    multa = Double.parseDouble(valores.get("multa"));
	}

	PreparedStatement ps = con.prepareStatement("INSERT INTO `emprestimos` (`id-alunos`, `id-acervo`, `data-emprestimo`, `data-prev-devol`, `data-devolucao`, `multa`) VALUES (?, ?, ?, ?, ?, ?)");

	ps.setInt(1, idAlunos);
	ps.setInt(2, idAcervo);
	ps.setDate(3, new java.sql.Date(dataEmprestimo.getTime()));
	ps.setDate(4, new java.sql.Date(dataPrevDevol.getTime()));
	ps.setDate(5, new java.sql.Date(dataDevolucao.getTime()));
	ps.setDouble(6, multa);

	int sucesso = ps.executeUpdate();

	return sucesso != 0;

    }

    public boolean atualizar(SortedMap<String, String> filtros, String id) throws SQLException {
	int idParsed = Integer.parseUnsignedInt(id);
	if (filtros.containsKey("id-alunos")) {
	    Integer.parseUnsignedInt(filtros.get("id-alunos"));
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

	EmprestimoModel disciplina = consultarId(Integer.toString(idParsed));
	Object[] vals = disciplina.retornarValoresRestantes(filtros);
	String[] keys = {"id", "id-alunos", "id-acervo", "data-emprestimo", "data-prev-devol", "data-devolucao", "multa"};
	Map<String, Object> valores = new LinkedHashMap<>();

	for (int i = 0; i < keys.length; i++) {
	    valores.put(keys[i], vals[i]);
	}
	return atualizarPorId(valores);
    }

    public boolean atualizarPorId(Map<String, Object> parametros) throws NumberFormatException, SQLException {
	int id = Integer.parseUnsignedInt(parametros.get("id").toString());
	int idTurma = Integer.parseUnsignedInt(parametros.get("id-turmas").toString());
	int cargaHorariaMin = Integer.parseUnsignedInt(parametros.get("carga-horaria-min").toString());

	PreparedStatement ps = con.prepareStatement("UPDATE `disciplinas` SET `id-turmas` = ?, `nome` = ?, `carga-horaria-min` = ?  WHERE `id` = ?");
	ps.setInt(1, idTurma);
	ps.setString(2, (String) parametros.get("nome"));
	ps.setInt(3, cargaHorariaMin);
	ps.setInt(4, id);

	int sucesso = ps.executeUpdate();

	return sucesso != 0;
    }

    public Set<DisciplinaModel> consultar(Map<String, String> filtros) throws NumberFormatException, SQLException {
	String sql;
	Set<DisciplinaModel> disciplinaResultado = new LinkedHashSet<>();
	sql = "SELECT * FROM `disciplinas` ORDER BY `id`";
	int idTurma = -1, horas = -1;

	if (filtros.containsKey("id-turmas")) {
	    // Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
	    idTurma = Integer.parseUnsignedInt(filtros.get("id-turmas"));
	}

	if (filtros.containsKey("carga-horaria-min")) {
	    // Se lançar a exceção NumberFormatException, o valor não é um inteiro sem
	    horas = Integer.parseUnsignedInt(filtros.get("carga-horaria-min"));
	}

	ResultSet resultadoBusca = con.prepareCall(sql).executeQuery();

	boolean adicionar;
	while (resultadoBusca.next()) {
	    adicionar = true;

	    DisciplinaModel disciplina = resultSetParaDisciplina(resultadoBusca);
	    if (filtros.containsKey("id-turmas")) {
		if (idTurma != disciplina.getIdTurmas()) {
		    adicionar = false;
		}
	    }
	    if (filtros.containsKey("nome")) {
		if (!filtros.get("nome").equals(disciplina.getNome())) {
		    adicionar = false;
		}
	    }
	    if (filtros.containsKey("carga-horaria-min")) {
		if (horas != disciplina.getCargaHorariaMin()) {
		    adicionar = false;
		}
	    }

	    if (adicionar) {
		disciplinaResultado.add(disciplina);
	    }
	}

	return disciplinaResultado;
    }

    private DisciplinaModel resultSetParaDisciplina(ResultSet res) throws SQLException {
	int id = res.getInt("id");
	int idTurmas = res.getInt("id-turmas");
	String nome = res.getString("nome");
	int cargaHorariaMin = res.getInt("carga-horaria-min");

	return new DisciplinaModel(id, idTurmas, nome, cargaHorariaMin);
    }

    public DisciplinaModel consultarId(String idStr) throws NumberFormatException, SQLException {
	PreparedStatement ps = con.prepareStatement("SELECT * FROM `disciplinas` WHERE `id` = ?");
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
