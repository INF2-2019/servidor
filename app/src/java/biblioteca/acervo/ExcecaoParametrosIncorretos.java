package biblioteca.acervo;

public class ExcecaoParametrosIncorretos extends Exception {

    public ExcecaoParametrosIncorretos(){
        super();
    }

    public ExcecaoParametrosIncorretos(String mensagem) {
        super(mensagem);
    }

}
