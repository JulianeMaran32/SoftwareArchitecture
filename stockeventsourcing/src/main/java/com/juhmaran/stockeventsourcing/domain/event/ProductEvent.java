package com.juhmaran.stockeventsourcing.domain.event;

/**
 * Interface selada que define o contrato para todos os eventos que podem ocorrer no agregado de Produto. <br>
 * Atua como o tipo base para polimorfismo no aggregate.
 * <p>
 * Propósito: Definir o `supertype`. É o que o `ProductAggregate` usa para manipular qualquer evento de forma genérica
 * (ex: na lista `List<ProductEvent> uncommittedChanges`).
 *
 * <p>
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
public sealed interface ProductEvent permits
  ProductEvents.ProductAddedToStockEvent,
  ProductEvents.ProductReservedEvent,
  ProductEvents.ProductReservationCancelledEvent,
  ProductEvents.ProductSoldEvent {
}