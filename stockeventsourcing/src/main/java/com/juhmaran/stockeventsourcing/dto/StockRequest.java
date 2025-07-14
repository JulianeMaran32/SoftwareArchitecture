package com.juhmaran.stockeventsourcing.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
public record StockRequest(
  @NotNull @Min(1) Integer quantity
) {
}
