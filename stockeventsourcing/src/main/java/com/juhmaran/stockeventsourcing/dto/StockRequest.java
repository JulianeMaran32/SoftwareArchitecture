package com.juhmaran.stockeventsourcing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para operações de estoque (reserva ou venda).")
public record StockRequest(

  @Schema(
    description = "Quantidade de itens para a operação.", example = "10",
    minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "A quantidade não pode ser nula")
  @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
  Integer quantity
) {
}
