# Servidor

Este repositório é dedicado à parte de `back-end` da aplicação web desenvolvida. No caso, a tecnologia utilizada é a `Java EE`.

## Como _buildar_ o projeto

Utilizar a ferramenta de _build_ padrão da IDE utilizada. O servidor tem que ser o `Glassfish 4.1.x`.

## Sobre os bancos de dados

Os bancos de dados seguem um padrão semelhante ao documento do projeto.

Os "dumps" estão na pasta [db](db/) do projeto.  
A documentação pode ser acessada [aqui](docs/bd/README.md)

Caso você queira facilitar sua vida na hora de incluir os arquivos, execute o `prod.sql`, que é um compilado dos outros arquivos.

### Configurando o banco de dados

Por questões de praticidade, foi adicionada a possibilidade de criar um arquivo `bd.properties` no pacote `config` do projeto. Caso você queira uma referência, existe um arquivo `_bd.properties` no mesmo pacote.  
O arquivo `bd.properties` é ignorado na hora de dar `commit` nos seus arquivos, então pode ficar tranquilo.

## Instruções para IDEs e Editores recomendados

### Ferramentas necessárias

- `Oracle JDK 8+` ou `OpenJDK 8+`
- Servidor para desenvolvimento: `Glassfish 4.1.x`
- Uma IDE recomendada

Instruções para instalação: [aqui](https://duckduckgo.com/)

### IDE's recomendadas

- NetBeans 8.2+
- IntelliJ IDEA

### Plugins e Configurações

Recomendamos, para sua facilidade, instalar o plugin `EditorConfig`, que irá padronizar o seu projeto automaticamente, baseado no arquivo `.editorconfig` em `app`. [Tutorial para instalação no NetBeans](https://inf2-2019.github.io/help/editorconfig/).

São usados:

- Indentação por `TAB`
- Charset `UTF-8`
- Fim de linha `LF`
- Uma linha em branco no fim de cada arquivo
- Remoção automática de espaços no fim da linha

#### `.editorconfig`

```ini
root = true

[*]
indent_style = tab
charset = utf-8
trim_trailing_whitespace = true
end_of_line = lf
insert_final_newline = true
```

## Bibliotecas

As bibliotecas usadas atualmente são:

- `MySQL Connector`
- `Mail`
- `Commons Email`
- `Activation`

### Caso você esteja tendo problemas com as bibliotecas

Geralmente essas bibliotecas costumam estar incluídas no projeto "automaticamente", caso você esteja tendo problemas com elas, tente:

1. Clique com o botão direito em "Bibliotecas" e selecione "Adicionar JAR/Pasta...", conforme [a imagem](http://prntscr.com/puoihq)
2. Localize a pasta `app/web/WEB-INF/lib`
3. Clique em abrir

## Documentações e links úteis

A documentação está disponível na pasta `docs`.

`Glassfish 4.1.2-` download: [aqui](https://javaee.github.io/glassfish/download)
