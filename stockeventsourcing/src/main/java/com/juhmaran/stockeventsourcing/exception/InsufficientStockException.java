package com.juhmaran.stockeventsourcing.exception;

/**
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
public class InsufficientStockException extends RuntimeException {
  public InsufficientStockException(String message) {
    super(message);
  }
}
