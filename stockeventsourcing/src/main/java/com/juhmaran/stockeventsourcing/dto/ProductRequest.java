package com.juhmaran.stockeventsourcing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para a criação de um novo produto.")
public record ProductRequest(
  @Schema(
    description = "Nome do produto.", example = "Camiseta Básica",
    requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "O nome não pode estar em branco")
  String name,

  @Schema(
    description = "Cor do produto.", example = "Azul Marinho",
    requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "A cor não pode estar em branco")
  String color,

  @Schema(
    description = "Material de fabricação do produto.", example = "Algodão",
    requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "O material não pode estar em branco")
  String material,

  @Schema(
    description = "Quantidade inicial de itens a serem adicionados ao estoque.", example = "100",
    minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "A quantidade não pode ser nula")
  @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
  Integer quantity
) {
}
