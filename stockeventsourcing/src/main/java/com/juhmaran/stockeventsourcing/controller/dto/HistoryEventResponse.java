package com.juhmaran.stockeventsourcing.controller.dto;

import java.time.Instant;

/**
 * DTO para a resposta da consulta do histórico de transações de um produto.
 * <p>
 * Created by Juliane Maran
 *
 * @since 12/07/2025
 */
public record HistoryEventResponse(
  Long id,
  String eventType,
  long version,
  String eventData,
  Instant timestamp
) {
}
