package diario.transferencia.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransfereRepository {

	private final Connection con;

	public TransfereRepository(Connection con) {
		this.con = con;
	}

	public void transfere(long cpf) throws SQLException, AlunoInativoException {
		try (PreparedStatement prst = con.prepareStatement("UPDATE `matriculas` SET `ativo` = 0 WHERE `id-alunos` = ? AND `ativo` = 1")) {
			prst.setLong(1, cpf);
			if (prst.executeUpdate() == 0) {
				throw new AlunoInativoException();
			}
		}
	}

}
