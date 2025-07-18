package com.juhmaran.stockeventsourcing.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class ProductAddedEvent extends Event {

  private String name;
  private String color;
  private String material;
  private int quantity;

}
