package com.juhmaran.stockeventsourcing.exception;

/**
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
public class ProductAlreadyExistsException extends RuntimeException {
  public ProductAlreadyExistsException(String message) {
    super(message);
  }
}
