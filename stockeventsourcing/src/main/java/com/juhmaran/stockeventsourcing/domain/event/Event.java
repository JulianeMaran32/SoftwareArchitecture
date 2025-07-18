package com.juhmaran.stockeventsourcing.domain.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@SuperBuilder
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = ProductAddedEvent.class, name = "ProductAdded"),
  @JsonSubTypes.Type(value = ProductReservedEvent.class, name = "ProductReserved"),
  @JsonSubTypes.Type(value = ProductSoldEvent.class, name = "ProductSold")
})
public abstract class Event {

  private final UUID id = UUID.randomUUID();
  private final LocalDateTime createdAt = LocalDateTime.now();
  private String aggregateId; // SKU do produto

}
