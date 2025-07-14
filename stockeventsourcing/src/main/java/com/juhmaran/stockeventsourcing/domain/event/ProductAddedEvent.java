package com.juhmaran.stockeventsourcing.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
@Getter
@SuperBuilder
@NoArgsConstructor
public class ProductAddedEvent extends Event {

  private String name;
  private String color;
  private String material;
  private int quantity;

}
