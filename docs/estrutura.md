# A estrutura do projeto

_Isso pode parecer exagerado, pode até estar, caso ache isso debata com alguém._

A ideia dessa estrutura é isolar as responsabilidades do código da forma mais ótima possível, deixando fácil e previsível o ciclo de formação de uma resposta.

## A estrutura que você deve se deparar

A estrutura que você deve ver é fundamentalmente essa.

```
.
├── src
│   ├── conf
│   │   └── MANIFEST.MF
│   └── java
│       ├── controller
│       │   ├── biblioteca
│       │   └── diario
│       ├── model
│       │   ├── biblioteca
│       │   └── diario
│       ├── repository
│       │   ├── biblioteca
│       │   └── diario
│       ├── utils
│       │   └── ConnectionFactory.java
│       └── view
│           ├── biblioteca
│           └── diario
└── web
    └── WEB-INF
        └── lib
            └── mysql-connector-java-8.0.18.jar
```

### Os elementos

Essas estrutura é bem semelhante a um padrão muito utilizado que é o MVC (Model, View, Controller). A ideia dele é separar o servidor em 3 partes essenciais como responsabilidades bem definidas: os modelos, que representam os dados, regras de negócio, lógica; as views que são as representações da informação como tabelas, XMLs, HTMLs; os controladores que mediam tudo repassando as operações adequadas para as demais partes.

No nosso caso teremos também: os repositórios que são classes especializadas em acessar o banco de dados a fim de salvar ou obter modelos e as utilidades que são classes que não se classificam muito bem nessas categorias mas que podem ser usadas nas demais.

Notem que em todas, exceto na utils, são separadas as partes de diário e biblioteca.

#### `controller`

Nesse pacote ficam os controladores da aplicação.

Esses controladores são geralmente servlets que gerenciam a requisição e invoca as demais partes de acordo com a necessidade. Esses servlets devem estar bem distribuídos, sendo separados em sub pastas de acordo com a área de cada um. Por exemplo, um controlador responsável pela inserção de um aluno no diario teria o nome completo (pacote + nome da classe): `controller.diario.aluno.Insere` e seria responsável somente pela inserção de alunos.

#### `model`

Nesse pacote ficam os modelos da aplicação.

Esses modelos são a estrutura dos dados da aplicação, neles são isolados os dados, operações e regras de negócio de cada item. Normalmente são semelhante ao esquema de uma tabela no banco de dados. Um exemplo de modelo de aluno teria o nome completo (pacote + nome da classe): `model.diario.Aluno`. Nele teriam todos os dados que um Aluno possui e todas as operações que devem ser realizadas sobre um Aluno.

#### `view`

Nesse pacote ficam todas as visualizações da aplicação.

Visualizações são a forma de representar dados específicos a serem enviados ao cliente. Uma view irá, no nosso caso, produzir um XML de acordo com a estrutura nela definida e os dados passados pelo controlador. Um exemplo de uma view de alunos teria o nome completo (pacote + nome da classe): `view.diario.Aluno`. E teria métodos para gerar um XML adequado para as necessidades do controlador. Por exemplo, gerar um xml com uma lista de todos Alunos que foram recebidos como parâmetro.

#### `repository`

Nesse pacote ficam todas as classes de operações com o banco de dados da aplicação.

Um repositório terá métodos para executar operações no banco de dados que estejam relacionados com um modelo "vinculado". Por exemplo um repositório de alunos teria métodos para adicionar, obter, editar e deletar alunos no banco de dados. Um exemplo de um repositório de alunos teria o nome completo (pacote + nome da classe): `repository.diario.AlunosRepository`.

#### `utils`

Pacote de utilidades

É um pacote onde ficam classes utilitárias que não se encaixam em nenhum categoria bem definida que não utilidades, os serviços, por exemplo, ficarão nela devido a sua escassez.
