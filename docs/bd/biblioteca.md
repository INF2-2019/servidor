# `biblioteca`

Nesse arquivo estão a estrutura do banco de dados do sistema bibliotecário.

Traduções:

- COLUMN_NAME: Nome da Coluna
- ORDINAL_POSITION: Posição da coluna
- COLUMN_DEFAULT: Valor padrão da coluna
- IS_NULLABLE: O valor pode ser nulo?
- COLUMN_TYPE: O tipo da coluna
- COLUMN_KEY: Tipo de chave da coluna
- EXTRA: Atributos extras

## `academicos`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | ----- | ------------ |
| id-obra     | 1                | `-`            | NO          | int(11)      | `-`        | `-`   | NEVER        |
| id-acervo   | 2                | `-`            | NO          | int(11)      | `-`        | `-`   | NEVER        |
| programa    | 3                | `-`            | NO          | varchar(255) | `-`        | `-`   | NEVER        |

## `acervo`\*\*\*\*

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE                                       | COLUMN_KEY | EXTRA          | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | ------------------------------------------------- | ---------- | -------------- | ------------ |
| id          | 1                | `-`            | NO          | int(11)                                           | PRI        | auto_increment | NEVER        |
| id-campi    | 2                | `-`            | NO          | int(11)                                           | `-`        | `-`            | NEVER        |
| nome        | 3                | `-`            | NO          | varchar(255)                                      | `-`        | `-`            | NEVER        |
| tipo        | 4                | `-`            | NO          | enum('LIVROS','PERIODICOS','ACADEMICOS','MIDIAS') | `-`        | `-`            | NEVER        |
| local       | 5                | `-`            | NO          | varchar(255)                                      | `-`        | `-`            | NEVER        |
| ano         | 6                | `-`            | NO          | int(11)                                           | `-`        | `-`            | NEVER        |
| editora     | 7                | `-`            | NO          | varchar(255)                                      | `-`        | `-`            | NEVER        |
| paginas     | 8                | `-`            | NO          | int(11)                                           | `-`        | `-`            | NEVER        |

## `admin`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | ----- | ------------ |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | `-`   | NEVER        |
| nome        | 2                | `-`            | NO          | varchar(255) | `-`        | `-`   | NEVER        |
| usuario     | 3                | `-`            | NO          | varchar(255) | UNI        | `-`   | NEVER        |
| email       | 4                | `-`            | NO          | varchar(255) | UNI        | `-`   | NEVER        |
| senha       | 5                | `-`            | NO          | varchar(255) | `-`        | `-`   | NEVER        |

## `alunos`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE   | COLUMN_KEY | EXTRA | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | ------------- | ---------- | ----- | ------------ |
| id          | 1                | `-`            | NO          | int(11)       | PRI        | `-`   | NEVER        |
| nome        | 2                | `-`            | NO          | varchar(255)  | `-`        | `-`   | NEVER        |
| senha       | 3                | `-`            | NO          | varchar(255)  | `-`        | `-`   | NEVER        |
| email       | 4                | `-`            | NO          | varchar(255)  | UNI        | `-`   | NEVER        |
| sexo        | 5                | `-`            | NO          | enum('M','F') | `-`        | `-`   | NEVER        |
| nascimento  | 6                | `-`            | NO          | date          | `-`        | `-`   | NEVER        |
| logradouro  | 7                | `-`            | NO          | varchar(255)  | `-`        | `-`   | NEVER        |
| numero      | 8                | `-`            | NO          | int(11)       | `-`        | `-`   | NEVER        |
| complemento | 9                | NULL           | YES         | varchar(255)  | `-`        | `-`   | NEVER        |
| bairro      | 10               | `-`            | NO          | varchar(255)  | `-`        | `-`   | NEVER        |
| cidade      | 11               | `-`            | NO          | varchar(255)  | `-`        | `-`   | NEVER        |
| cep         | 12               | `-`            | NO          | int(11)       | `-`        | `-`   | NEVER        |
| uf          | 13               | `-`            | NO          | varchar(255)  | `-`        | `-`   | NEVER        |
| foto        | 14               | `-`            | NO          | text          | `-`        | `-`   | NEVER        |

## `autores`

| COLUMN_NAME  | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE                                                                       | COLUMN_KEY | EXTRA | IS_GENERATED |
| ------------ | ---------------- | -------------- | ----------- | --------------------------------------------------------------------------------- | ---------- | ----- | ------------ |
| id-obra      | 1                | `-`            | NO          | int(11)                                                                           | `-`        | `-`   | NEVER        |
| nome         | 2                | `-`            | NO          | varchar(255)                                                                      | `-`        | `-`   | NEVER        |
| sobrenome    | 3                | `-`            | NO          | varchar(255)                                                                      | `-`        | `-`   | NEVER        |
| ordem        | 4                | `-`            | NO          | int(11)                                                                           | `-`        | `-`   | NEVER        |
| qualificacao | 5                | `-`            | NO          | enum('PRINCIPAL','SECUNDARIO','ORGANIZADOR','COORDENADOR','COMPILADOR','DIRETOR') | `-`        | `-`   | NEVER        |

## `descartes`

| COLUMN_NAME   | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA | IS_GENERATED |
| ------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | ----- | ------------ |
| id-acervo     | 1                | `-`            | NO          | int(11)      | `-`        | `-`   | NEVER        |
| data-descarte | 2                | `-`            | NO          | date         | `-`        | `-`   | NEVER        |
| motivos       | 3                | `-`            | NO          | text         | `-`        | `-`   | NEVER        |
| operador      | 4                | `-`            | NO          | varchar(255) | `-`        | `-`   | NEVER        |

## `emprestimos`

| COLUMN_NAME     | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          | IS_GENERATED |
| --------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- | ------------ |
| id              | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment | NEVER        |
| id-alunos       | 2                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| id-acervo       | 3                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| data-emprestimo | 4                | `-`            | NO          | date         | `-`        | `-`            | NEVER        |
| data-prev-devol | 5                | `-`            | NO          | date         | `-`        | `-`            | NEVER        |
| data-devolucao  | 6                | `-`            | NO          | date         | `-`        | `-`            | NEVER        |
| multa           | 7                | `-`            | NO          | decimal(7,2) | `-`        | `-`            | NEVER        |

## `livros`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE | COLUMN_KEY | EXTRA | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | ----------- | ---------- | ----- | ------------ |
| id-obra     | 1                | `-`            | NO          | int(11)     | `-`        | `-`   | NEVER        |
| id-acervo   | 2                | `-`            | NO          | int(11)     | `-`        | `-`   | NEVER        |
| edicao      | 3                | `-`            | NO          | int(11)     | `-`        | `-`   | NEVER        |
| isbn        | 4                | `-`            | NO          | int(11)     | `-`        | `-`   | NEVER        |

## `midias`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE                        | COLUMN_KEY | EXTRA | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | ---------------------------------- | ---------- | ----- | ------------ |
| id-obra     | 1                | `-`            | NO          | int(11)                            | `-`        | `-`   | NEVER        |
| id-acervo   | 2                | `-`            | NO          | int(11)                            | `-`        | `-`   | NEVER        |
| tempo       | 3                | `-`            | NO          | time                               | `-`        | `-`   | NEVER        |
| subtipo     | 4                | `-`            | NO          | enum('CD','DVD','FITA','PENDRIVE') | `-`        | `-`   | NEVER        |

## `operador`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | ----- | ------------ |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | `-`   | NEVER        |
| nome        | 2                | `-`            | NO          | varchar(255) | `-`        | `-`   | NEVER        |
| senha       | 3                | `-`            | NO          | varchar(255) | `-`        | `-`   | NEVER        |
| email       | 4                | `-`            | NO          | varchar(255) | UNI        | `-`   | NEVER        |

## `partes`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          | IS_GENERATED |
| -------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- | ------------ |
| id             | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment | NEVER        |
| id-periodicos  | 2                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| titulo         | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |
| pag-inicio     | 4                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| pag-final      | 5                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| palavras-chave | 6                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |

## `periodicos`

| COLUMN_NAME   | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          | IS_GENERATED |
| ------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- | ------------ |
| id            | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment | NEVER        |
| id-acervo     | 2                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| periodicidade | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |
| mes           | 4                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |
| volume        | 5                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| subtipo       | 6                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |
| issn          | 7                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |

## `reservas`

| COLUMN_NAME  | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE | COLUMN_KEY | EXTRA          | IS_GENERATED |
| ------------ | ---------------- | -------------- | ----------- | ----------- | ---------- | -------------- | ------------ |
| id           | 1                | `-`            | NO          | int(11)     | PRI        | auto_increment | NEVER        |
| id-aluno     | 2                | `-`            | NO          | int(11)     | `-`        | `-`            | NEVER        |
| id-acervo    | 3                | `-`            | NO          | int(11)     | `-`        | `-`            | NEVER        |
| data-reserva | 4                | `-`            | NO          | date        | `-`        | `-`            | NEVER        |
| tempo-espera | 5                | `-`            | NO          | int(11)     | `-`        | `-`            | NEVER        |
| emprestou    | 6                | `-`            | NO          | tinyint(1)  | `-`        | `-`            | NEVER        |
