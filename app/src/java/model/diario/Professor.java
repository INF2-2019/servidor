package model.diario;

/**
 * <h1>Professor</h1>
 * Classe que representa um professor dentro da aplicação, com todos os dados
 * que identificam um professor e seus dados de login: email e senha.
 *
 * @author Jônata Novais Cirqueira
 * @author Nikolas Victor Mota
 * @version 1.0
 * @since 2019-10-20
 */
public class Professor {

    private final static int INVALID_ID = -1;

    // SIAPE: número composto por 9 dígitos, que identifica os servidores ativos e aposentados no Serviço Público Federal.
    private int idSiape;
    private int idDepto;
    private String nome, senha, email;

    // M-Mestrado, D-Doutorado, E-Espacialização, G-Graduação
    private char titulacao;

    public Professor(int idDepto, String nome, String senha, String email, char titulacao) {
	this.idSiape = INVALID_ID;
	this.idDepto = idDepto;
	this.nome = nome;
	this.senha = senha;
	this.email = email;
	this.titulacao = titulacao;
    }

    public Professor(int idSiape, int idDepto, String nome, String senha, String email, char titulacao) {
	this.idSiape = idSiape;
	this.idDepto = idDepto;
	this.nome = nome;
	this.senha = senha;
	this.email = email;
	this.titulacao = titulacao;
    }

    public int getIdSiape() {
	return idSiape;
    }

    public void setIdSiape(int idSiape) {
	this.idSiape = idSiape;
    }

    public int getIdDepto() {
	return idDepto;
    }

    public void setIdDepto(int idDepto) {
	this.idDepto = idDepto;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getSenha() {
	return senha;
    }

    public void setSenha(String senha) {
	this.senha = senha;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public char getTitulacao() {
	return titulacao;
    }

    public void setTitulacao(char titulacao) {
	this.titulacao = titulacao;
    }

}
