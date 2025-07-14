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
public class ProductReservedEvent extends Event {

  private int quantity;

}
