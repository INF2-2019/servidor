package diario.etapas.repository;

import diario.etapas.model.EtapasModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
            sql = "SELECT * FROM `etapas`";
        } else {
            sql = "SELECT * FROM `etapas` WHERE ";

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
                double valor = Double.parseDouble(filtros.get("valor"));
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

    public EtapasModel consultarId(String idStr) throws NumberFormatException, SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM `etapas` WHERE `id` = ?");
        // Se id não for um inteiro sem sinal, joga a exceção NumberFormatException
        int id = Integer.parseUnsignedInt(idStr);

        ps.setInt(1, id);

        ResultSet resultado = ps.executeQuery();

        if (resultado.next()) {
            return resultSetParaEtapas(resultado);
        }

        return null;

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
        if (valores.size() != 2) {
            return false;
        }
        
        int ano = 0;
        if (valores.containsKey("ano")) {
            ano = Integer.parseInt(valores.get("ano"));
        }

        double valor = 0;
        if (valores.containsKey("valor")) {
            valor = Double.parseDouble(valores.get("valor"));
        }

        PreparedStatement ps = con.prepareStatement("INSERT INTO `etapas` (`ano`, `valor`) VALUES (?, ?)");

        ps.setInt(1, ano);
        ps.setDouble(2, valor);

        int sucesso = ps.executeUpdate();

        return sucesso != 0;

    }

    public boolean atualizarPorId(Map<String, Object> parametros) throws NumberFormatException, SQLException {
        int id = Integer.parseUnsignedInt(parametros.get("id").toString());
        int ano = Integer.parseUnsignedInt(parametros.get("ano").toString());
        double valor = Double.parseDouble(parametros.get("valor").toString());

        PreparedStatement ps = con.prepareStatement("UPDATE `etapas` SET `ano` = " + ano + ", `valor` = " + valor + " WHERE `id` = " + id);

        int sucesso = ps.executeUpdate();

        return sucesso != 0;
    }

    public boolean atualizar(Map<String, String> parametros) throws NumberFormatException, SQLException {
        int id = Integer.parseUnsignedInt(parametros.get("id"));

        if (parametros.containsKey("ano")) {
            Integer.parseUnsignedInt(parametros.get("ano"));
        }

        if (parametros.containsKey("valor")) {
            double valor = Double.parseDouble(parametros.get("valor"));
            if (valor < 0) {
                throw new NumberFormatException();
            }
        }

        EtapasModel etapa = consultarId(Integer.toString(id));
        Object[] vals = etapa.retornarValoresRestantes(parametros);
        String[] keys = {"id", "ano", "valor"};
        Map<String, Object> valores = new LinkedHashMap<>();

        for (int i = 0; i < keys.length; i++) {
            valores.put(keys[i], vals[i]);
        }

        return atualizarPorId(valores);
    }

    private EtapasModel resultSetParaEtapas(ResultSet res) throws SQLException {
        int id = res.getInt("id");
        int ano = res.getInt("ano");
        int valor = res.getInt("valor");

        return new EtapasModel(id, ano, valor);
    }

}
