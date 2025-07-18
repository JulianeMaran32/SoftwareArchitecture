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
import com.juhmaran.stockeventsourcing.exception.EventDeserializationException;
import com.juhmaran.stockeventsourcing.exception.EventSerializationException;
import com.juhmaran.stockeventsourcing.exception.ProductAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

  private final ObjectMapper objectMapper;
  private final EventRepository eventRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  @Transactional
  public void addProduct(String sku, ProductRequest request) {
    log.info("Iniciando processo de adição do produto com SKU: {}", sku);
    if (!eventRepository.findByAggregateIdOrderByTimestampAsc(sku).isEmpty()) {
      log.warn("Tentativa de criar um produto com SKU já existente: {}", sku);
      throw new ProductAlreadyExistsException("Produto com SKU " + sku + " já existe.");
    }
    var aggregate = new ProductAggregate();
    aggregate.addProduct(sku, request.name(), request.color(), request.material(), request.quantity());
    saveAndPublishEvents(aggregate);
    log.info("Produto com SKU: {} adicionado com sucesso.", sku);
  }

  @Override
  @Transactional
  public void reserveProduct(String sku, StockRequest request) {
    log.info("Iniciando processo de reserva de {} item(s) para o produto com SKU: {}", request.quantity(), sku);
    ProductAggregate aggregate = loadAggregate(sku);
    aggregate.reserveProduct(request.quantity());
    saveAndPublishEvents(aggregate);
    log.info("Reserva de {} item(s) para o produto com SKU: {} realizada com sucesso.", request.quantity(), sku);
  }

  @Override
  @Transactional
  public void sellProduct(String sku, StockRequest request) {
    log.info("Iniciando processo de venda de {} item(s) do produto com SKU: {}", request.quantity(), sku);
    ProductAggregate aggregate = loadAggregate(sku);
    aggregate.sellProduct(request.quantity());
    saveAndPublishEvents(aggregate);
    log.info("Venda de {} item(s) do produto com SKU: {} realizada com sucesso.", request.quantity(), sku);
  }

  private ProductAggregate loadAggregate(String sku) {
    log.debug("Carregando agregado para o SKU: {}", sku);
    List<EventStore> eventStores = eventRepository.findByAggregateIdOrderByTimestampAsc(sku);
    if (eventStores.isEmpty()) {
      log.warn("Nenhum evento encontrado para o agregado com SKU: {}", sku);
      throw new AggregateNotFoundException("Produto com SKU " + sku + " não encontrado.");
    }
    List<Event> history = eventStores.stream().map(this::deserializeEvent).toList();
    log.debug("Agregado para o SKU: {} reconstruído a partir de {} evento(s).", sku, history.size());
    return new ProductAggregate(sku, history);
  }

  private void saveAndPublishEvents(ProductAggregate aggregate) {
    List<Event> newEvents = aggregate.getUncommittedEvents();
    log.debug("Salvando e publicando {} novo(s) evento(s) para o agregado SKU: {}", newEvents.size(), aggregate.getSku());
    newEvents.forEach(event -> {
      EventStore eventStore = serializeEvent(event);
      eventRepository.save(eventStore);
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
      log.error("Falha ao serializar evento do tipo {} para o agregado {}", event.getClass().getSimpleName(), event.getAggregateId(), e);
      throw new EventSerializationException("Erro ao serializar o evento", e);
    }
  }

  private Event deserializeEvent(EventStore eventStore) {
    try {
      return objectMapper.readValue(eventStore.getEventData(), Event.class);
    } catch (JsonProcessingException e) {
      log.error("Falha ao deserializar evento do tipo {} para o agregado {}", eventStore.getEventType(), eventStore.getAggregateId(), e);
      throw new EventDeserializationException("Erro ao deserializar o evento", e);
    }
  }

}
