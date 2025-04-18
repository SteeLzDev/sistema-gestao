-- Primeiro, excluir tabelas que têm chaves estrangeiras
DROP TABLE IF EXISTS usuarios_permissoes;
-- Outras tabelas dependentes aqui...

-- Depois, excluir as tabelas principais
DROP TABLE IF EXISTS permissoes;
DROP TABLE IF EXISTS usuarios;
-- Outras tabelas principais aqui...

-- Criar tabelas na ordem correta
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    cargo VARCHAR(50),
    perfil VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS permissoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS usuarios_permissoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    permissao_id BIGINT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (permissao_id) REFERENCES permissoes(id)
);

-- Outras criações de tabela aqui...