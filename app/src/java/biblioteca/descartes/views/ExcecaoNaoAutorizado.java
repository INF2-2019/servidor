package biblioteca.descartes.views;

public class ExcecaoNaoAutorizado extends ExcecaoPadrao {

    public ExcecaoNaoAutorizado(String causa, String mensagem){
        super(causa,mensagem);
    }

    public ExcecaoNaoAutorizado(String mensagem) {
        super(mensagem);
    }

}
