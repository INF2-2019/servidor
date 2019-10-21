package diario.etapas.model;

import diario.etapas.Model;
import java.util.Map;

public class EtapasModel extends Model {

    private int id, ano;
    private double valor;

    public EtapasModel(int ano, double valor) {
	// Para evitar repetição, reaproveitamento do construtor completo
	this(ID_INDEFINIDO, ano, valor);
    }

    public EtapasModel(int id, int ano, double valor) {
	// Usar setters para validar os dados de maneira isolada
	this.setId(id);
	this.setAno(ano);
	this.setValor(valor);
    }
    
    public Object[] retornarValoresRestantes(Map<String, String> parametros) {
		Object[] retorno = new Object[3];
		retorno[0] = id;

		if(!parametros.containsKey("ano"))
			retorno[1] = ano;
		else
			retorno[1] = parametros.get("ano");

		if(!parametros.containsKey("valor"))
			retorno[2] = valor;
		else
			retorno[2] = parametros.get("valor");

		return retorno;
	}

    public void setId(int id) {
	this.id = id;
    }

    public void setAno(int ano) {
	this.ano = ano;
    }

    public void setValor(double valor) {
	this.valor = valor;
    }

    public int getId() {
	return id;
    }

    public int getAno() {
	return ano;
    }

    public double getValor() {
	return valor;
    }
}
