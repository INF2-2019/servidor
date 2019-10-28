# Manutenção de cursos

Funcionalidade do servidor para *inserir, deletar, alterar e consultar* a tabela de cursos, do banco de dados do sistema de diário acadêmico.

## Como usar

Com o servidor em execução, é possível acessar o seguinte endereço:

`https://localhost:8080/app/diario/cursos/{funcionalidade}`

em que `{funcionalidade}` é o recurso a ser usado. Ou seja, pode ser qualquer um dos seguintes: `consultar`, `deletar`, `inserir` ou `atualizar`.

## Estrutura básica das respostas

| Operação | Método | Tipo de resposta |
|----------|--------|------------------|
| Consultar | `GET` | XML de cursos |
| Deletar | `GET` | XML de sucesso/erro |
| Inserir | `POST` | XML de sucesso/erro |
| Atualizar | `POST` | XML de sucesso/erro |

*Estrutura do XML de cursos:*

```xml
<cursos>
    <curso>
        <id></id>
        <id-depto></id-depto>
        <nome></nome>
        <horas-total></horas-total>
        <modalidade></modalidade>
    </curso>
</cursos>
```

*Estrutura do XML de erro:*

```xml
<erro>
    <mensagem></mensagem>
</erro>
```

*Estrutura do XML de sucesso:*

```xml
<sucesso>
    <mensagem></mensagem>
</sucesso>
```


## Utilização

### Parâmetros

Para as operações que necessitarem de parâmetros, o nome de cada um representa uma coluna no banco de dados, que *não necessariamente* é o mesmo. A tabela a seguir representa as "traduções" feitas:

|Nome da coluna no BD | Nome do parâmetro|
|-------------------- | -----------------|
| id | id |
| id-depto | departamento |
| nome | nome |
| horas-total | horas |
| modalidade | modalidade |

### Consultar

A operação tem três possibilidades de requisição, levando parâmetros diferentes.

| Parâmetros da Requisição | Resposta do servidor |
|--------------------------|----------------------|
| Sem parâmetros | Todos os cursos |
| `id` | O curso com esse `id` |
| Outros parâmetros para filtragem | Cursos que se encaixam na filtragem |

Um exemplo de requisição para o servidor, filtrando por `id-depto` e `horas-total` seria:

`https://localhost:8080/app/diario/cursos/consultar?departamento=5&horas=300`

### Deletar

A operação deve receber *um* parâmetro apenas, deve receber somente o `id` do curso a ser deletado.

Um exemplo de requisição para o servidor seria:

`https://localhost:8080/app/diario/cursos/deletar?id=2`

### Inserir

A operação deve receber *obrigatóriamente* todos os dados necessários para a criação de um novo curso no BD, ou seja, deve receber os seguintes parâmetros da requisição: `departamento`, `nome`, `horas` e `modalidade` (o `id` é desnecessário, visto que ele é gerado automaticamente pelo banco de dados).

### Atualizar

A operação deve *obrigatóriamente* receber o `id` do curso a ser atualizado. Além disso, deve-se passar *ao menos um parâmetro* com o novo valor. Um exemplo de estrutura do envio da requisição `POST` seria:

| Parâmetro | Valor |
|----------|------|
| `id` | 2 |
| `departamento` | 5 |
| `nome` | Exemplo |

Assim, se atualizaria o curso de `id` = 2, alterando seu `id-depto` para 5 e seu `nome` para *"Exemplo"*
