package com.juhmaran.stockeventsourcing.exception;

/**
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String message) {
    super(message);
  }
}
