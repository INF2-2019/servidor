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

## `admin`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| nome        | 2                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| usuario     | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| email       | 4                | `-`            | YES         | varchar(255) | `-`        | `-`            |
| senha       | 5                | `-`            | NO          | varchar(255) | `-`        | `-`            |

## `alunos`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE   | COLUMN_KEY | EXTRA |
| ----------- | ---------------- | -------------- | ----------- | ------------- | ---------- | ----- |
| id          | 1                | `-`            | NO          | bigint(20)    | PRI        | `-`   |
| nome        | 2                | `-`            | NO          | varchar(255)  | `-`        | `-`   |
| email       | 3                | `-`            | NO          | varchar(255)  | UNI        | `-`   |
| senha       | 4                | `-`            | NO          | varchar(255)  | `-`        | `-`   |
| sexo        | 5                | `-`            | NO          | enum('M','F') | `-`        | `-`   |
| nascimento  | 6                | `-`            | NO          | date          | `-`        | `-`   |
| logradouro  | 7                | `-`            | NO          | varchar(255)  | `-`        | `-`   |
| numero      | 8                | `-`            | NO          | int(11)       | `-`        | `-`   |
| complemento | 9                | `-`            | YES         | varchar(255)  | `-`        | `-`   |
| bairro      | 10               | `-`            | NO          | varchar(255)  | `-`        | `-`   |
| cidade      | 11               | `-`            | NO          | varchar(255)  | `-`        | `-`   |
| cep         | 12               | `-`            | NO          | int(11)       | `-`        | `-`   |
| uf          | 13               | `-`            | NO          | varchar(255)  | `-`        | `-`   |
| foto        | 14               | `-`            | YES         | text          | `-`        | `-`   |

## `atividades`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| -------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id             | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-disciplinas | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| nome           | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| data           | 4                | `-`            | NO          | date         | `-`        | `-`            |
| valor          | 5                | `-`            | NO          | decimal(5,2) | `-`        | `-`            |

## `campi`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| nome        | 2                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| cidade      | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| uf          | 4                | `-`            | NO          | varchar(255) | `-`        | `-`            |

## `conteudos`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| -------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id             | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-etapas      | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| id-disciplinas | 3                | `-`            | NO          | int(11)      | `-`        | `-`            |
| conteudos      | 4                | `-`            | YES         | text         | `-`        | `-`            |
| data           | 5                | `-`            | NO          | date         | `-`        | `-`            |
| valor          | 6                | 0.00           | NO          | decimal(5,2) | `-`        | `-`            |

## `cursos`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-depto    | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| nome        | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
| horas-total | 4                | `-`            | NO          | int(11)      | `-`        | `-`            |
| modalidade  | 5                | `-`            | YES         | varchar(255) | `-`        | `-`            |

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
| faltas        | 3                | 0              | NO          | int(11)      | `-`        | `-`   |
| nota          | 4                | 0.00           | NO          | decimal(5,2) | `-`        | `-`   |

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
| ano         | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| valor       | 3                | `-`            | NO          | decimal(5,2) | `-`        | `-`            |

## `matriculas`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE | COLUMN_KEY | EXTRA          |
| -------------- | ---------------- | -------------- | ----------- | ----------- | ---------- | -------------- |
| id             | 1                | `-`            | NO          | int(11)     | PRI        | auto_increment |
| id-alunos      | 2                | `-`            | NO          | bigint(20)  | `-`        | `-`            |
| id-disciplinas | 3                | `-`            | NO          | int(11)     | `-`        | `-`            |
| ano            | 4                | `-`            | NO          | int(11)     | `-`        | `-`            |
| ativo          | 5                | 1              | NO          | tinyint(1)  | `-`        | `-`            |

## `prof_disciplinas`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE | COLUMN_KEY | EXTRA |
| -------------- | ---------------- | -------------- | ----------- | ----------- | ---------- | ----- |
| id-professores | 1                | `-`            | NO          | int(11)     | `-`        | `-`   |
| id-disciplinas | 2                | `-`            | NO          | int(11)     | `-`        | `-`   |

## `professores`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE           | COLUMN_KEY | EXTRA |
| ----------- | ---------------- | -------------- | ----------- | --------------------- | ---------- | ----- |
| id          | 1                | `-`            | NO          | int(11)               | PRI        | `-`   |
| id-depto    | 2                | `-`            | NO          | int(11)               | `-`        | `-`   |
| nome        | 3                | `-`            | NO          | varchar(255)          | `-`        | `-`   |
| senha       | 4                | `-`            | NO          | varchar(255)          | `-`        | `-`   |
| email       | 5                | `-`            | NO          | varchar(255)          | UNI        | `-`   |
| titulacao   | 6                | `-`            | NO          | enum('M','D','E','G') | `-`        | `-`   |

## `turmas`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment |
| id-cursos   | 2                | `-`            | NO          | int(11)      | `-`        | `-`            |
| nome        | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            |
