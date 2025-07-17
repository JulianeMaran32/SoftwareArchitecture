package com.juhmaran.stockeventsourcing.exception;

/**
 * Created by Juliane Maran
 * @since 16/07/2025
 */
public class EventDeserializationException extends RuntimeException {
  public EventDeserializationException(String message) {
    super(message);
  }
}
