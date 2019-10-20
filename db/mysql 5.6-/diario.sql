SET time_zone = "-03:00";


CREATE DATABASE IF NOT EXISTS diario CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE diario;


DROP TABLE IF EXISTS `alunos`;
CREATE TABLE IF NOT EXISTS `alunos` (
  `id` INT NOT NULL,
  `nome` VARCHAR(127) NOT NULL,
  `email` VARCHAR(127) NOT NULL UNIQUE,
  `senha` TEXT NOT NULL,
  `sexo` ENUM('M', 'F') NOT NULL,
  `nascimento` DATE NOT NULL,
  `logradouro` VARCHAR(127) NOT NULL,
  `numero` INT NOT NULL,
  `complemento` VARCHAR(127),
  `bairro` VARCHAR(127) NOT NULL,
  `cidade` VARCHAR(127) NOT NULL,
  `cep` INT NOT NULL,
  `uf` VARCHAR(127) NOT NULL,
  `foto` text NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `professores`;
CREATE TABLE IF NOT EXISTS `professores` (
  `id` INT NOT NULL,
  `id-depto` INT NOT NULL,
  `nome` VARCHAR(127) NOT NULL,
  `senha` TEXT NOT NULL,
  `email` VARCHAR(127) NOT NULL UNIQUE,
  `titulacao` ENUM('M', 'D', 'E', 'G') NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id` INT NOT NULL,
  `nome` VARCHAR(127) NOT NULL,
  `usuario` VARCHAR(127) NOT NULL, 
  `email` VARCHAR(127) NOT NULL UNIQUE,
  `senha` TEXT NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `campi`;
CREATE TABLE IF NOT EXISTS `campi` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(127) NOT NULL,
  `cidade` VARCHAR(127) NOT NULL,
  `uf` VARCHAR(127) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `cursos`;
CREATE TABLE IF NOT EXISTS `cursos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-depto` INT NOT NULL,
  `nome` VARCHAR(127) NOT NULL,
  `horas-total` INT NOT NULL,
  `modalidade` VARCHAR(127) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `departamentos`;
CREATE TABLE IF NOT EXISTS `departamentos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-campi` INT NOT NULL,
  `nome` VARCHAR(127) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `disciplinas`;
CREATE TABLE IF NOT EXISTS `disciplinas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-turmas` INT NOT NULL,
  `nome` VARCHAR(127) NOT NULL,
  `carga-horaria-min` INT NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `etapas`;
CREATE TABLE IF NOT EXISTS `etapas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ano` INT NOT NULL,
  `valor` DECIMAL(3, 2) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `matriculas`;
CREATE TABLE IF NOT EXISTS `matriculas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-alunos` INT NOT NULL,
  `id-disciplinas` INT NOT NULL,
  `ano` INT NOT NULL,
  `ativo` BOOLEAN NOT NULL,
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
  `nome` VARCHAR(127) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `atividades`;
CREATE TABLE IF NOT EXISTS `atividades` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-disciplinas` INT NOT NULL,
  `nome` VARCHAR(127) NOT NULL,
  `data` DATE NOT NULL,
  `valor` DECIMAL(3, 2) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `conteudos`;
CREATE TABLE IF NOT EXISTS `conteudos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id-etapas` INT NOT NULL,
  `id-disciplinas` INT NOT NULL,
  `conteudos` TEXT NOT NULL,
  `datas` DATE NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `diario`;
CREATE TABLE IF NOT EXISTS `diario` (
  `id-conteudos` INT NOT NULL,
  `id-matriculas` INT NOT NULL,
  `id-atividades` INT NOT NULL,
  `faltas` INT NOT NULL,
  `nota` DECIMAL(3, 2) NOT NULL
);
