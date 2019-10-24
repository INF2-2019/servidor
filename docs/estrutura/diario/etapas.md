# Manutenção de Etapas

Funcionalidade que, por meio de servlets Java, permite inserir, alterar, excluir e consultar a tabela *etapas* do banco de dados.

## Estrutura da tabela *Etapas*

| Posição | Nome |    Tipo     |
|---------|------|-------------|
|1        |id    |INT(11)      |
|2        |ano   |INT(11)      |
|3        |valor |DECIMAL(5, 2)|

## Estrutura das funções de manipulação

### Inserir

A funcionalidade *inserir* requer a passagem dos parâmentros `ano` e `valor`. Retorna um XML com uma mensagem de sucesso ou erro.

### Alterar

A funcionalidade *alterar* requer a passagem do parâmetro `id`, sempre, e pelo menos um dos dois outros parâmetros `ano` ou `valor`. Retorna um XML com uma mensagem de sucesso ou erro.

### Excluir

A funcionalidade *excluir* requer a passagem apenas do parâmetro `id`. Retorna um XML com uma mensagem de sucesso ou erro.

### Consultar

A funcionalidade *consultar* não requer a passagem obrigatória de parâmetros, mas aceita quaisquer combinações de `id`, `ano` ou `valor`. Retorna um XML com as informações daqueles itens da tabela que satisfaçam todos os parâmetros.

## Estrutura dos XML de resposta

### XML de erro
```xml
<erro>
    <mensagem>{conteúdo da mensagem}</mensagem>
 </erro>
 ```

 ### XML de sucesso
 ```xml
<sucesso>
    <mensagem>{conteúdo da mensagem}</mensagem>
 </sucesso>
 ```

 ### XML de consulta
 ```xml
<etapas>
    <etapa>
        <id>1</id>
        <ano></ano>
        <valor></valor>
    </etapa>
    <etapa>
        <id>2</id>
        <ano></ano>
        <valor></valor>
    </etapa>
</etapas>
```
