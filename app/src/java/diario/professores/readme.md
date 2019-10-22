# Manipulação da tabela 'Professores'

## Estrutura da tabela

|     Posição    |  Nome da Coluna |     Tipo da Coluna    |    Chave    |
| -------------- | --------------- | --------------------- | ----------- |
| 1              | id              | int(11)               | Primária    |
| 2              | id-depto        | int(11)               | `-`         |
| 3              | nome            | varchar(255)          | `-`         |
| 4              | senha           | varchar(255)          | `-`         |
| 5              | email           | varchar(255)          | Única       |
| 6              | titulacao       | enum('M','D','E','G') | `-`         |

----------------------------------------

## Estrutura do XML de resposta

```xml

<root>
	<info>
		<erro>true/false</erro>
		<mensagem></mensagem>
	</info>
	<professores>
		<professor>
			<id></id>
			<id-depto></id-depto>
			<nome></nome>
			<senha></senha>
			<email></email>
			<titulacao></tirulacao>
		</professor>

		<!-- ... -->

	</professores>
</root>

```

--------------------------------

## Parâmetros em cada operação

- **Inserção:** Todos os parâmetros devem ser preenchidos

- **Atualização:** Todos os parâmetros devem ser preenchidos

- **Remoção:** Apenas o parâmetro 'id' é obrigatório, outros serão ignorados

- **Consulta:** Sem parâmetros, sempre retorna todos os professores
