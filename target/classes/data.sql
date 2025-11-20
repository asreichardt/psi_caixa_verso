
CREATE TABLE IF NOT EXISTS produto (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    rentabilidade DOUBLE NOT NULL,
    risco VARCHAR(50) NOT NULL,
    valor_minimo DOUBLE NOT NULL,
    prazo_minimo_meses INTEGER NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT true
);


INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('CDB Banco ABC 2026', 'CDB', 0.12, 'Baixo', 1000.00, 6, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('CDB Banco XYZ 2027', 'CDB', 0.15, 'Baixo', 500.00, 12, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('Fundo Conservador XPTO', 'Fundo', 0.08, 'Baixo', 1000.00, 3, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('Fundo Moderado ABCD', 'Fundo', 0.12, 'Médio', 2000.00, 6, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('Fundo Agressivo EFGH', 'Fundo', 0.18, 'Alto', 5000.00, 12, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('Tesouro Direto IPCA+', 'Tesouro', 0.06, 'Baixo', 100.00, 12, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('Ações Blue Chips', 'Ações', 0.20, 'Alto', 10000.00, 24, true);

INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('CDB Premium Banco Brasil', 'CDB', 0.135, 'Baixo', 2000.00, 12, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('Fundo Imobiliário Shopping Center', 'FII', 0.095, 'Médio', 500.00, 6, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('LCI Banco Verde', 'LCI', 0.088, 'Baixo', 1000.00, 12, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('Ações Tecnologia', 'Ações', 0.255, 'Alto', 3000.00, 18, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('Tesouro Selic 2029', 'Tesouro', 0.072, 'Baixo', 50.00, 24, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('Fundo Multimercado Global', 'Fundo', 0.142, 'Médio', 2500.00, 12, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('Debênture Infraestrutura', 'Debênture', 0.118, 'Médio', 1500.00, 24, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('CDB Banco Digital', 'CDB', 0.128, 'Baixo', 100.00, 3, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('Fundo Cambial Dólar', 'Fundo', 0.068, 'Baixo', 2000.00, 12, true);
INSERT INTO produto (nome, tipo, rentabilidade, risco, valor_minimo, prazo_minimo_meses, ativo) VALUES ('Ações Small Caps', 'Ações', 0.315, 'Alto', 5000.00, 36, true);


CREATE TABLE IF NOT EXISTS simulacao (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    cliente_id BIGINT NOT NULL,
    produto VARCHAR(255) NOT NULL,
    valor_investido DOUBLE NOT NULL,
    valor_final DOUBLE NOT NULL,
    prazo_meses INTEGER NOT NULL,
    data_simulacao TIMESTAMP NOT NULL,
    tipo_produto VARCHAR(100) NOT NULL
);


INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES (101, 'CDB Banco ABC 2026', 5000.00, 5300.00, 6, '2025-10-01 09:15:00', 'CDB');
INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES (102, 'Fundo Conservador XPTO', 10000.00, 10200.00, 3, '2025-10-01 10:30:00', 'Fundo');
INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES (103, 'CDB Banco XYZ 2027', 15000.00, 17250.00, 12, '2025-10-02 11:45:00', 'CDB');
INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES (101, 'Tesouro Direto IPCA+', 8000.00, 8480.00, 12, '2025-10-02 14:20:00', 'Tesouro');
INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES (104, 'Fundo Moderado ABCD', 12000.00, 13440.00, 6, '2025-10-03 08:50:00', 'Fundo');
INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(101, 'CDB Banco ABC 2026', 5000.00, 5300.00, 6, '2025-10-01 09:15:00', 'CDB');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) 
VALUES (101, 'CDB Banco ABC 2026', 5000.00, 5300.00, 6, '2025-10-01 09:15:00', 'CDB');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) 
VALUES (102, 'Fundo Conservador XPTO', 10000.00, 10200.00, 3, '2025-10-01 10:30:00', 'Fundo');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) 
VALUES (103, 'CDB Banco XYZ 2027', 15000.00, 17250.00, 12, '2025-10-02 11:45:00', 'CDB');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) 
VALUES (101, 'Tesouro Direto IPCA+', 8000.00, 8480.00, 12, '2025-10-02 14:20:00', 'Tesouro');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) 
VALUES (104, 'Fundo Moderado ABCD', 12000.00, 13440.00, 6, '2025-10-03 08:50:00', 'Fundo');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(102, 'Fundo Conservador XPTO', 10000.00, 10200.00, 3, '2025-10-01 10:30:00', 'Fundo');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(103, 'CDB Banco XYZ 2027', 15000.00, 17250.00, 12, '2025-10-02 11:45:00', 'CDB');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(101, 'Tesouro Direto IPCA+', 8000.00, 8480.00, 12, '2025-10-02 14:20:00', 'Tesouro');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(104, 'Fundo Moderado ABCD', 12000.00, 13440.00, 6, '2025-10-03 08:50:00', 'Fundo');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(105, 'Ações Blue Chips', 25000.00, 27500.00, 12, '2025-10-03 16:10:00', 'Ações');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(102, 'CDB Premium Banco Brasil', 7000.00, 7945.00, 12, '2025-10-04 09:30:00', 'CDB');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(106, 'Fundo Imobiliário Shopping Center', 6000.00, 6570.00, 12, '2025-10-04 11:15:00', 'FII');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(107, 'LCI Banco Verde', 9000.00, 9792.00, 12, '2025-10-05 13:40:00', 'LCI');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(103, 'Ações Tecnologia', 15000.00, 18825.00, 12, '2025-10-05 15:25:00', 'Ações');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(108, 'Tesouro Selic 2029', 5000.00, 5360.00, 12, '2025-10-06 10:05:00', 'Tesouro');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(101, 'Fundo Multimercado Global', 18000.00, 20556.00, 12, '2025-10-07 08:45:00', 'Fundo');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(109, 'Debênture Infraestrutura', 11000.00, 12958.00, 24, '2025-10-07 14:30:00', 'Debênture');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(110, 'CDB Banco Digital', 3000.00, 3084.00, 3, '2025-10-08 09:20:00', 'CDB');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(102, 'Fundo Cambial Dólar', 14000.00, 14952.00, 12, '2025-10-08 16:45:00', 'Fundo');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(111, 'Ações Small Caps', 20000.00, 26300.00, 12, '2025-10-09 11:10:00', 'Ações');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(103, 'CDB Banco ABC 2026', 6500.00, 6890.00, 6, '2025-10-09 13:55:00', 'CDB');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(112, 'Fundo Agressivo EFGH', 22000.00, 25960.00, 12, '2025-10-10 10:30:00', 'Fundo');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(113, 'CDB Banco XYZ 2027', 8000.00, 9200.00, 12, '2025-10-10 15:20:00', 'CDB');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(101, 'Ações Blue Chips', 35000.00, 38500.00, 12, '2025-10-11 08:15:00', 'Ações');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(114, 'Tesouro Direto IPCA+', 4500.00, 4770.00, 12, '2025-10-11 12:40:00', 'Tesouro');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(115, 'Fundo Conservador XPTO', 7500.00, 7650.00, 3, '2025-10-12 09:50:00', 'Fundo');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(102, 'CDB Premium Banco Brasil', 9500.00, 10783.75, 12, '2025-10-12 14:25:00', 'CDB');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(116, 'Fundo Imobiliário Shopping Center', 8500.00, 9307.50, 12, '2025-10-13 10:10:00', 'FII');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(117, 'LCI Banco Verde', 12500.00, 13600.00, 12, '2025-10-13 16:35:00', 'LCI');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(103, 'Ações Tecnologia', 28000.00, 35140.00, 12, '2025-10-14 11:45:00', 'Ações');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(118, 'Tesouro Selic 2029', 6200.00, 6646.40, 12, '2025-10-14 13:20:00', 'Tesouro');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(119, 'Fundo Multimercado Global', 16500.00, 18843.00, 12, '2025-10-15 09:30:00', 'Fundo');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(120, 'Debênture Infraestrutura', 13500.00, 15903.00, 24, '2025-10-15 15:15:00', 'Debênture');

INSERT INTO simulacao (cliente_id, produto, valor_investido, valor_final, prazo_meses, data_simulacao, tipo_produto) VALUES
(101, 'CDB Banco Digital', 4200.00, 4317.60, 3, '2025-10-16 10:50:00', 'CDB');


-- Criar tabela de telemetria
CREATE TABLE IF NOT EXISTS telemetria (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome_servico VARCHAR(255) NOT NULL,
    tempo_resposta_ms BIGINT NOT NULL,
    data_chamada TIMESTAMP NOT NULL,
    sucesso BOOLEAN NOT NULL
);

INSERT INTO telemetria (nome_servico, tempo_resposta_ms, data_chamada, sucesso) VALUES ('simulacao-service', 150, '2025-10-01 10:00:00', true);
INSERT INTO telemetria (nome_servico, tempo_resposta_ms, data_chamada, sucesso) VALUES ('produto-service', 80, '2025-10-01 11:00:00', true);
INSERT INTO telemetria (nome_servico, tempo_resposta_ms, data_chamada, sucesso) VALUES ('simulacao-service', 200, '2025-10-02 09:00:00', true);