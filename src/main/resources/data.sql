--CREATE TABLE IF NOT EXISTS usuarios (
--    id SERIAL PRIMARY KEY,
--    nome VARCHAR(255) NOT NULL,
--    username VARCHAR(100) NOT NULL UNIQUE,
--    senha VARCHAR(255) NOT NULL UNIQUE,
--    email VARCHAR(255) NOT NULL UNIQUE,
--    cargo VARCHAR(100),
--    perfil VARCHAR(100),
--    status VARCHAR(50)
--);
-- Simple insert statements for H2 database
INSERT INTO usuarios (nome, username, email, senha, cargo, perfil, status)
VALUES ('Administrador', 'admin', 'admin@sistema.com', 'admin123', 'Administrador', 'Administrador', 'Ativo');

INSERT INTO usuarios (nome, username, email, senha, cargo, perfil, status)
VALUES ('Carlos Oliveira', 'carlos', 'carlos@oficina.com', '123456', 'Gerente', 'Administrador', 'Ativo');

INSERT INTO usuarios (nome, username, email, senha, cargo, perfil, status)
VALUES ('Ana Silva', 'ana', 'ana@oficina.com', '123456', 'Atendente', 'Operador', 'Ativo');
