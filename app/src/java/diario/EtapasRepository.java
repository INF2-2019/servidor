package repository.diario;

import model.diario.EtapasModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class EtapasRepository {

    private Connection con;

    public EtapasRepository(Connection conexao) {
	this.con = conexao;
    }

    public Set<EtapasModel> consultar(Map<String, String> filtros) throws NumberFormatException, SQLException {
	// Base da query SQL
	String sql;
	Set<EtapasModel> etapasResultado = new LinkedHashSet<>();
	boolean jaAdicionado = false;

	if (filtros.isEmpty()) {
	    sql = "SELECT * FROM `cursos`";
	} else {
	    sql = "SELECT * FROM `cursos` WHERE ";

	    if (filtros.containsKey("id")) {
		// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
		// não preciso usar o valor do parse se der certo
		Integer.parseUnsignedInt(filtros.get("id"));
	    }

	    if (filtros.containsKey("ano")) {
		// Se lançar a exceção NumberFormatException, o valor não é um inteiro sem sinal
		// não preciso usar o valor do parse se der certo
		Integer.parseUnsignedInt(filtros.get("ano"));
	    }

	    if (filtros.containsKey("valor")) {
		double valor = Double.parseDouble(filtros.get("ano"));
		//Verifica se o valor é negativo, se sim, lança uma exceção
		if (valor < 0) {
		    throw new NumberFormatException();
		}
	    }

	    // Montar a query SQL com base nos filtros passados no Map
	    for (Map.Entry<String, String> filtro : filtros.entrySet()) {
		if (jaAdicionado) {
		    sql += "AND (`" + filtro.getKey() + "` = '" + filtro.getValue() + "') ";
		} else {
		    sql += "(`" + filtro.getKey() + "` = '" + filtro.getValue() + "') ";
		    jaAdicionado = true;
		}
	    }
	}
	sql += " ORDER BY `id`";

	ResultSet resultadoBusca = con.prepareCall(sql).executeQuery();

	// Itera por cada item do resultado e adiciona nos resultados
	while (resultadoBusca.next()) {
	    etapasResultado.add(resultSetParaEtapas(resultadoBusca));
	}

	return etapasResultado;
    }

    public boolean deletar(String idStr) throws NumberFormatException, SQLException {
	//Deletar de acordo com o id colocado
	PreparedStatement ps = con.prepareStatement("DELETE FROM `etapas` WHERE `id` = ?");

	// Se id não for um inteiro sem sinal, lança a exceção NumberFormatException
	int id = Integer.parseUnsignedInt(idStr);

	ps.setInt(1, id);

	int sucesso = ps.executeUpdate();

	// Se deletou algo, retorna true, senão retorna false
	return sucesso != 0;

    }

    public boolean inserir(Map<String, String> valores) throws NumberFormatException, SQLException {
	// Tem que ter os 3 valores a serem inseridos no BD (id, ano e valor)
	if (valores.size() != 3) {
	    return false;
	}

	int id = 0;
	if (valores.containsKey("id")) {
	    id = Integer.parseUnsignedInt(valores.get("id"));
	}

	int ano = 0;
	if (valores.containsKey("ano")) {
	    ano = Integer.parseUnsignedInt(valores.get("ano"));
	}

	int valor = 0;
	if (valores.containsKey("valor")) {
	    valor = Integer.parseUnsignedInt(valores.get("valor"));
	}

	PreparedStatement ps = con.prepareStatement("INSERT INTO `etapas` (`id`, `ano`, `valor`) VALUES (?, ?, ?)");

	ps.setInt(1, id);
	ps.setInt(2, ano);
	ps.setInt(3, valor);

	int sucesso = ps.executeUpdate();

	return sucesso != 0;

    }

    private EtapasModel resultSetParaEtapas(ResultSet res) throws SQLException {
	int id = res.getInt("id");
	int ano = res.getInt("ano");
	int valor = res.getInt("valor");

	return new EtapasModel(id, ano, valor);
    }

}
