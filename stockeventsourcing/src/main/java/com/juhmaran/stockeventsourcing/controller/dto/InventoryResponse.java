package com.juhmaran.stockeventsourcing.controller.dto;

import java.time.Instant;

/**
 * DTO para a resposta da consulta de inventário de um produto.
 * <p>
 * Created by Juliane Maran
 *
 * @since 12/07/2025
 */
public record InventoryResponse(
  String sku,
  String name,
  String color,
  String material,
  int availableQuantity,
  int reservedQuantity,
  int soldQuantity,
  Instant lastModified
) {
}
