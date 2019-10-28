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
    SimpleDateFormat simpleFormat;

    public EmprestimoRepository(Connection con) {
	this.con = con;
        simpleFormat = new SimpleDateFormat("yyyy-mm-dd");
    }

    public void deletar(String id) throws SQLException {
	String sql;
	int idParsed = Integer.parseUnsignedInt(id);
                  sql = "SELECT * FROM `emprestimos` WHERE `id`= ?";
                  Date dataDevolucao = new Date();
                  ResultSet resultadoBusca = con.prepareCall(sql).executeQuery();
                  
	sql = "UPDATE `emprestimos` set  `data-devolucao` = ?, `multa` = ? WHERE `id` = ?";

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

	Date dataEmprestimo = new Date();
	String dataE = simpleFormat.format(dataEmprestimo);
	dataEmprestimo = simpleFormat.parse(dataE);

	if (valores.containsKey("data-emprestimo")) {
	    dataEmprestimo = simpleFormat.parse(valores.get("data-emprestimo"));
	}

	Date dataPrevDevol = new Date(dataEmprestimo.getYear(), dataEmprestimo.getMonth(), dataEmprestimo.getDate() + EmprestimoModel.tempoEmprestimo);
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

    public boolean atualizar(SortedMap<String, String> filtros, String id) throws SQLException, NumberFormatException, ParseException {
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

	EmprestimoModel emprestimo = consultarId(Integer.toString(idParsed));
	Object[] vals = emprestimo.retornarValoresRestantes(filtros);
	String[] keys = {"id", "id-alunos", "id-acervo", "data-emprestimo", "data-prev-devol", "data-devolucao", "multa"};
	Map<String, Object> valores = new LinkedHashMap<>();

	for (int i = 0; i < keys.length; i++) {
	    valores.put(keys[i], vals[i]);
	}
	return atualizarPorId(valores);
    }

    public boolean atualizarPorId(Map<String, Object> parametros) throws NumberFormatException, SQLException, ParseException {
	int id = Integer.parseUnsignedInt(parametros.get("id").toString());
	int idAlunos = Integer.parseUnsignedInt(parametros.get("id-alunos").toString());
	int idAcervo = Integer.parseUnsignedInt(parametros.get("id-acervo").toString());
        Date dataEmprestimo = simpleFormat.parse(parametros.get("data-emprestimo").toString());
        Date dataPrevDevol = simpleFormat.parse(parametros.get("data-prev-devol").toString());
        Date dataDevolucao = simpleFormat.parse(parametros.get("data-devolucao").toString());
        double multa = Double.parseDouble(parametros.get("multa").toString());

	PreparedStatement ps = con.prepareStatement("UPDATE `emprestimos` SET `id-alunos` = ?, `id-acervo` = ?, `data-emprestimo` = ?, `data-prev-devol` = ?, `data-devolucao` = ?, `multa` = ? WHERE `id` = ?");
	ps.setInt(1, idAlunos);
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
	int idAlunos = -1, idAcervo = -1;
        java.sql.Date dataEmprestimo = new java.sql.Date(-1, -1, -1), dataPrevDevol = new java.sql.Date(-1, -1, -1), dataDevolucao = new java.sql.Date(-1, -1, -1);
        double multa = -0.1;
        

	if (filtros.containsKey("id-alunos")) {
	    // Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
	    idAlunos = Integer.parseUnsignedInt(filtros.get("id-alunos"));
	}

	if (filtros.containsKey("id-acervo")) {
	    // Se lançar a exceção NumberFormatException, o valor não é um inteiro sem
	    idAcervo = Integer.parseUnsignedInt(filtros.get("id-acervo"));
	}
        
        if(filtros.containsKey("data-emprestimo")){
            dataEmprestimo = new java.sql.Date(simpleFormat.parse(filtros.get("data-emprestimo")).getTime());
        }
        
        if(filtros.containsKey("data-prev-devol")){
            dataPrevDevol = new java.sql.Date(simpleFormat.parse(filtros.get("data-prev-devol")).getTime());
        }
        
        if(filtros.containsKey("data-devolucao")){
            dataDevolucao = new java.sql.Date(simpleFormat.parse(filtros.get("data-devolucao")).getTime());
        }
        
        if(filtros.containsKey("multa")){
            multa = Double.parseDouble(filtros.get("multa"));
        }
        
	ResultSet resultadoBusca = con.prepareCall(sql).executeQuery();

	boolean adicionar;
	while (resultadoBusca.next()) {
	    adicionar = true;

	    EmprestimoModel emprestimo = resultSetParaDisciplina(resultadoBusca);
	    if (filtros.containsKey("id-alunos")) {
                if(idAlunos != emprestimo.getIdAlunos()){
                    adicionar = false;
                }
            }

            if (filtros.containsKey("id-acervo")) {
                if(idAcervo != emprestimo.getIdAcervo()){
                    adicionar = false;
                }
            }

            if(filtros.containsKey("data-emprestimo")){
                if(dataEmprestimo != emprestimo.getDataEmprestimo()){
                    adicionar = false;
                }
            }

            if(filtros.containsKey("data-prev-devol")){
                if(dataPrevDevol != emprestimo.getDataPrevDevol()){
                    adicionar = false;
                }
            }

            if(filtros.containsKey("data-devolucao")){
                if(dataDevolucao != emprestimo.getDataDevolucao()){
                    adicionar = false;
                }
            }

            if(filtros.containsKey("multa")){
                if(multa != emprestimo.getMulta()){
                    adicionar = false;
                }
            }
	}

	return emprestimoResultado;
    }

    private EmprestimoModel resultSetParaDisciplina(ResultSet res) throws SQLException, ParseException {
	int id = res.getInt("id");
	int idAlunos = res.getInt("id-alunos");        
	int idAcervo = res.getInt("id-acervo");
                  Date dataEmprestimo = simpleFormat.parse(res.getDate("data-emprestimo").toString());
                  Date dataPrevDevol = simpleFormat.parse(res.getDate("data-prev-devol").toString());
                  Date dataDevolucao = simpleFormat.parse(res.getDate("data-devolucao").toString());
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
