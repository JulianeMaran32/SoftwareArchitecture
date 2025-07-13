package com.juhmaran.stockeventsourcing.domain.command;

/**
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
public final class ProductCommands {

  private ProductCommands() {
  }

  public record AddProductStockCommand(
    String sku,
    String name,
    String color,
    String material,
    int initialQuantity
  ) {
  }

  public record ReserveProductCommand(String sku, int quantity) {
  }

  public record SellProductCommand(String sku, int quantity) {
  }

}
