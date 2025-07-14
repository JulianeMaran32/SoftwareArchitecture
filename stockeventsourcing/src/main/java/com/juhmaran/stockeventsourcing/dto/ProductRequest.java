package com.juhmaran.stockeventsourcing.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
public record ProductRequest(
  @NotBlank String name,
  @NotBlank String color,
  @NotBlank String material,
  @NotNull @Min(1) Integer quantity
) {
}
