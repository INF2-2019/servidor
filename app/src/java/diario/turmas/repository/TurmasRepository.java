package diario.turmas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.ConnectionFactory;

public class TurmasRepository {

    private Connection con;

    public TurmasRepository() {
        this(ConnectionFactory.getDiario());
    }

    public TurmasRepository(Connection c) {
        con = c;
    }

    public boolean deletaTurma(String idStr) throws SQLException, NumberFormatException, NullPointerException {
        int idInt = Integer.parseInt(idStr);

        String sql = "DELETE FROM turmas WHERE id = " + idInt;
        PreparedStatement prepared = con.prepareStatement(sql);

        int deu = prepared.executeUpdate();

        return deu != 0;
    }

    public boolean insereTurma(String idStr, String idCursosStr, String nome) throws SQLException, NumberFormatException, NullPointerException {
        int idInt = Integer.parseInt(idStr);
        int idCursosInt = Integer.parseInt(idCursosStr);

        String sql = "INSERT INTO turmas (`id`, `id-cursos`, `nome`) VALUES (" + idInt + "," + idCursosInt + ",'" + nome + "')";
        PreparedStatement prepared = con.prepareStatement(sql);

        int deu = prepared.executeUpdate();

        return deu != 0;
    }

    public boolean alteraTurma(String idStr, String idCursosStr, String nome) throws SQLException, NumberFormatException, NullPointerException {
        int idInt = Integer.parseInt(idStr);
        int idCursosInt = Integer.parseInt(idCursosStr);

        String sql = "UPDATE turmas SET `id-cursos` = " + idCursosInt + ", `nome` = '" + nome + "' WHERE id = " + idInt;
        PreparedStatement prepared = con.prepareStatement(sql);

        int deu = prepared.executeUpdate();

        return deu != 0;
    }
}
