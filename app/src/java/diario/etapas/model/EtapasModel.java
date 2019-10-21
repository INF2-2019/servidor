package model.diario;

import model.Model;

public class EtapasModel extends Model {

    private int id, Ano;
    private double Valor;

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

    public void setId(int id) {
	this.id = id;
    }

    public void setAno(int ano) {
	this.Ano = ano;
    }

    public void setValor(double valor) {
	this.Valor = valor;
    }

    public int getId() {
	return id;
    }

    public int getAno() {
	return Ano;
    }

    public double getValor() {
	return Valor;
    }
}
