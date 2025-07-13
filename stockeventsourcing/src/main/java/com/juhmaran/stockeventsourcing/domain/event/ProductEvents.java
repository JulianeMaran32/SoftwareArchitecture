package com.juhmaran.stockeventsourcing.domain.event;

/**
 * Classe final que atua como um namespace para agrupar todas as implementações concretas de eventos de produto. <br>
 * Isso mantém o domínio coeso e organizado.
 * <p>
 * Propósito: Agrupar logicamente todos os fatos que podem ocorrer.
 * Acessamos um evento específico através de `ProductEvents.ProductSoldEvent`, o que torna o código muito explícito
 * sobre qual conjunto de eventos estamos usando.
 *
 * <p>
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
public final class ProductEvents {

  // Construtor privado para impedir a instanciação desta classe utilitária.
  private ProductEvents() {
  }

  // --- Implementações Concretas dos Eventos ---

  public record ProductAddedToStockEvent(
    String sku,
    String name,
    String color,
    String material,
    int quantity
  ) implements ProductEvent {
  } // Implementa o contrato

  public record ProductReservedEvent(int quantity) implements ProductEvent {
  } // Implementa o contrato

  public record ProductSoldEvent(int quantity) implements ProductEvent {
  } // Implementa o contrato

  public record ProductReservationCancelledEvent(int quantity) implements ProductEvent {
  } // Implementa o contrato

}
