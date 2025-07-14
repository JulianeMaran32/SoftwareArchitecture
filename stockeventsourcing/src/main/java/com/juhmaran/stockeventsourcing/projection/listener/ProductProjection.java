package com.juhmaran.stockeventsourcing.projection.listener;

import com.juhmaran.stockeventsourcing.domain.event.ProductAddedEvent;
import com.juhmaran.stockeventsourcing.domain.event.ProductReservedEvent;
import com.juhmaran.stockeventsourcing.domain.event.ProductSoldEvent;
import com.juhmaran.stockeventsourcing.projection.model.ProductView;
import com.juhmaran.stockeventsourcing.projection.repository.ProductViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by Juliane Maran
 *
 * @since 14/07/2025
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProductProjection {

  private final ProductViewRepository repository;

  /**
   * @param event Product Added
   */
  @EventListener
  public void on(ProductAddedEvent event) {
    log.info("Projecting ProductAddedEvent: {}", event.getAggregateId());
    ProductView view = ProductView.builder()
      .sku(event.getAggregateId())
      .name(event.getName())
      .color(event.getColor())
      .material(event.getMaterial())
      .availableQuantity(event.getQuantity())
      .reservedQuantity(0)
      .soldQuantity(0)
      .build();
    repository.save(view);
  }

  /**
   * @param event Product Reserved
   */
  @EventListener
  public void on(ProductReservedEvent event) {
    log.info("Projecting ProductReservedEvent: {}", event.getAggregateId());
    repository.findById(event.getAggregateId()).ifPresent(view -> {
      view.setAvailableQuantity(view.getAvailableQuantity() - event.getQuantity());
      view.setReservedQuantity(view.getReservedQuantity() + event.getQuantity());
      repository.save(view);
    });
  }

  /**
   * @param event Product Sold
   */
  @EventListener
  public void on(ProductSoldEvent event) {
    log.info("Projecting ProductSoldEvent: {}", event.getAggregateId());
    repository.findById(event.getAggregateId()).ifPresent(view -> {
      view.setReservedQuantity(view.getReservedQuantity() - event.getQuantity());
      view.setSoldQuantity(view.getSoldQuantity() + event.getQuantity());
      repository.save(view);
    });
  }

}
