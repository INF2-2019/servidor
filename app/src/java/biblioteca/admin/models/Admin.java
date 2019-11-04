package biblioteca.admin.models;

public class Admin {
	public final static int ID_INDEFINIDO = -1;

	private int id;
	private String nome, usuario, email, hashSenha;

	public Admin(String nome, String usuario, String email, String hashSenha) {
		this(ID_INDEFINIDO, nome, usuario, email, hashSenha);
	}

	public Admin(int id, String nome, String usuario, String email) {
		this(id, nome, usuario, email, null);
	}

	public Admin(String nome, String usuario, String email) {
		this(ID_INDEFINIDO, nome, usuario, email, null);
	}

	public Admin(int id, String nome, String usuario, String email, String hashSenha) {
		setId(id);
		setNome(nome);
		setUsuario(usuario);
		setEmail(email);
		setHashSenha(hashSenha);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHashSenha() {
		return hashSenha;
	}

	public void setHashSenha(String hashSenha) {
		this.hashSenha = hashSenha;
	}
}
