# Manutenção de cursos

Funcionalidade do servidor para *inserir, deletar, alterar e consultar a tabela de cursos*, do banco de dados do sistema de diário acadêmico.

## Como usar

Com o servidor em execução, é possível acessar o seguinte endereço:

`https://localhost:8080/app/diario/cursos/{funcionalidade}`

em que `{funcionalidade}` é o recurso a ser usado. Ou seja, pode ser qualquer um dos seguintes: `consultar`, `deletar`, `inserir` ou `atualizar`.

## Parâmetros

Para as operações que necessitarem de parâmetros, o nome de cada um representa uma coluna no banco de dados, que *não necessariamente* é o mesmo. A tabela a seguir representa as "traduções" feitas:

|Nome da coluna no BD | Nome do parâmetro|
|-------------------- | -----------------|
|id-depto | departamento|
