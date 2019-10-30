package diario.alunos.repository;

import diario.campi.view.viewConsulta;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import utils.Hasher;
import static utils.Hasher.hash;
import utils.autenticador.DiarioAutenticador;
import utils.autenticador.DiarioCargos;

/**
 *
 * @author User
 */
public class AlunosRepository {
    private Connection con;
    
    public AlunosRepository(Connection con){
        this.con = con;
    }
    
    
    public String deletarAlunos(String id) throws SQLException{
		PreparedStatement ps = con.prepareStatement("SELECT * FROM `matriculas` WHERE `id-alunos` = ?");
		int idParsed = Integer.parseUnsignedInt(id);
		ps.setInt(1, idParsed);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) 
			return "mat";
        String sql;
        sql = "DELETE FROM `alunos` WHERE `id` = ?";
        
        PreparedStatement stat =  con.prepareStatement(sql);
        stat.setInt(1, idParsed);
       
        int sucesso = stat.executeUpdate();
        // Se deletou algo, retorna true, senão retorna false
		if (sucesso != 0)
			return "sucesso";
		return "erro";
    }
    
    public boolean inserirAlunos(String id, String nome, String email, String senha, String sexo, String nascimento, String logradouro, String numero, String complemento, String bairro, String cidade, String cep, String uf, String foto  ) throws NumberFormatException, SQLException, NoSuchAlgorithmException, ParseException, InvalidKeySpecException {
		

		int idParsed = Integer.parseUnsignedInt(id);
		int numeroParsed = Integer.parseUnsignedInt(numero);
		int cepParsed = Integer.parseUnsignedInt(cep);
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		 Date nascimentoDate = null;
		 java.sql.Date data = null;
		nascimentoDate = formato.parse(nascimento);
		data = new java.sql.Date(nascimentoDate.getTime());
		
		String hashSenha = null;
		hashSenha = hash(senha);
		
		PreparedStatement ps = con.prepareStatement("INSERT INTO `alunos` (`id`, `nome`, `email`, `senha`, `sexo`, `nascimento`, `logradouro`, `numero`, `complemento`, `bairro`, `cidade`, `cep`, `uf`, `foto`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		ps.setInt(1, idParsed);
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
    
    public boolean alterarAlunos(String id, String nome, String email, String senha, String sexo, String nascimento, String logradouro, String numero, String complemento, String bairro, String cidade, String cep, String uf, String foto  ) throws NumberFormatException, SQLException, NoSuchAlgorithmException, ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		Date nascimentoDate = null;
		java.sql.Date data = null;
		nascimentoDate = formato.parse(nascimento);
		data = new java.sql.Date(nascimentoDate.getTime());
		int adcs = 0;
		String query = "UPDATE alunos SET";
		if (!"".equals(nome)) {
			query += " nome='"+nome+"'";
			adcs++;
		}
		if (!"".equals(email)) {
		   if (adcs > 0) 
			query += ",";
		   query += " email='"+email+"'";
		  adcs++;
		}
		if (!"".equals(sexo)) {
		   if (adcs > 0) 
			query += ",";
		   query += " sexo='"+sexo+"'";
		}
		if (!"".equals(nascimento)) {
		   if (adcs > 0) 
			query += ",";
		   query += " nascimento='"+data+"'";
		}
		if (!"".equals(logradouro)) {
		   if (adcs > 0) 
			query += ",";
		   query += " logradouro='"+logradouro+"'";
		}
		if (!"".equals(numero)) {
		   if (adcs > 0) 
			query += ",";
		   query += " numero='"+Integer.parseUnsignedInt(numero)+"'";
		}
		if (!"".equals(complemento)) {
		   if (adcs > 0) 
			query += ",";
		   query += " complemento='"+complemento+"'";
		}
		if (!"".equals(bairro)) {
		   if (adcs > 0) 
			query += ",";
		   query += " bairro='"+bairro+"'";
		}
		if (!"".equals(cidade)) {
		   if (adcs > 0) 
			query += ",";
		   query += " cidade='"+cidade+"'";
		}
		if (!"".equals(cep)) {
		   if (adcs > 0) 
			query += ",";
		   query += " numero='"+Integer.parseUnsignedInt(cep)+"'";
		}
		if (!"".equals(uf)) {
		   if (adcs > 0) 
			query += ",";
		   query += " uf='"+uf+"'";
		}
		if (!"".equals(foto)) {
		   if (adcs > 0) 
			query += ",";
		   query += " foto='"+foto+"'";
		}
		

		query += " WHERE `id` = "+id+"";

		PreparedStatement ps = con.prepareStatement(query);    

		int sucesso = ps.executeUpdate();

return sucesso != 0;

	}
	
	public boolean checarAutorizacaoADM(HttpServletRequest request, HttpServletResponse response) {
		DiarioAutenticador x = new DiarioAutenticador(request, response);
		
		return x.cargoLogado() == DiarioCargos.ADMIN;
	}
	
	public String listarAlunos() throws SQLException {
		String xml  = "";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM `alunos`");

		while(rs.next()) {
			xml += viewConsulta.XMLAluno(rs.getInt("id"), rs.getString("nome"), rs.getString("email"));
		}
		xml = viewConsulta.XMLConsultaAlunos(xml);
		return xml;
	}
	
	public String consultarPorId(String id) throws SQLException {
		String xml = "";
		PreparedStatement ps = con.prepareStatement("SELECT * FROM `alunos` WHERE `id` = ?");
		int idParsed = Integer.parseUnsignedInt(id);
		ps.setInt(1, idParsed);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			xml += viewConsulta.XMLAlunoCompleto(rs.getInt("id"), rs.getString("nome"),  rs.getString("email"), rs.getString("sexo"), rs.getDate("nascimento"), rs.getString("logradouro"), rs.getInt("numero"), rs.getString("complemento"), rs.getString("bairro"),rs.getString("cidade"), rs.getInt("cep"),rs.getString("uf"), rs.getString("foto"));
		}
		return xml;
	}
        
        public boolean checarAutorizacaoAluno(HttpServletRequest request, HttpServletResponse response, String id) {
		DiarioAutenticador x = new DiarioAutenticador(request, response);
		int idParsed= Integer.parseUnsignedInt(id);
		return x.cargoLogado() == DiarioCargos.ALUNO && x.idLogado() == idParsed;
	}
        
        public Boolean alterarSenha(String id, String senha) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
            String hashSenha = null;
            int idParsed = Integer.parseUnsignedInt(id);
            hashSenha = hash(senha);
            String query = "UPDATE alunos SET senha= ? WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, hashSenha);
            ps.setInt(2, idParsed);
            int sucesso = ps.executeUpdate();
            return sucesso!=0;
        }
        
        public Boolean alterarFoto(String id, String foto) throws SQLException {
            int idParsed = Integer.parseUnsignedInt(id);            
            String query = "UPDATE alunos SET foto= ? WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, foto);
            ps.setInt(2, idParsed);
            int sucesso = ps.executeUpdate();
            return sucesso!=0;
        }
		
		public Boolean logarAluno(HttpServletRequest request, HttpServletResponse response, String id, String senha) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
			int idParsed = Integer.parseUnsignedInt(id);
			DiarioAutenticador x = new DiarioAutenticador(request, response);
            String query = "SELECT * FROM `alunos` WHERE `id` = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idParsed);
			ResultSet rs = ps.executeQuery();
			rs.next();
			if(Hasher.validar(senha, rs.getString("senha"))) {
				x.logar(idParsed, DiarioCargos.ALUNO, false);
				return true;
			}
			return false;
				
		}
   
}