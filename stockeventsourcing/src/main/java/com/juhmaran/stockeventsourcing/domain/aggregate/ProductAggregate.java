package com.juhmaran.stockeventsourcing.domain.aggregate;

import com.juhmaran.stockeventsourcing.domain.event.ProductEvent;
import com.juhmaran.stockeventsourcing.domain.event.ProductEvents;
import com.juhmaran.stockeventsourcing.exception.InsufficientStockException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
@Getter
@Slf4j
public class ProductAggregate {

  private String sku;
  private String name;
  private int availableQuantity;
  private int reservedQuantity;
  private int soldQuantity;

  private final List<ProductEvent> uncommittedChanges = new ArrayList<>();

  public static ProductAggregate rehydrate(List<ProductEvent> history) {
    ProductAggregate aggregate = new ProductAggregate();
    history.forEach(aggregate::apply);
    return aggregate;
  }

  public void addProduct(String sku, String name, String color, String material, int quantity) {
    if (this.sku != null) {
      throw new IllegalStateException("Product " + sku + " already exists.");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Initial quantity must be positive.");
    }
    raiseEvent(new ProductEvents.ProductAddedToStockEvent(sku, name, color, material, quantity));
  }

  public void reserveStock(int quantityToReserve) {
    if (quantityToReserve <= 0) {
      throw new IllegalArgumentException("Quantity to reserve must be positive.");
    }
    if (availableQuantity < quantityToReserve) {
      throw new InsufficientStockException("Not enough available stock for SKU " + sku + ". Requested: " +
        quantityToReserve + ", Available: " + availableQuantity);
    }
    raiseEvent(new ProductEvents.ProductReservedEvent(quantityToReserve));
  }

  public void sellStock(int quantityToSell) {
    if (quantityToSell <= 0) {
      throw new IllegalArgumentException("Quantity to sell must be positive.");
    }
    if (reservedQuantity < quantityToSell) {
      throw new InsufficientStockException("Not enough reserved stock for SKU " + sku + " to sell. Requested: " +
        quantityToSell + ", Reserved: " + reservedQuantity);
    }
    raiseEvent(new ProductEvents.ProductSoldEvent(quantityToSell));
  }

  private void raiseEvent(ProductEvent event) {
    apply(event);
    uncommittedChanges.add(event);
  }

  private void apply(ProductEvent event) {
    log.debug("Applying event: {}", event.getClass().getSimpleName());
    switch (event) {
      case ProductEvents.ProductAddedToStockEvent e -> {
        this.sku = e.sku();
        this.name = e.name();
        this.availableQuantity = e.quantity();
      }
      case ProductEvents.ProductReservedEvent e -> {
        this.availableQuantity -= e.quantity();
        this.reservedQuantity += e.quantity();
      }
      case ProductEvents.ProductSoldEvent e -> {
        this.reservedQuantity -= e.quantity();
        this.soldQuantity += e.quantity();
      }
      case ProductEvents.ProductReservationCancelledEvent e -> {
        this.reservedQuantity -= e.quantity();
        this.availableQuantity += e.quantity();
      }
    }
    log.debug("State after event: available={}, reserved={}, sold={}",
      availableQuantity, reservedQuantity, soldQuantity);
  }

}
