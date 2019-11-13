# Manutenção de Disciplinas

com a API é possivel realizar as operações de  inserir,deletar,atualizar,consultar  na tabela de Disciplinas do Diario Acadêmico

## Como usar

é possivel com o endereço abaixo acessar as funcionalidades da API

`https://localhost:8080/app/diario/disciplinas/{funcionalidade}`

em que `{funcionalidade}` é o recurso a ser usado. Ou seja, pode ser qualquer um dos seguintes: `consultar`, `deletar`, `inserir`, `atualizar` ou `consultarporid`.

## Estrutura básica das respostas

| Operação | Método | Tipo de resposta |
|----------|--------|------------------|
| Consultar | `GET` | XML de cursos |
| ConsultarPorId | `GET` | XML de sucesso/erro |
| Deletar | `GET` | XML de sucesso/erro |
| Inserir | `POST` | XML de sucesso/erro |
| Atualizar | `POST` | XML de sucesso/erro |


*Estrutura do XML de cursos:*

```xml
<disciplinas>
    <disciplina>
        <id></id>
        <id-turmas></id-turmas>
        <nome></nome>
        <carga-horaria-min></carga-horaria-min>
    </disciplina>
</disciplinas>
```

*Estrutura do XML de erro:*

```xml
<erro>
    <informacao></informacao>
</erro>
```

*Estrutura do XML de sucesso:*

```xml
<sucesso>
    <informacao></informacao>
</sucesso>
```


## Utilização

### Parâmetros

Para as operações que necessitarem de parâmetros, o nome de cada um representa uma coluna no banco de dados, que *não necessariamente* é o mesmo. A tabela a seguir representa as "traduções" feitas:

|Nome da coluna no BD | Nome do parâmetro|
|-------------------- | -----------------|
| id | id |
| id-depto | turma |
| nome | nome |
| caraga-horaria-min | horas |


### Consultar

A operação tem três possibilidades de requisição, levando parâmetros diferentes.

| Parâmetros da Requisição | Resposta do servidor |
|--------------------------|----------------------|
| Sem parâmetros | Todos as Disciplinas |
| Outros parâmetros para filtragem | Disciplinas que se encaixam na filtragem |

Um exemplo de requisição para o servidor, filtrando por `nome` e `id-turmas` seria:

`https://localhost:8080/app/diario/disciplinas/consultar?turma=5&nome=ALP`

### Consultar por ID

A operação de consulta por id deve ser realizada somente com o id da disciplina a ser consultada

Um exemplo de requisição seria:

`https://localhost:8080/app/diario/disciplinas/consultarporid?turma=5&nome=ALP`
### Deletar

A operação deve receber *um* parâmetro apenas, deve receber somente o `id` da Disciplina a ser deletada.

Um exemplo de requisição para o servidor seria:

`https://localhost:8080/app/diario/disciplinas/deletar?id=2`

### Inserir

A operação deve receber *obrigatóriamente* todos os dados necessários para a criação de uma nova disciplina no BD, ou seja, deve receber os seguintes parâmetros da requisição: `turma`, `nome`, `horas`  (como o id é gerado automaticamente é desnecessario na requisição).

### Atualizar

A operação deve *obrigatóriamente* receber o `id`  a ser atualizado. Além disso, deve-se passar *ao menos um parâmetro* com o novo valor. Um exemplo de estrutura do envio da requisição `POST` seria:

| Parâmetro | Valor |
|----------|------|
| `id` | 12 |
| `turma` | 7 |
| `nome` | Linguagem de Programção |

Assim, se atualizaria a disciplina de `id` = 12, alterando seu `id-depto` para 5 e seu `nome` para *"Linguagem de Programção"*