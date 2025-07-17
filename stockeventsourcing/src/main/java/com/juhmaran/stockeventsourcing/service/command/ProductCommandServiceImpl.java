package com.juhmaran.stockeventsourcing.service.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juhmaran.stockeventsourcing.domain.aggregate.ProductAggregate;
import com.juhmaran.stockeventsourcing.domain.event.Event;
import com.juhmaran.stockeventsourcing.domain.repository.EventRepository;
import com.juhmaran.stockeventsourcing.domain.repository.EventStore;
import com.juhmaran.stockeventsourcing.dto.ProductRequest;
import com.juhmaran.stockeventsourcing.dto.StockRequest;
import com.juhmaran.stockeventsourcing.exception.AggregateNotFoundException;
import com.juhmaran.stockeventsourcing.exception.ProductAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

  private final ObjectMapper objectMapper;
  private final EventRepository eventRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  @Transactional
  public void addProduct(String sku, ProductRequest request) {
    if (!eventRepository.findByAggregateIdOrderByTimestampAsc(sku).isEmpty()) {
      throw new ProductAlreadyExistsException("Produto com SKU " + sku + " já existe.");
    }
    var aggregate = new ProductAggregate();
    aggregate.addProduct(sku, request.name(), request.color(), request.material(), request.quantity());
    saveAndPublishEvents(aggregate);
  }

  @Override
  @Transactional
  public void reserveProduct(String sku, StockRequest request) {
    ProductAggregate aggregate = loadAggregate(sku);
    aggregate.reserveProduct(request.quantity());
    saveAndPublishEvents(aggregate);
  }

  @Override
  @Transactional
  public void sellProduct(String sku, StockRequest request) {
    ProductAggregate aggregate = loadAggregate(sku);
    aggregate.sellProduct(request.quantity());
    saveAndPublishEvents(aggregate);
  }

  private ProductAggregate loadAggregate(String sku) {
    List<EventStore> eventStores = eventRepository.findByAggregateIdOrderByTimestampAsc(sku);
    if (eventStores.isEmpty()) {
      throw new AggregateNotFoundException("Produto com SKU " + sku + " não encontrado.");
    }
    List<Event> history = eventStores.stream().map(this::deserializeEvent).toList();
    return new ProductAggregate(sku, history);
  }

  private void saveAndPublishEvents(ProductAggregate aggregate) {
    List<Event> newEvents = aggregate.getUncommittedEvents();
    newEvents.forEach(event -> {
      eventRepository.save(serializeEvent(event));
      eventPublisher.publishEvent(event);
    });
    newEvents.clear();
  }

  private EventStore serializeEvent(Event event) {
    try {
      return EventStore.builder()
        .aggregateId(event.getAggregateId())
        .eventType(event.getClass().getSimpleName())
        .timestamp(event.getCreatedAt())
        .eventData(objectMapper.writeValueAsString(event))
        .build();
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Erro ao serializar o evento", e);
    }
  }

  private Event deserializeEvent(EventStore eventStore) {
    try {
      return (Event) objectMapper.readValue(eventStore.getEventData(),
        Class.forName("com.juhmaran.stockeventsourcing.domain.event" + eventStore.getEventType()));
    } catch (JsonProcessingException | ClassNotFoundException e) {
      throw new RuntimeException("Erro ao deserializer o evento", e);
    }
  }

}
