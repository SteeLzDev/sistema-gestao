-- Primeiro, remover as tabelas com chaves estrangeiras
DROP TABLE IF EXISTS itens_venda;

-- Depois, remover as tabelas referenciadas
DROP TABLE IF EXISTS vendas;
DROP TABLE IF EXISTS produtos;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS fila_atendimento;
DROP TABLE IF EXISTS atendimentos;

-- Create usuarios table
CREATE TABLE usuarios (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(255) NOT NULL,
username VARCHAR(100) NOT NULL UNIQUE,
email VARCHAR(255) NOT NULL,
senha VARCHAR(255) NOT NULL,
cargo VARCHAR(100),
perfil VARCHAR(100),
status VARCHAR(50)
);

-- Create produtos table
CREATE TABLE produtos (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
codigo VARCHAR(100) NOT NULL UNIQUE,
nome VARCHAR(255) NOT NULL,
categoria VARCHAR(100) NOT NULL,
quantidade INT NOT NULL,
preco DECIMAL(10, 2) NOT NULL
);

-- Create vendas table
CREATE TABLE vendas (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
cliente VARCHAR(255) NOT NULL,
data_hora TIMESTAMP NOT NULL,
valor_total DECIMAL(10, 2) NOT NULL
);

-- Create itens_venda table
CREATE TABLE itens_venda (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
venda_id BIGINT NOT NULL,
produto_id BIGINT NOT NULL,
quantidade INT NOT NULL,
preco_unitario DECIMAL(10, 2) NOT NULL,
subtotal DECIMAL(10, 2) NOT NULL,
FOREIGN KEY (venda_id) REFERENCES vendas(id),
FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

-- Create fila_atendimento table
CREATE TABLE fila_atendimento (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(255) NOT NULL,
servico VARCHAR(255) NOT NULL,
telefone VARCHAR(20),
observacoes VARCHAR(500),
prioridade VARCHAR(50) NOT NULL,
chegada TIMESTAMP NOT NULL,
status VARCHAR(50) NOT NULL
);

-- Create atendimentos table
CREATE TABLE atendimentos (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(255) NOT NULL,
servico VARCHAR(255) NOT NULL,
inicio TIMESTAMP NOT NULL,
atendente VARCHAR(255) NOT NULL,
status VARCHAR(50) NOT NULL
);