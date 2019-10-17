# Servidor

Esse repositório é dedicado a parte de `back-end` da aplicação a ser der desenvolvida, no caso a tecnologia utilizada será a `Java EE`.

## Aviso

Esse repositório tem afazeres globais, caso queira ajudar verifique o [`TO-DO`](TO-DO.md).

## Instruções para IDEs e Editores recomendados

### Ferramentas necessárias

- `Oracle JDK 8+` ou `OpenJDK 8+`
- Servidor para desenvolvimento: `Glassfish 5.x`
- Uma IDE recomendada

Instruções para instalação: [aqui](https://duckduckgo.com/)

### IDE's recomendadas

- NetBeans 8+
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
indent_size = 4
charset = utf-8
trim_trailing_whitespace = true
end_of_line = lf
insert_final_newline = true
```

## Como _buildar_ o projeto

Utilizar a ferramenta de _build_ padrão da IDE utilizada. O servidor tem que ser o `Glassfish 5`.

## Sobre os bancos de dados

Os bancos de dados seguem um padrão semelhante ao documento do projeto, futuramente serão adicionados os `dumps` e um esquema

## Bibliotecas

As bibliotecas usadas atualmente são:

- `JSTL`

## Documentações e links úteis

A documentação estará disponível na pasta `docs`.

`Glassfish 5.0-` download: [aqui](https://javaee.github.io/glassfish/download)
`Glassfish 5.1+` download: [aqui](https://eclipse-ee4j.github.io/glassfish/download)
`Glassfish 5.1+` repo: [aqui](https://github.com/eclipse-ee4j/glassfish/tree/5.1.0)
