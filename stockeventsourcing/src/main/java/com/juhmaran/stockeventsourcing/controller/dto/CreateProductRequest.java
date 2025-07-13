package com.juhmaran.stockeventsourcing.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para a requisição de criação de um novo produto/estoque.
 * <p>
 * Created by Juliane Maran
 *
 * @since 12/07/2025
 */
public record CreateProductRequest(
  @NotBlank(message = "O campo 'sku' é obrigatório.")
  String sku,

  @NotBlank(message = "O campo 'name' é obrigatório.")
  String name,

  @NotBlank(message = "O campo 'color' é obrigatório.")
  String color,

  @NotBlank(message = "O campo 'material' é obrigatório.")
  String material,

  @NotNull(message = "O campo 'initialQuantity' é obrigatório.")
  @Min(value = 1, message = "A quantidade inicial deve ser de no mínimo 1.")
  Integer initialQuantity
) {
}
