SET time_zone = "-03:00";

----------------
-- diario.sql --
----------------

CREATE DATABASE IF NOT EXISTS diario CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE diario;


DROP TABLE IF EXISTS `alunos`;
CREATE TABLE IF NOT EXISTS `alunos` (
  `id` BIGINT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `senha` VARCHAR(255) NOT NULL,
  `sexo` ENUM('M', 'F') NOT NULL,
  `nascimento` DATE NOT NULL,
  `logradouro` VARCHAR(255) NOT NULL,
  `numero` INT NOT NULL,
  `complemento` VARCHAR(255),
  `bairro` VARCHAR(255) NOT NULL,
  `cidade` VARCHAR(255) NOT NULL,
  `cep` INT NOT NULL,
  `uf` VARCHAR(255) NOT NULL,
  `foto` text,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `professores`;
CREATE TABLE IF NOT EXISTS `professores` (
  `id` INT NOT NULL,
  `id-depto` INT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `titulacao` ENUM('M', 'D', 'E', 'G') NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `usuario` VARCHAR(255) NOT NULL, 
  `email` VARCHAR(255),
  `senha` VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `campi`;
CREATE TABLE IF NOT EXISTS `campi` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `cidade` VARCHAR(255) NOT NULL,
  `uf` VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `cursos`;
CREATE TABLE IF NOT EXISTS `cursos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-depto` INT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `horas-total` INT NOT NULL,
  `modalidade` VARCHAR(255),
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `departamentos`;
CREATE TABLE IF NOT EXISTS `departamentos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-campi` INT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `disciplinas`;
CREATE TABLE IF NOT EXISTS `disciplinas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-turmas` INT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `carga-horaria-min` INT NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `etapas`;
CREATE TABLE IF NOT EXISTS `etapas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ano` INT NOT NULL,
  `valor` DECIMAL(5, 2) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `matriculas`;
CREATE TABLE IF NOT EXISTS `matriculas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-alunos` BIGINT NOT NULL,
  `id-disciplinas` INT NOT NULL,
  `ano` INT NOT NULL,
  `ativo` BOOLEAN NOT NULL DEFAULT True,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `prof_disciplinas`;
CREATE TABLE IF NOT EXISTS `prof_disciplinas` (
  `id-professores` INT NOT NULL,
  `id-disciplinas` INT NOT NULL
);

DROP TABLE IF EXISTS `turmas`;
CREATE TABLE IF NOT EXISTS `turmas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-cursos` INT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `conteudos`;
CREATE TABLE IF NOT EXISTS `conteudos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-etapas` INT NOT NULL,
  `id-disciplinas` INT NOT NULL,
  `conteudos` TEXT,
  `data` DATE NOT NULL,
  `valor` DECIMAL(5, 2) NOT NULL DEFAULT 000.00,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `diario`;
CREATE TABLE IF NOT EXISTS `diario` (
  `id-conteudos` INT NOT NULL,
  `id-matriculas` INT NOT NULL,
  `faltas` INT NOT NULL DEFAULT 0,
  `nota` DECIMAL(5, 2) NOT NULL DEFAULT 000.00
);

--------------------
-- biblioteca.sql --
--------------------

CREATE DATABASE IF NOT EXISTS biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE biblioteca;

DROP TABLE IF EXISTS `alunos`;
CREATE TABLE IF NOT EXISTS `alunos` (
  `id` BIGINT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `sexo` ENUM('M', 'F') NOT NULL,
  `nascimento` DATE NOT NULL,
  `logradouro` VARCHAR(255) NOT NULL,
  `numero` INT NOT NULL,
  `complemento` VARCHAR(255),
  `bairro` VARCHAR(255) NOT NULL,
  `cidade` VARCHAR(255) NOT NULL,
  `cep` INT NOT NULL,
  `uf` VARCHAR(255) NOT NULL,
  `foto` text,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `campi`;
CREATE TABLE IF NOT EXISTS `campi` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `cidade` VARCHAR(255) NOT NULL,
  `uf` VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `operador`;
CREATE TABLE IF NOT EXISTS `operador` (
  `id` INT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `usuario` VARCHAR(255) NOT NULL, 
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `senha` VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `academicos`;
CREATE TABLE IF NOT EXISTS `academicos` (
  `id-obra` INT NOT NULL,
  `id-acervo` INT NOT NULL,
  `programa` VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS `acervo`;
CREATE TABLE IF NOT EXISTS `acervo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-campi` INT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `tipo` ENUM('LIVROS', 'PERIODICOS', 'ACADEMICOS', 'MIDIAS') NOT NULL,
  `local` VARCHAR(255) NOT NULL,
  `ano` INT NOT NULL,
  `editora` VARCHAR(255) NOT NULL,
  `paginas` INT NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `autores`;
CREATE TABLE IF NOT EXISTS `autores` (
  `id-obra` INT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `sobrenome` VARCHAR(255) NOT NULL,
  `ordem` INT NOT NULL,
  `qualificacao` ENUM(
    'PRINCIPAL', 
    'SECUNDARIO', 
    'ORGANIZADOR', 
    'COORDENADOR', 
    'COMPILADOR', 
    'DIRETOR'
  ) NOT NULL
);

DROP TABLE IF EXISTS `livros`;
CREATE TABLE IF NOT EXISTS `livros` (
  `id-obra` INT NOT NULL,
  `id-acervo` INT NOT NULL,
  `edicao` INT NOT NULL,
  `isbn` BIGINT NOT NULL
);

DROP TABLE IF EXISTS `midias`;
CREATE TABLE IF NOT EXISTS `midias` (
  `id-obra` INT NOT NULL,
  `id-acervo` INT NOT NULL,
  `tempo` TIME NOT NULL,
  `subtipo` ENUM('CD', 'DVD', 'FITA', 'PENDRIVE') NOT NULL
);

DROP TABLE IF EXISTS `partes`;
CREATE TABLE IF NOT EXISTS `partes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-periodicos` INT NOT NULL,
  `titulo` VARCHAR(255) NOT NULL,
  `pag-inicio` INT NOT NULL,
  `pag-final` INT NOT NULL,
  `palavras-chave` VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `periodicos`;
CREATE TABLE IF NOT EXISTS `periodicos` (
  `id` INT NOT NULL,
  `id-acervo` INT NOT NULL,
  `periodicidade` VARCHAR(255) NOT NULL,
  `mes` VARCHAR(255) NOT NULL,
  `volume` INT NOT NULL,
  `subtipo` VARCHAR(255) NOT NULL,
  `issn` INT NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `descartes`;
CREATE TABLE IF NOT EXISTS `descartes` (
  `id-acervo` INT NOT NULL,
  `data-descarte` DATE NOT NULL,
  `motivos` TEXT NOT NULL,
  `operador` VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS `emprestimos`;
CREATE TABLE IF NOT EXISTS `emprestimos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-alunos` BIGINT NOT NULL,
  `id-acervo` INT NOT NULL,
  `data-emprestimo` DATE NOT NULL,
  `data-prev-devol` DATE NOT NULL,
  `data-devolucao` DATE,
  `multa` DECIMAL(7,2) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `reservas`;
CREATE TABLE IF NOT EXISTS `reservas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-alunos` BIGINT NOT NULL,
  `id-acervo` INT NOT NULL,
  `data-reserva` DATE NOT NULL,
  `tempo-espera` INT NOT NULL,
  `emprestou` BOOLEAN NOT NULL DEFAULT False,
  PRIMARY KEY (id)
);

-------------------
-- add_admin.sql --
-------------------

USE diario;
INSERT INTO admin (nome, usuario, email, senha) VALUES ('Admins Adminos',	'admin',	'admin@admin.com',	'1000:47e67c5a0984a060b51ce53b809890ce:ef7fbe203771c4625150809b0e3cd64eedc0a3e13364e5aad40fb3d14cac37d4a0c458e0c5773519ac9b0303099da9f1d6ed847b57ec99f5920944c283c5a7c7');

USE biblioteca;
INSERT INTO admin (nome, usuario, email, senha) VALUES ('Admins Adminos',	'admin',	'admin@admin.com',	'1000:47e67c5a0984a060b51ce53b809890ce:ef7fbe203771c4625150809b0e3cd64eedc0a3e13364e5aad40fb3d14cac37d4a0c458e0c5773519ac9b0303099da9f1d6ed847b57ec99f5920944c283c5a7c7');
