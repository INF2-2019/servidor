package diario.disciplinas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import diario.disciplinas.model.DisciplinaModel;
import java.util.SortedMap;

public class DisciplinaRepository {

    private Connection con;

    public DisciplinaRepository(Connection con) {
        this.con = con;
    }

    public void Deletar(String id) throws SQLException {
        String sql;
        int idParsed = Integer.parseUnsignedInt(id);
        sql = "DELETE FROM `disciplinas` WHERE `id` = ?";

        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1, idParsed);
        stat.executeUpdate();
    }

    public boolean inserir(Map<String, String> valores) throws NumberFormatException, SQLException {
  
        if (valores.size() != 3) {
            return false;
        }

        int idTurmas = 0;

        if (valores.containsKey("id-turmas")) {
            idTurmas = Integer.parseUnsignedInt(valores.get("id-turmas"));
        }

        int cargaHorariaMin = 0;

        if (valores.containsKey("carga-horaria-min")) {
            cargaHorariaMin = Integer.parseUnsignedInt(valores.get("carga-horaria-min"));
        }

        PreparedStatement ps = con.prepareStatement("INSERT INTO `disciplinas` (`id-turmas`, `nome`, `carga-horaria-min`) VALUES (?, ?, ?)");

        ps.setInt(1, idTurmas);
        ps.setString(2, valores.get("nome"));
        ps.setInt(3, cargaHorariaMin);

        int sucesso = ps.executeUpdate();

        return sucesso != 0;

    }

    public boolean atualizar(SortedMap<String, String> filtros, String id) throws SQLException {
        int idParsed = Integer.parseUnsignedInt(id);
        String query = "UPDATE `disciplinas` SET";
        String ultimo = null;
        for (Object iterador : filtros.entrySet()) {
            ultimo = ((Map.Entry<String, String>) iterador).getKey();
        }
        for (Object iterador : filtros.entrySet()) {
            if (((Map.Entry<String, String>) iterador).getKey().equals(ultimo)) {
                query += " `" + ((Map.Entry<String, String>) iterador).getKey() + "` = '" + ((Map.Entry<String, String>) iterador).getValue() + "'";
            } else {
                query += " `" + ((Map.Entry<String, String>) iterador).getKey() + "` = '" + ((Map.Entry<String, String>) iterador).getValue() + "',";
            }
        }
        query += " WHERE `id` = " + idParsed;

        PreparedStatement pse = con.prepareStatement(query);
        int sucesso = pse.executeUpdate();
        return sucesso != 0;
    }

    public Set<DisciplinaModel> consultar(Map<String, String> filtros) throws NumberFormatException, SQLException {
        String sql;
        Set<DisciplinaModel> DisciplinaResultado = new LinkedHashSet<>();
        boolean jaAdicionado = false;

        if (filtros.isEmpty()) {
            sql = "SELECT * FROM `disciplinas`";
        } else {
            sql = "SELECT * FROM `disciplinas` WHERE ";

            if (filtros.containsKey("id-turmas")) {
                Integer.parseUnsignedInt(filtros.get("id-turmas"));
            }

            if (filtros.containsKey("carga-horaria-min")) {
                Integer.parseUnsignedInt(filtros.get("carga-horaria-min"));
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

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet resultadoBusca = ps.executeQuery();

        // Itera por cada item do resultado e adiciona nos resultados
        while (resultadoBusca.next()) {
            DisciplinaResultado.add(resultSetParaDisciplina(resultadoBusca));
        }

        return DisciplinaResultado;
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
        return new DisciplinaModel(id,0,"erro",0);

    }
}
