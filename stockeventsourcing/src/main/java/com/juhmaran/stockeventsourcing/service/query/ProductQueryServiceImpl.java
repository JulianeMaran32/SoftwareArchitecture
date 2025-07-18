package com.juhmaran.stockeventsourcing.service.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juhmaran.stockeventsourcing.domain.event.Event;
import com.juhmaran.stockeventsourcing.domain.repository.EventRepository;
import com.juhmaran.stockeventsourcing.exception.EventDeserializationException;
import com.juhmaran.stockeventsourcing.exception.ProductNotFoundException;
import com.juhmaran.stockeventsourcing.projection.model.ProductView;
import com.juhmaran.stockeventsourcing.projection.repository.ProductViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

  private final ProductViewRepository productViewRepository;
  private final EventRepository eventRepository;
  private final ObjectMapper objectMapper;

  @Override
  public ProductView getProductStock(String sku) {
    log.info("Buscando visão de estoque para o produto com SKU: {}", sku);
    return productViewRepository.findById(sku)
      .orElseThrow(() -> {
        log.warn("Visão de produto não encontrada para o SKU: {}", sku);
        return new ProductNotFoundException("Produto com SKU " + sku + " não encontrado.");
      });
  }

  @Override
  public List<Event> getProductHistory(String sku) {
    log.info("Buscando histórico de eventos para o produto com SKU: {}", sku);
    var eventStores = eventRepository.findByAggregateIdOrderByTimestampAsc(sku);
    if (eventStores.isEmpty()) {
      log.warn("Nenhum histórico encontrado para o produto com SKU: {}", sku);
      throw new ProductNotFoundException("Nenhum histórico encontrado para o produto com SKU " + sku);
    }
    return eventStores.stream()
      .map(eventStore -> {
        try {
          return objectMapper.readValue(eventStore.getEventData(), Event.class);
        } catch (JsonProcessingException e) {
          log.error("Falha ao deserializar evento do histórico para o SKU: {}", sku, e);
          throw new EventDeserializationException("Erro ao deserializar o histórico de eventos", e);
        }
      }).toList();
  }

}
