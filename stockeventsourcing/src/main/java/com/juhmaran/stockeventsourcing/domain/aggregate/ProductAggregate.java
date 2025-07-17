package com.juhmaran.stockeventsourcing.domain.aggregate;

import com.juhmaran.stockeventsourcing.domain.event.Event;
import com.juhmaran.stockeventsourcing.domain.event.ProductAddedEvent;
import com.juhmaran.stockeventsourcing.domain.event.ProductReservedEvent;
import com.juhmaran.stockeventsourcing.domain.event.ProductSoldEvent;
import com.juhmaran.stockeventsourcing.exception.InsufficientStockException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor
public class ProductAggregate {

  private String sku;
  private String name;
  private String color;
  private String material;
  private int availableQuantity;
  private int reservedQuantity;
  private int soldQuantity;

  private final List<Event> uncommittedEvents = new ArrayList<>();

  // Construtor para reconstruir o estado a partir de eventos
  public ProductAggregate(String sku, List<Event> eventHistory) {
    this.sku = sku;
    eventHistory.forEach(this::applyChange);
  }

  private void applyChange(Event event) {
    if (event instanceof ProductAddedEvent e) {
      apply(e);
    }
    if (event instanceof ProductReservedEvent e) {
      apply(e);
    }
    if (event instanceof ProductSoldEvent e) {
      apply(e);
    }
  }

  private void applyNewChange(Event event) {
    applyChange(event);
    uncommittedEvents.add(event);
  }

  // ---- Métodos de negócio que geram eventos ----
  public void addProduct(String sku, String name, String color, String material, int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive.");
    }
    var event = ProductAddedEvent.builder()
      .aggregateId(sku)
      .name(name)
      .color(color)
      .material(material)
      .quantity(quantity)
      .build();
    applyNewChange(event);
  }

  public void reserveProduct(int quantity) {
    if (quantity > this.availableQuantity) {
      throw new InsufficientStockException("Not enough available stock to reserve.");
    }
    var event = ProductReservedEvent.builder()
      .aggregateId(this.sku)
      .quantity(quantity)
      .build();
    applyNewChange(event);
  }

  public void sellProduct(int quantity) {
    if (quantity > this.reservedQuantity) {
      throw new InsufficientStockException("Not enough reserved stock to sell.");
    }
    var event = ProductSoldEvent.builder()
      .aggregateId(this.sku)
      .quantity(quantity)
      .build();
    applyNewChange(event);
  }

  // ---- Métodos privados para aplicar o estado ----
  private void apply(ProductAddedEvent event) {
    this.sku = event.getAggregateId();
    this.name = event.getName();
    this.color = event.getColor();
    this.material = event.getMaterial();
    this.availableQuantity += event.getQuantity();
  }

  private void apply(ProductReservedEvent event) {
    this.availableQuantity -= event.getQuantity();
    this.reservedQuantity += event.getQuantity();
  }

  private void apply(ProductSoldEvent event) {
    this.reservedQuantity -= event.getQuantity();
    this.soldQuantity += event.getQuantity();
  }

}