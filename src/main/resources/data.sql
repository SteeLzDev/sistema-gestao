CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    cargo VARCHAR(100),
    perfil VARCHAR(100),
    status VARCHAR(50)
);
-- Inserir usuários de exemplo
INSERT INTO usuarios (nome, username, email, cargo, perfil, status)
VALUES ('Davi Oliveira', 'davi', 'davi@oficina.com', 'Gerente', 'Administrador', 'Ativo');

INSERT INTO usuarios (nome, username, email, cargo, perfil, status) 
VALUES ('Ana Silva', 'ana', 'ana@oficina.com', 'Atendente', 'Operador', 'Ativo');

INSERT INTO usuarios (nome, username, email, cargo, perfil, status) 
VALUES ('Roberto Santos', 'roberto', 'roberto@oficina.com', 'Mecânico', 'Operador', 'Ativo');

INSERT INTO usuarios (nome, username, email, cargo, perfil, status) 
VALUES ('Juliana Costa', 'juliana', 'juliana@oficina.com', 'Financeiro', 'Administrador', 'Ativo');

INSERT INTO usuarios (nome, username, email, cargo, perfil, status) 
VALUES ('Pedro Almeida', 'pedro', 'pedro@oficina.com', 'Estoquista', 'Operador', 'Inativo');

