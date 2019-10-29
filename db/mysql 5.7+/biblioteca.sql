SET time_zone = "-03:00";


CREATE DATABASE IF NOT EXISTS biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE biblioteca;

DROP TABLE IF EXISTS `alunos`;
CREATE TABLE IF NOT EXISTS `alunos` (
  `id` INT NOT NULL,
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
  `foto` text NOT NULL,
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
  `isbn` INT NOT NULL
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
  `id` INT NOT NULL AUTO_INCREMENT,
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
  `id-alunos` INT NOT NULL,
  `id-acervo` INT NOT NULL,
  `data-emprestimo` DATE NOT NULL,
  `data-prev-devol` DATE NOT NULL,
  `data-devolucao` DATE NOT NULL,
  `multa` DECIMAL(7,2) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `reservas`;
CREATE TABLE IF NOT EXISTS `reservas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-aluno` INT NOT NULL,
  `id-acervo` INT NOT NULL,
  `data-reserva` DATE NOT NULL,
  `tempo-espera` INT NOT NULL,
  `emprestou` BOOLEAN NOT NULL,
  PRIMARY KEY (id)
);
