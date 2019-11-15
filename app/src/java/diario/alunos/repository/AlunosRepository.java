package diario.alunos.repository;

import diario.campi.view.viewConsulta;
import utils.Hasher;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;


public class AlunosRepository {

	private Connection con;

	public AlunosRepository(Connection con) {
		this.con = con;
	}

	public String deletarAlunos(String id) throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM `matriculas` WHERE `id-alunos` = ?");
		long idParsed = Long.parseLong(id);
		ps.setLong(1, idParsed);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return "mat";
		}
		String sql;
		sql = "DELETE FROM `alunos` WHERE `id` = ?";

		PreparedStatement stat = con.prepareStatement(sql);
		stat.setLong(1, idParsed);

		int sucesso = stat.executeUpdate();
		// Se deletou algo, retorna true, senão retorna false
		if (sucesso != 0) {
			return "sucesso";
		}
		return "erro";
	}

	public boolean inserirAlunos(String id, String nome, String email, String senha, String sexo, String nascimento, String logradouro, String numero, String complemento, String bairro, String cidade, String cep, String uf, String foto) throws NumberFormatException, SQLException, NoSuchAlgorithmException, ParseException, InvalidKeySpecException {

		long idParsed = Long.parseLong(id);
		int numeroParsed = Integer.parseUnsignedInt(numero);
		int cepParsed = Integer.parseUnsignedInt(cep);

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date nascimentoDate = null;
		java.sql.Date data = null;
		nascimentoDate = formato.parse(nascimento);
		data = new java.sql.Date(nascimentoDate.getTime());

		String hashSenha = null;
		hashSenha = Hasher.hash(senha);

		PreparedStatement ps = con.prepareStatement("INSERT INTO `alunos` (`id`, `nome`, `email`, `senha`, `sexo`, `nascimento`, `logradouro`, `numero`, `complemento`, `bairro`, `cidade`, `cep`, `uf`, `foto`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		ps.setLong(1, idParsed);
		ps.setString(2, nome);
		ps.setString(3, email);
		ps.setString(4, hashSenha);
		ps.setString(5, sexo);
		ps.setDate(6, data);
		ps.setString(7, logradouro);
		ps.setInt(8, numeroParsed);
		ps.setString(9, complemento);
		ps.setString(10, bairro);
		ps.setString(11, cidade);
		ps.setInt(12, cepParsed);
		ps.setString(13, uf);
		ps.setString(14, foto);

		int sucesso = ps.executeUpdate();

		return sucesso != 0;

	}

	public boolean alterarAlunos(String id, String nome, String email, String senha, String sexo, String nascimento, String logradouro, String numero, String complemento, String bairro, String cidade, String cep, String uf, String foto) throws NumberFormatException, SQLException, NoSuchAlgorithmException, ParseException, InvalidKeySpecException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date nascimentoDate = null;
		java.sql.Date data = null;
		int adcs = 0;
		int numeroParsed = 0;
		int cepParsed = 0;
		long idParsed = Long.parseLong(id);
		int cont = 1;
		String hashSenha = null;
		hashSenha = Hasher.hash(senha);
		boolean[] pars = new boolean[13];
		for (int i = 0; i < 13; i++) {
			pars[i] = false;
		}
		String query = "UPDATE alunos SET";
		if (!"".equals(nome)) {
			query += " nome= ?";
			adcs++;
			pars[0] = true;
		}
		if (!"".equals(email)) {
			if (adcs > 0) {
				query += ",";
			}
			query += " email= ?";
			adcs++;
			pars[1] = true;
		}
		if (!"".equals(senha)) {
			if (adcs > 0) {
				query += ",";
			}
			query += " senha= ?";
			adcs++;
			pars[2] = true;
		}
		if (!"".equals(sexo)) {
			if (adcs > 0) {
				query += ",";
			}
			query += " sexo= ?";
			pars[3] = true;
		}
		if (!"".equals(nascimento)) {
			nascimentoDate = formato.parse(nascimento);
			data = new java.sql.Date(nascimentoDate.getTime());
			if (adcs > 0) {
				query += ",";
			}
			query += " nascimento= ?";
			pars[4] = true;
		}
		if (!"".equals(logradouro)) {
			if (adcs > 0) {
				query += ",";
			}
			query += " logradouro= ?";
			pars[5] = true;
		}
		if (!"".equals(numero)) {
			numeroParsed = Integer.parseUnsignedInt(numero);
			if (adcs > 0) {
				query += ",";
			}
			query += " numero= ?";
			pars[6] = true;
		}
		if (!"".equals(complemento)) {
			if (adcs > 0) {
				query += ",";
			}
			query += " complemento= ?";
			pars[7] = true;
		}
		if (!"".equals(bairro)) {
			if (adcs > 0) {
				query += ",";
			}
			query += " bairro= ?";
			pars[8] = true;
		}
		if (!"".equals(cidade)) {
			if (adcs > 0) {
				query += ",";
			}
			query += " cidade= ?";
			pars[9] = true;
		}
		if (!"".equals(cep)) {
			cepParsed = Integer.parseUnsignedInt(cep);
			if (adcs > 0) {
				query += ",";
			}
			query += " cep= ?";
			pars[10] = true;
		}
		if (!"".equals(uf)) {
			if (adcs > 0) {
				query += ",";
			}
			query += " uf= ?";
			pars[11] = true;
		}
		if (!"".equals(foto)) {
			if (adcs > 0) {
				query += ",";
			}
			query += " foto= ?";
			pars[12] = true;
		}

		query += " WHERE `id` = ?";

		PreparedStatement ps = con.prepareStatement(query);

		if (pars[0]) {
			ps.setString(cont, nome);
			cont++;
		}
		if (pars[1]) {
			ps.setString(cont, email);
			cont++;
		}

		if (pars[2]) {
			ps.setString(cont, hashSenha);
			cont++;
		}

		if (pars[3]) {
			ps.setString(cont, sexo);
			cont++;
		}

		if (pars[4]) {
			ps.setDate(cont, data);
			cont++;
		}

		if (pars[5]) {
			ps.setString(cont, logradouro);
			cont++;
		}
		if (pars[6]) {
			ps.setInt(cont, numeroParsed);
			cont++;
		}
		if (pars[7]) {
			ps.setString(cont, complemento);
			cont++;
		}
		if (pars[8]) {
			ps.setString(cont, bairro);
			cont++;
		}
		if (pars[9]) {
			ps.setString(cont, cidade);
			cont++;
		}
		if (pars[10]) {
			ps.setInt(cont, cepParsed);
			cont++;
		}
		if (pars[11]) {
			ps.setString(cont, uf);
			cont++;
		}
		if (pars[12]) {
			ps.setString(cont, foto);
			cont++;
		}
		ps.setLong(cont, idParsed);

		int sucesso = ps.executeUpdate();

		return sucesso != 0;

	}

	public boolean checarAutorizacaoADM(HttpServletRequest request, HttpServletResponse response) {
		DiarioAutenticador x = new DiarioAutenticador(request, response);

		return x.cargoLogado() == DiarioCargos.ADMIN;
	}

	public String listarAlunos() throws SQLException {
		String xml = "";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM `alunos`");
		while (rs.next()) {
			Long id = rs.getLong("id");
			String idStr = Long.toString(id);
			String zeros = "";
			for (int i = idStr.length(); i < 11; i++)
				zeros += "0";
			zeros += idStr;
			xml += viewConsulta.XMLAluno(zeros, rs.getString("nome"), rs.getString("email"));
		}
		xml = viewConsulta.XMLConsultaAlunos(xml);
		return xml;
	}

	public String consultarPorId(String id) throws SQLException {
		String xml = "";
		PreparedStatement ps = con.prepareStatement("SELECT * FROM `alunos` WHERE `id` = ?");
		long idParsed = Long.parseLong(id);
		ps.setLong(1, idParsed);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Long id2 = rs.getLong("id");
			String idStr = Long.toString(id2);
			String zeros = "";
			for (int i = idStr.length(); i < 11; i++)
				zeros += "0";
			zeros += idStr;
			xml += viewConsulta.XMLAlunoCompleto(zeros,
				rs.getString("nome"),
				rs.getString("email"),
				rs.getString("sexo"),
				rs.getDate("nascimento"),
				rs.getString("logradouro"),
				rs.getInt("numero"),
				rs.getString("complemento"),
				rs.getString("bairro"),
				rs.getString("cidade"),
				rs.getInt("cep"),
				rs.getString("uf"),
				rs.getString("foto"));
		}
		return xml;
	}

	public boolean checarAutorizacaoAluno(HttpServletRequest request, HttpServletResponse response, String id) {
		DiarioAutenticador x = new DiarioAutenticador(request, response);
		long idParsed = Long.parseLong(id);
		return x.cargoLogado() == DiarioCargos.ALUNO && (Long) x.idLogado() == idParsed;
	}

	public Boolean alterarSenha(String id, String senha) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		String hashSenha = null;
		long idParsed = Long.parseLong(id);
		hashSenha = Hasher.hash(senha);
		String query = "UPDATE alunos SET senha= ? WHERE id = ?";

		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, hashSenha);
		ps.setLong(2, idParsed);
		int sucesso = ps.executeUpdate();
		return sucesso != 0;
	}

	public Boolean alterarFoto(String id, String foto) throws SQLException {
		long idParsed = Long.parseLong(id);
		String query = "UPDATE alunos SET foto= ? WHERE id = ?";

		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, foto);
		ps.setLong(2, idParsed);
		int sucesso = ps.executeUpdate();
		return sucesso != 0;
	}

	public Boolean logarAluno(HttpServletRequest request, HttpServletResponse response, String id, String senha) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		long idParsed = Long.parseLong(id);
		DiarioAutenticador x = new DiarioAutenticador(request, response);
		String query = "SELECT * FROM `alunos` WHERE `id` = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, idParsed);
		ResultSet rs = ps.executeQuery();
		rs.next();
		if (Hasher.validar(senha, rs.getString("senha"))) {
			x.logar(idParsed, DiarioCargos.ALUNO, false);
			return true;
		}
		return false;

	}
	
	public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
            CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            (CPF.length() != 11))
            return(false);
          
        char dig10, dig11;
        int sm, i, r, num, peso;
          
        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {              
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere '0' no inteiro 0         
        // (48 eh a posicao de '0' na tabela ASCII)         
            num = (int)(CPF.charAt(i) - 48); 
            sm = sm + (num * peso);
            peso = peso - 1;
            }
          
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico
          
        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }
          
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);
          
        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
                } catch (InputMismatchException erro) {
                return(false);
            }
        }

}
