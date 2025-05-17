-- Tabela de configurações gerais
CREATE TABLE IF NOT EXISTS configuracao_geral (
    id BIGSERIAL PRIMARY KEY,
    nome_sistema VARCHAR(100) NOT NULL,
    versao VARCHAR(20) NOT NULL,
    tema VARCHAR(50) DEFAULT 'claro',
    idioma VARCHAR(10) DEFAULT 'pt_BR',
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de configurações de banco de dados
CREATE TABLE IF NOT EXISTS configuracao_banco_dados (
    id BIGSERIAL PRIMARY KEY,
    tipo_banco VARCHAR(20) NOT NULL,
    host VARCHAR(100) NOT NULL,
    porta INT NOT NULL,
    nome_banco VARCHAR(50) NOT NULL,
    usuario VARCHAR(50) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    max_conexoes INT DEFAULT 10,
    timeout_conexao INT DEFAULT 30000,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de configurações de notificação
CREATE TABLE IF NOT EXISTS configuracao_notificacao (
    id BIGSERIAL PRIMARY KEY,
    email_habilitado BOOLEAN DEFAULT FALSE,
    servidor_smtp VARCHAR(100),
    porta_smtp INT,
    usuario_smtp VARCHAR(100),
    senha_smtp VARCHAR(100),
    email_remetente VARCHAR(100),
    push_habilitado BOOLEAN DEFAULT FALSE,
    api_key_push VARCHAR(100),
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de configurações de segurança
CREATE TABLE IF NOT EXISTS configuracao_seguranca (
    id BIGSERIAL PRIMARY KEY,
    tempo_sessao INT DEFAULT 30,
    max_tentativas_login INT DEFAULT 5,
    politica_senhas_forte BOOLEAN DEFAULT TRUE,
    autenticacao_dois_fatores BOOLEAN DEFAULT FALSE,
    tempo_bloqueio_conta INT DEFAULT 30,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de histórico de configurações
CREATE TABLE IF NOT EXISTS historico_configuracao (
    id BIGSERIAL PRIMARY KEY,
    tipo_configuracao VARCHAR(50) NOT NULL,
    id_configuracao BIGINT NOT NULL,
    usuario VARCHAR(100) NOT NULL,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    descricao_alteracao TEXT NOT NULL,
    valor_anterior TEXT,
    valor_novo TEXT
);

-- Inserir configurações padrão (adaptado para PostgreSQL)
INSERT INTO configuracao_geral (nome_sistema, versao, tema, idioma)
VALUES ('Sistema de Gestão', '1.0.0', 'claro', 'pt_BR')
ON CONFLICT DO NOTHING;

INSERT INTO configuracao_banco_dados (tipo_banco, host, porta, nome_banco, usuario, senha, max_conexoes, timeout_conexao)
VALUES ('POSTGRES', 'localhost', 5432, 'sistema_gestao', 'postgres', 'admin', 10, 30000)
ON CONFLICT DO NOTHING;

INSERT INTO configuracao_notificacao (email_habilitado, servidor_smtp, porta_smtp, usuario_smtp, senha_smtp, email_remetente, push_habilitado)
VALUES (false, 'smtp.example.com', 587, 'usuario@example.com', 'senha123', 'noreply@sistema.com', false)
ON CONFLICT DO NOTHING;

INSERT INTO configuracao_seguranca (tempo_sessao, max_tentativas_login, politica_senhas_forte, autenticacao_dois_fatores, tempo_bloqueio_conta)
VALUES (30, 5, true, false, 30)
ON CONFLICT DO NOTHING;
