-- Arquivo: 01_schema_creation.sql
-- Descrição: Script para criar a tabela 'event_store' e seus índices.
-- Nota: Este script é para referência, pois o Hibernate (ddl-auto)
--       geralmente cuida da criação da tabela. É útil para configurar
--       um banco de dados do zero manualmente.

CREATE TABLE IF NOT EXISTS event_store (
    id UUID PRIMARY KEY,
    timestamp TIMESTAMP NOT NULL,
    aggregate_id VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    event_data TEXT NOT NULL
);

-- Criar um índice no aggregate_id para otimizar as buscas por histórico de produto.
CREATE INDEX IF NOT EXISTS idx_event_store_aggregate_id ON event_store (aggregate_id);

-- Exibindo a estrutura da tabela para confirmação
\d event_store;