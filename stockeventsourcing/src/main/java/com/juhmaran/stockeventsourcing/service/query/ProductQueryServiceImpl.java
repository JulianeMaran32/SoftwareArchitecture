package com.juhmaran.stockeventsourcing.service.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juhmaran.stockeventsourcing.domain.event.Event;
import com.juhmaran.stockeventsourcing.domain.repository.EventRepository;
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
    return productViewRepository.findById(sku)
      .orElseThrow(() -> new ProductNotFoundException("Produto com SKU " + sku + " não encontrado."));
  }

  @Override
  public List<Event> getProductHistory(String sku) {
    var eventStores = eventRepository.findByAggregateIdOrderByTimestampAsc(sku);
    if (eventStores.isEmpty()) {
      throw new ProductNotFoundException("Nenhum histórico encontrado para o produto com SKU " + sku);
    }
    return eventStores.stream()
      .map(eventStore -> {
        try {
          return (Event) objectMapper.readValue(
            eventStore.getEventData(),
            Class.forName("com.juhmaran.stockeventsourcing.domain.event." + eventStore.getEventType()));
        } catch (JsonProcessingException | ClassNotFoundException e) {
          throw new RuntimeException("Erro ao deserializer o histórico de eventos");
        }
      }).toList();
  }
}
