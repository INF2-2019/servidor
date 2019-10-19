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

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | ----- |
| id-obra     | 1                | `-`            | NO          | int(11)      | `-`        | `-`   |
| id-acervo   | 2                | `-`            | NO          | int(11)      | `-`        | `-`   |
| programa    | 3                | `-`            | NO          | varchar(255) | `-`        | `-`   |

## `acervo`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-campi    | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| nome        | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| tipo        | 4                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| local       | 5                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| ano         | 6                | `-`            | NO          | int(11)      | `-`        | `-`            |
| editora     | 7                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| paginas     | 8                | `-`            | NO          | int(11)      | `-`        | `-`            |

## `autores`

| COLUMN_NAME  | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA |
| ------------ | ---------------- | -------------- | ----------- | ------------ | ---------- | ----- |
| id-obra      | 1                | `-`            | NO          | int(11)      | `-`        | `-`   |
| nomes        | 2                | `-`            | NO          | varchar(255) | `-`        | `-`   |
| sobrenome    | 3                | `-`            | NO          | varchar(255) | `-`        | `-`   |
| ordem        | 4                | `-`            | NO          | int(11)      | `-`        | `-`   |
| qualificacao | 5                | `-`            | NO          | varchar(255) | `-`        | `-`   |

## `descartes`

| COLUMN_NAME   | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA |
| ------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | ----- |
| id-acervo     | 1                | `-`            | NO          | int(11)      | `-`        | `-`   |
| data-descarte | 2                | `-`            | NO          | date         | `-`        | `-`   |
| motivos       | 3                | `-`            | NO          | text         | `-`        | `-`   |
| operador      | 4                | `-`            | NO          | varchar(255) | `-`        | `-`   |

## `emprestimos`

| COLUMN_NAME     | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| --------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id              | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-alunos       | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| id-acervo       | 3                | `-`            | NO          | int(11)      | `-`        | `-`            |
| data-emprestimo | 4                | `-`            | NO          | date         | `-`        | `-`            |
| data-prev-devol | 5                | `-`            | NO          | date         | `-`        | `-`            |
| data-devolucao  | 6                | `-`            | NO          | date         | `-`        | `-`            |
| multa           | 7                | `-`            | NO          | decimal(5,2) | `-`        | `-`            |

## `livros`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE | COLUMN_KEY | EXTRA |
| ----------- | ---------------- | -------------- | ----------- | ----------- | ---------- | ----- |
| id-obra     | 1                | `-`            | NO          | int(11)     | `-`        | `-`   |
| id-acervo   | 2                | `-`            | NO          | int(11)     | `-`        | `-`   |
| edicao      | 3                | `-`            | NO          | int(11)     | `-`        | `-`   |
| isbn        | 4                | `-`            | NO          | int(11)     | `-`        | `-`   |

## `midias`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | ----- |
| id-obra     | 1                | `-`            | NO          | int(11)      | `-`        | `-`   |
| id-acervo   | 2                | `-`            | NO          | int(11)      | `-`        | `-`   |
| tempo       | 3                | `-`            | NO          | time         | `-`        | `-`   |
| subtipo     | 4                | `-`            | NO          | varchar(255) | `-`        | `-`   |

## `partes`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| -------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id             | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-periodicos  | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| titulo         | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| pag-inicio     | 4                | `-`            | NO          | int(11)      | `-`        | `-`            |
| pag-final      | 5                | `-`            | NO          | int(11)      | `-`        | `-`            |
| palavras-chave | 6                | `-`            | NO          | varchar(255) | `-`        | `-`            |

## `periodicos`

| COLUMN_NAME   | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| ------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id            | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-acervo     | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| periodicidade | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| mes           | 4                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| volume        | 5                | `-`            | NO          | int(11)      | `-`        | `-`            |
| subtipo       | 6                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| issn          | 7                | `-`            | NO          | int(11)      | `-`        | `-`            |

## `reservas`

| COLUMN_NAME  | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE | COLUMN_KEY | EXTRA          |
| ------------ | ---------------- | -------------- | ----------- | ----------- | ---------- | -------------- |
| id           | 1                | `-`            | NO          | int(11)     | PRI        | auto_increment |
| id-aluno     | 2                | `-`            | NO          | int(11)     | `-`        | `-`            |
| id-acervo    | 3                | `-`            | NO          | int(11)     | `-`        | `-`            |
| data-reserva | 4                | `-`            | NO          | date        | `-`        | `-`            |
| tempo-espera | 5                | `-`            | NO          | int(11)     | `-`        | `-`            |
| emprestou    | 6                | `-`            | NO          | tinyint(1)  | `-`        | `-`            |
