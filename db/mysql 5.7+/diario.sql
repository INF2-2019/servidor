SET time_zone = "-03:00";


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
