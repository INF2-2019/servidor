package diario.alunos.repository;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import static utils.Hasher.hash;

/**
 *
 * @author User
 */
public class AlunosRepository {
    private Connection con;
    
    public AlunosRepository(Connection con){
        this.con = con;
    }
    
    
    public boolean deletarAlunos(String id) throws SQLException{
        String sql;
        int idParsed = Integer.parseUnsignedInt(id);
        sql = "DELETE FROM `alunos` WHERE `id` = ?";
        
        PreparedStatement stat =  con.prepareStatement(sql);
        stat.setInt(1, idParsed);
       
        int sucesso = stat.executeUpdate();
        // Se deletou algo, retorna true, senão retorna false
        return sucesso != 0;
    }
    
    public boolean inserirAlunos(String id, String nome, String email, String senha, String sexo, String nascimento, String logradouro, String numero, String complemento, String bairro, String cidade, String cep, String uf, String foto  ) throws NumberFormatException, SQLException, NoSuchAlgorithmException {
		

		int idParsed = Integer.parseUnsignedInt(id);
		int numeroParsed = Integer.parseUnsignedInt(numero);
		int cepParsed = Integer.parseUnsignedInt(cep);
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		 Date nascimentoDate = null;
		 java.sql.Date data = null;
		try {
			nascimentoDate = formato.parse(nascimento);
			data = new java.sql.Date(nascimentoDate.getTime());
		} catch (ParseException ex) {
			Logger.getLogger(AlunosRepository.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		String hashSenha = null;
		try {
			hashSenha = hash(senha);
		} catch (InvalidKeySpecException ex) {
			Logger.getLogger(AlunosRepository.class.getName()).log(Level.SEVERE, null, ex);
		}
		
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
   
}