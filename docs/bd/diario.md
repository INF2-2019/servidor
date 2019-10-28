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
| email       | 3                | `-`            | NO          | varchar(255)  | UNI        | `-`   | NEVER        |
| senha       | 4                | `-`            | NO          | varchar(255)  | `-`        | `-`   | NEVER        |
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

## `atividades`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          | IS_GENERATED |
| -------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- | ------------ |
| id             | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment | NEVER        |
| id-disciplinas | 2                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| nome           | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |
| data           | 4                | `-`            | NO          | date         | `-`        | `-`            | NEVER        |
| valor          | 5                | `-`            | NO          | decimal(5,2) | `-`        | `-`            | NEVER        |

## `campi`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- | ------------ |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment | NEVER        |
| nome        | 2                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |
| cidade      | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |
| uf          | 4                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |

## `conteudos`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE | COLUMN_KEY | EXTRA          | IS_GENERATED |
| -------------- | ---------------- | -------------- | ----------- | ----------- | ---------- | -------------- | ------------ |
| id             | 1                | `-`            | NO          | int(11)     | PRI        | auto_increment | NEVER        |
| id-etapas      | 2                | `-`            | NO          | int(11)     | `-`        | `-`            | NEVER        |
| id-disciplinas | 3                | `-`            | NO          | int(11)     | `-`        | `-`            | NEVER        |
| conteudos      | 4                | `-`            | NO          | text        | `-`        | `-`            | NEVER        |
| datas          | 5                | `-`            | NO          | date        | `-`        | `-`            | NEVER        |

## `cursos`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- | ------------ |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment | NEVER        |
| id-depto    | 2                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| nome        | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |
| horas-total | 4                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| modalidade  | 5                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |

## `departamentos`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- | ------------ |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment | NEVER        |
| id-campi    | 2                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| nome        | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |

## `diario`

| COLUMN_NAME   | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA | IS_GENERATED |
| ------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | ----- | ------------ |
| id-conteudos  | 1                | `-`            | NO          | int(11)      | `-`        | `-`   | NEVER        |
| id-matriculas | 2                | `-`            | NO          | int(11)      | `-`        | `-`   | NEVER        |
| id-atividades | 3                | `-`            | NO          | int(11)      | `-`        | `-`   | NEVER        |
| faltas        | 4                | `-`            | NO          | int(11)      | `-`        | `-`   | NEVER        |
| nota          | 5                | `-`            | NO          | decimal(5,2) | `-`        | `-`   | NEVER        |

## `disciplinas`

| COLUMN_NAME       | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          | IS_GENERATED |
| ----------------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- | ------------ |
| id                | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment | NEVER        |
| id-turmas         | 2                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| nome              | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |
| carga-horaria-min | 4                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |

## `etapas`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- | ------------ |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment | NEVER        |
| ano         | 2                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| valor       | 3                | `-`            | NO          | decimal(5,2) | `-`        | `-`            | NEVER        |

## `matriculas`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE | COLUMN_KEY | EXTRA          | IS_GENERATED |
| -------------- | ---------------- | -------------- | ----------- | ----------- | ---------- | -------------- | ------------ |
| id             | 1                | `-`            | NO          | int(11)     | PRI        | auto_increment | NEVER        |
| id-alunos      | 2                | `-`            | NO          | int(11)     | `-`        | `-`            | NEVER        |
| id-disciplinas | 3                | `-`            | NO          | int(11)     | `-`        | `-`            | NEVER        |
| ano            | 4                | `-`            | NO          | int(11)     | `-`        | `-`            | NEVER        |
| ativo          | 5                | `-`            | NO          | tinyint(1)  | `-`        | `-`            | NEVER        |

## `professores`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE           | COLUMN_KEY | EXTRA | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | --------------------- | ---------- | ----- | ------------ |
| id          | 1                | `-`            | NO          | int(11)               | PRI        | `-`   | NEVER        |
| id-depto    | 2                | `-`            | NO          | int(11)               | `-`        | `-`   | NEVER        |
| nome        | 3                | `-`            | NO          | varchar(255)          | `-`        | `-`   | NEVER        |
| senha       | 4                | `-`            | NO          | varchar(255)          | `-`        | `-`   | NEVER        |
| email       | 5                | `-`            | NO          | varchar(255)          | UNI        | `-`   | NEVER        |
| titulacao   | 6                | `-`            | NO          | enum('M','D','E','G') | `-`        | `-`   | NEVER        |

## `prof_disciplinas`

| COLUMN_NAME    | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE | COLUMN_KEY | EXTRA | IS_GENERATED |
| -------------- | ---------------- | -------------- | ----------- | ----------- | ---------- | ----- | ------------ |
| id-professores | 1                | `-`            | NO          | int(11)     | `-`        | `-`   | NEVER        |
| id-disciplinas | 2                | `-`            | NO          | int(11)     | `-`        | `-`   | NEVER        |

## `turmas`

| COLUMN_NAME | ORDINAL_POSITION | COLUMN_DEFAULT | IS_NULLABLE | COLUMN_TYPE  | COLUMN_KEY | EXTRA          | IS_GENERATED |
| ----------- | ---------------- | -------------- | ----------- | ------------ | ---------- | -------------- | ------------ |
| id          | 1                | `-`            | NO          | int(11)      | PRI        | auto_increment | NEVER        |
| id-cursos   | 2                | `-`            | NO          | int(11)      | `-`        | `-`            | NEVER        |
| nome        | 3                | `-`            | NO          | varchar(255) | `-`        | `-`            | NEVER        |
