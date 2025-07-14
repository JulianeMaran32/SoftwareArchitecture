package com.juhmaran.stockeventsourcing.exception;

/**
 * Created by Juliane Maran
 * @since 13/07/2025
 */
public class AggregateNotFoundException extends RuntimeException {
  public AggregateNotFoundException(String message) {
    super(message);
  }
}
