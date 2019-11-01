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

## Estrutura das funções de manipulação

##### Inserir

A funcionalidade *inserir* requer a passagem de todos os parâmetros. Retorna um XML com uma mensagem de sucesso ou erro.

##### Alterar

A funcionalidade *alterar* requer a passagem de todos os parâmetros. Retorna um XML com uma mensagem de sucesso ou erro.

##### Excluir

A funcionalidade *excluir* requer a passagem apenas do parâmetro `id` (SIAPE). Retorna um XML com uma mensagem de sucesso ou erro.

##### Consultar

A funcionalidade *consultar* não requer passagem de parâmetros. Retorna um XML com os dados dos professores ou um XML de erro.

## Estrutura dos XML de resposta

### XML de erro
```xml
<erro>
    <mensagem></mensagem>
 </erro>
 ```

 ### XML de sucesso
 ```xml
<sucesso>
    <mensagem></mensagem>
 </sucesso>
 ```

 ### XML de consulta
 ```xml
<professor>
    <professor>
        <id></id>
        <id-depto></id-depto>
        <nome></nome>
        <senha></senha>
        <email></email>
        <titulacao></titulacao>
    </professor>
	<!-- ... -->
</professores>
```
