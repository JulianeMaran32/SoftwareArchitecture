package com.juhmaran.stockeventsourcing.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para requisições que modificam a quantidade (reservar, vender).
 * <p>
 * Created by Juliane Maran
 *
 * @since 12/07/2025
 */
public record UpdateStockRequest(
  @NotNull(message = "O campo 'quantity' é obrigatório.")
  @Min(value = 1, message = "A quantidade deve ser de no mínimo 1.")
  Integer quantity
) {
}
