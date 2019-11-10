package diario.departamentos.service;

import diario.departamentos.controller.ParametrosIncorretosException;

public class DepartamentoValidation {
	
	public static void validaNome(String nome) throws ParametrosIncorretosException {
		if(nome == null)
			throw new ParametrosIncorretosException("Informe o nome do departamento");
		else if(nome.equals(""))
			throw new ParametrosIncorretosException("O nome do departamento não pode ser vazio");
		else if(nome.length() >= 128)
			throw new ParametrosIncorretosException("O nome do departamento não pode exceder 127 caracteres");
	}
	
}
