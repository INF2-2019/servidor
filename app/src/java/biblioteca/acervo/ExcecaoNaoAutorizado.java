package biblioteca.acervo;

public class ExcecaoNaoAutorizado extends Exception {

    public ExcecaoNaoAutorizado(){
        super();
    }

    public ExcecaoNaoAutorizado(String mensagem) {
        super(mensagem);
    }

}
