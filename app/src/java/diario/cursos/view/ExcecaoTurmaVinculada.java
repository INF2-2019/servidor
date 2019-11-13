package diario.cursos.view;

public class ExcecaoTurmaVinculada extends Exception {
	public ExcecaoTurmaVinculada() {
		super("Não foi possível deletar o curso pois existem turmas vinculadas.");
	}

	public ExcecaoTurmaVinculada(String mensagem) {
		super(mensagem);
	}

}
