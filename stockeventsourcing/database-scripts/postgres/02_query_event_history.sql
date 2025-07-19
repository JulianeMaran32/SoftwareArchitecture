-- Arquivo: 02_query_event_history.sql
-- Descrição: Scripts para inspecionar os eventos salvos no PostgreSQL.

-- ==============================================================================
-- CONSULTAR HISTÓRICO DE EVENTOS PARA UM SKU ESPECÍFICO
-- ==============================================================================
-- Instruções:
-- 1. Conecte-se ao banco 'eventstore' no seu cliente SQL.
-- 2. Substitua 'SKU-TSHIRT-01' pelo SKU que deseja investigar.
-- 3. Execute a query.

SELECT
    id,
    timestamp,
    aggregate_id,
    event_type,
    jsonb_pretty(event_data::jsonb) AS event_data_formatted -- Formata o JSON para melhor leitura
FROM
    event_store
WHERE
    aggregate_id = 'SKU-TSHIRT-01'
ORDER BY
    timestamp ASC;


-- ==============================================================================
-- CONTAR EVENTOS POR TIPO
-- ==============================================================================
-- Útil para ter uma visão geral da distribuição de eventos no sistema.

SELECT
    event_type,
    COUNT(*) as total_events
FROM
    event_store
GROUP BY
    event_type
ORDER BY
    total_events DESC;