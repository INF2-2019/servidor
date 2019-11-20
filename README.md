# Servidor

Esse repositório é dedicado a parte de `back-end` da aplicação a ser der desenvolvida, no caso a tecnologia utilizada será a `Java EE`.

## A estrutura do projeto

Leia sobre a estrutura do projeto [aqui](docs/estrutura.md).

## Instruções para IDEs e Editores recomendados

### Ferramentas necessárias

- `Oracle JDK 8+` ou `OpenJDK 8+`
- Servidor para desenvolvimento: `Glassfish 4`
- Uma IDE recomendada

Instruções para instalação: [aqui](https://duckduckgo.com/)

### IDE's recomendadas

- NetBeans 8.2+
- IntelliJ IDEA

### Plugins e Configurações

Recomendamos, para sua facilidade, instalar o plugin `EditorConfig` que ira padronizar o seu projeto "automaticamente" baseado no arquivo `.editorconfig` em `app`. [Tutorial para instalação no NetBeans](https://inf2-2019.github.io/help/editorconfig/).

Serão usados:

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

## Como _buildar_ o projeto

Utilizar a ferramenta de _build_ padrão da IDE utilizada. O servidor tem que ser o `Glassfish 4`.

## Sobre os bancos de dados

Os bancos de dados seguem um padrão semelhante ao documento do projeto.

Os "dumps" estão na pasta [db](db/) do projeto.  
A documentação pode ser acessada [aqui](docs/bd/README.md)

## Bibliotecas

As bibliotecas usadas atualmente são:

- `MySQL Connector`
- `Mail`
- `Commons Email`
- `Activation`

As três últimas bibliotecas precisam ser incluídas no projeto (caso já não estejam automaticamente), para isso siga os passos:

1. Clique com o botão direito em "Bibliotecas" e selecione "Adicionar JAR/Pasta..." conforme [a imagem](http://prntscr.com/puoihq)
2. Localize a pasta `app/web/WEB-INF/lib`
3. Segure CTRL e clique sobre `mail`, `commons-email-1.3` e `activation`
4. Clique em abrir

## Documentações e links úteis

A documentação estará disponível na pasta `docs`.

`Glassfish 4.1.2-` download: [aqui](https://javaee.github.io/glassfish/download)
