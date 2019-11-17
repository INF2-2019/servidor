/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.diario.diario;

import diario.diario.views.ExcecaoPadrao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/**
 * @author juanr
 */
public class DiarioRepository {

    protected Connection conexao;

    public DiarioRepository(Connection conexao) {
	this.conexao = conexao;
    }

    public boolean insere(DiarioModel modelo) throws SQLException, ExcecaoPadrao {
	DiarioModel filtro = new DiarioModel(modelo.getIdConteudo(), modelo.getIdMatricula());
	ArrayList<DiarioModel> lista = consulta(filtro);

	// Se diario já existir, atualiza o existente com os novos dados
	if (lista.size() > 0) {
	    return atualizar(modelo, filtro);
	}

	String query = "INSERT INTO diario(`id-conteudos`,`id-matriculas`, faltas, nota) VALUES (?, ?, COALESCE(?,0), COALESCE(?,0.0))";
	PreparedStatement st = conexao.prepareStatement(query);
	st.setInt(1, modelo.getIdConteudo());
	st.setInt(2, modelo.getIdMatricula());

	if (modelo.getFalta() != null) {
	    st.setInt(3, modelo.getFalta());
	} else {
	    st.setNull(3, Types.INTEGER);
	}

	if (modelo.getNota() != null) {
	    st.setDouble(4, modelo.getNota());
	} else {
	    st.setNull(4, Types.DECIMAL);
	}

	int r = st.executeUpdate();

	st.close();
	conexao.close();

	return r != 0;
    }

    public boolean atualizar(DiarioModel modelo, DiarioModel filtro) throws SQLException, ExcecaoPadrao {
	String query = "UPDATE diario SET"
		+ "`id-conteudos`=COALESCE(?,diario.`id-conteudos`),"
		+ "`id-matriculas`=COALESCE(?,diario.`id-matriculas`),"
		+ " faltas=COALESCE(?, diario.faltas),"
		+ " nota=COALESCE(?, diario.nota)";
	PreparedStatement st;

	if (filtro.getIdConteudo() != null && filtro.getIdMatricula() != null) {
	    query += " WHERE `id-conteudos`=? AND `id-matriculas`=?";
	    st = conexao.prepareStatement(query);
	    st.setInt(5, filtro.getIdConteudo());
	    st.setInt(6, filtro.getIdMatricula());
	} else if (filtro.getIdConteudo() != null) {
	    query += " WHERE `id-conteudos`=?";
	    st = conexao.prepareStatement(query);
	    st.setInt(5, filtro.getIdConteudo());
	} else if (filtro.getIdMatricula() != null) {
	    query += " WHERE `id-matriculas`=?";
	    st = conexao.prepareStatement(query);
	    st.setInt(5, filtro.getIdMatricula());
	} else {
	    throw new ExcecaoPadrao("Nenhuma alteração efetuada!");
	}

	if (modelo.getIdConteudo() != null) {
	    st.setNull(1, modelo.getIdConteudo());
	} else {
	    st.setNull(1, Types.INTEGER);
	}

	if (modelo.getIdMatricula() != null) {
	    st.setInt(2, modelo.getIdMatricula());
	} else {
	    st.setNull(2, Types.INTEGER);
	}

	if (modelo.getFalta() != null) {
	    st.setInt(3, modelo.getFalta());
	} else {
	    st.setNull(3, Types.INTEGER);
	}

	if (modelo.getNota() != null) {
	    st.setDouble(4, modelo.getNota());
	} else {
	    st.setNull(4, Types.DECIMAL);
	}

	int r = st.executeUpdate();

	st.close();
	conexao.close();

	return r != 0;
    }

    /* Deletar */
    public boolean remover(DiarioModel filtro) throws SQLException, ExcecaoPadrao {
	String query = "DELETE FROM diario";
	PreparedStatement st;

	int r = 0;

	if (filtro.getIdConteudo() != null && filtro.getIdMatricula() != null) {
	    query += " WHERE `id-conteudos`=? AND `id-matriculas`=?";
	    st = conexao.prepareStatement(query);
	    st.setInt(1, filtro.getIdConteudo());
	    st.setInt(2, filtro.getIdMatricula());
	    r = st.executeUpdate();

	} else if (filtro.getIdConteudo() != null) {
	    query += " WHERE `id-conteudos`=?";
	    st = conexao.prepareStatement(query);
	    st.setInt(1, filtro.getIdConteudo());
	    r = st.executeUpdate();
	    st.close();
	} else if (filtro.getIdMatricula() != null) {
	    query += " WHERE `id-matriculas`=?";
	    st = conexao.prepareStatement(query);
	    st.setInt(1, filtro.getIdMatricula());
	    r = st.executeUpdate();
	    st.close();
	} else {
	    throw new ExcecaoPadrao("A ação de deletar toda tabela não é permitida!");
	}
	return r != 0;
    }

    /* Consulta */
    public ArrayList consulta(DiarioModel filtro) throws SQLException {
	String query = "SELECT * FROM diario";
	PreparedStatement st;

	if (filtro.getIdConteudo() != null && filtro.getIdMatricula() != null) {
	    query += " WHERE `id-conteudos`=? AND `id-matriculas`=?";
	    st = conexao.prepareStatement(query);
	    st.setInt(1, filtro.getIdConteudo());
	    st.setInt(2, filtro.getIdMatricula());
	} else if (filtro.getIdConteudo() != null) {
	    query += " WHERE `id-conteudos`=?";
	    st = conexao.prepareStatement(query);
	    st.setInt(1, filtro.getIdConteudo());
	} else if (filtro.getIdMatricula() != null) {
	    query += " WHERE `id-matriculas`=?";
	    st = conexao.prepareStatement(query);
	    st.setInt(1, filtro.getIdMatricula());
	} else {
	    st = conexao.prepareStatement(query);
	    //throw new ExcecaoPadrao("Operação Inválida: Não pode mostrar tudo.");
	}

	ResultSet resultado = st.executeQuery();
	ArrayList<DiarioModel> lista = new ArrayList<>();
	while (resultado.next()) {
	    DiarioModel modelo = new DiarioModel(resultado.getInt("id-conteudos"), resultado.getInt("id-matriculas"), resultado.getInt("faltas"), resultado.getDouble("nota"));
	    lista.add(modelo);
	}

	st.close();
	return lista;
    }

}
