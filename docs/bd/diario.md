# `diario`

Nesse arquivo estão a estrutura do banco de dados do diário acadêmico.

Traduções:

- COLUMN_NAME: Nome da Coluna
- ORDINAL_POSITION: Posição da coluna
- COLUMN_DEFAULT: Valor padrão da coluna
- IS_NULLABLE: O valor pode ser nulo?
- COLUMN_TYPE: O tipo da coluna
- COLUMN_KEY: Tipo de chave da coluna
- EXTRA: Atributos extras

## `alunos`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | ----- |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | `-`   |
| nome        | 2                | `-`            | NO          | varchar(255) | `-`        | `-`   |
| sexo        | 3                | `-`            | NO          | varchar(255) | `-`        | `-`   |
| nascimento  | 4                | `-`            | NO          | varchar(255) | `-`        | `-`   |
| logradouro  | 5                | `-`            | NO          | varchar(255) | `-`        | `-`   |
| numero      | 6                | `-`            | NO          | int(11)      | `-`        | `-`   |
| complemento | 7                | `-`            | NO          | varchar(255) | `-`        | `-`   |
| bairro      | 8                | `-`            | NO          | varchar(255) | `-`        | `-`   |
| cidade      | 9                | `-`            | NO          | varchar(255) | `-`        | `-`   |
| cep         | 10               | `-`            | NO          | int(11)      | `-`        | `-`   |
| uf          | 11               | `-`            | NO          | varchar(255) | `-`        | `-`   |
| email       | 12               | `-`            | NO          | varchar(255) | UNI        | `-`   |
| foto        | 13               | `-`            | NO          | text         | `-`        | `-`   |

## `atividades`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| -------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id             | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-disciplinas | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| nome           | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| data           | 4                | `-`            | NO          | date         | `-`        | `-`            |
| valor          | 5                | `-`            | NO          | decimal(3,2) | `-`        | `-`            |

## `campi`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| nome        | 2                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| cidade      | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| uf          | 4                | `-`            | NO          | varchar(255) | `-`        | `-`            |

## `conteudos`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE | COLUMN_KEY | EXTRA          |
| -------------- | ---------------- | -------------- | ----------- | ----------- | ---------- | -------------- |
| id             | 1                | `-`            | NO          | int(11)     | PRI        | auto_increment |
| id-etapas      | 2                | `-`            | NO          | int(11)     | `-`        | `-`            |
| id-disciplinas | 3                | `-`            | NO          | int(11)     | `-`        | `-`            |
| conteudos      | 4                | `-`            | NO          | text        | `-`        | `-`            |
| datas          | 5                | `-`            | NO          | date        | `-`        | `-`            |

## `cursos`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-depto    | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| nome        | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| horas-total | 4                | `-`            | NO          | int(11)      | `-`        | `-`            |
| modalidade  | 5                | `-`            | NO          | varchar(255) | `-`        | `-`            |

## `departamentos`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-campi    | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| nome        | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |

## `diario`

| COLUMN_NAME   | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA |
| ------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | ----- |
| id-conteudos  | 1                | `-`            | NO          | int(11)      | `-`        | `-`   |
| id-matriculas | 2                | `-`            | NO          | int(11)      | `-`        | `-`   |
| id-atividades | 3                | `-`            | NO          | int(11)      | `-`        | `-`   |
| faltas        | 4                | `-`            | NO          | int(11)      | `-`        | `-`   |
| nota          | 5                | `-`            | NO          | decimal(3,2) | `-`        | `-`   |

## `disciplinas`

| COLUMN_NAME       | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| ----------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id                | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-turmas         | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| nome              | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| carga-horaria-min | 4                | `-`            | NO          | int(11)      | `-`        | `-`            |

## `etapas`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| valor       | 2                | `-`            | NO          | decimal(3,2) | `-`        | `-`            |

## `matriculas`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE | COLUMN_KEY | EXTRA          |
| -------------- | ---------------- | -------------- | ----------- | ----------- | ---------- | -------------- |
| id             | 1                | `-`            | NO          | int(11)     | PRI        | auto_increment |
| id-alunos      | 2                | `-`            | NO          | int(11)     | `-`        | `-`            |
| id-disciplinas | 3                | `-`            | NO          | int(11)     | `-`        | `-`            |
| ano            | 4                | `-`            | NO          | int(11)     | `-`        | `-`            |
| ativo          | 5                | `-`            | NO          | tinyint(1)  | `-`        | `-`            |

## `prof_disciplinas`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE | COLUMN_KEY | EXTRA |
| -------------- | ---------------- | -------------- | ----------- | ----------- | ---------- | ----- |
| id-professores | 1                | `-`            | NO          | int(11)     | `-`        | `-`   |
| id-disciplinas | 2                | `-`            | NO          | int(11)     | `-`        | `-`   |

## `professores`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | ----- |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | `-`   |
| id-depto    | 2                | `-`            | NO          | int(11)      | `-`        | `-`   |
| nome        | 3                | `-`            | NO          | varchar(255) | `-`        | `-`   |
| titulacao   | 4                | `-`            | NO          | varchar(255) | `-`        | `-`   |

## `turmas`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-cursos   | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| nome        | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
