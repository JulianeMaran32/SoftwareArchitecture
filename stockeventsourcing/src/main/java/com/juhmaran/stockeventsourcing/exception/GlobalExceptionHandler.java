package com.juhmaran.stockeventsourcing.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  private record ErrorResponse(int status, String error, String message, LocalDateTime timestamp) {
  }

  /**
   * Handler para erros de validação (400 Bad Request).
   * Captura exceções quando os dados de entrada (DTOs) falham na validação.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    String errors = ex.getBindingResult().getFieldErrors()
      .stream()
      .map(FieldError::getDefaultMessage)
      .collect(Collectors.joining(", "));

    return new ErrorResponse(
      HttpStatus.BAD_REQUEST.value(),
      "Erro de Validação",
      errors,
      LocalDateTime.now()
    );
  }

  /**
   * Handler para recursos não encontrados (404 Not Found).
   * Captura exceções quando um produto ou agregado não é encontrado.
   */
  @ExceptionHandler({ProductNotFoundException.class, AggregateNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleNotFoundException(RuntimeException ex) {
    return new ErrorResponse(
      HttpStatus.NOT_FOUND.value(),
      "Recurso não encontrado",
      ex.getMessage(),
      LocalDateTime.now()
    );
  }

  /**
   * Handler para conflitos de negócio (409 Conflict).
   * Captura exceções como tentativa de criar um produto que já existe ou estoque insuficiente.
   */
  @ExceptionHandler({ProductAlreadyExistsException.class, InsufficientStockException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse handleConflictException(RuntimeException ex) {
    return new ErrorResponse(
      HttpStatus.CONFLICT.value(),
      "Conflito de Negócio",
      ex.getMessage(),
      LocalDateTime.now()
    );
  }

  /**
   * Handler para entidades não processáveis (422 Unprocessable Entity).
   * Captura argumentos inválidos que não são erros de validação de sintaxe, mas de semântica de negócio.
   * Ex: quantidade negativa.
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {
    return new ErrorResponse(
      HttpStatus.UNPROCESSABLE_ENTITY.value(),
      "Requisição não processável",
      ex.getMessage(),
      LocalDateTime.now()
    );
  }

  /**
   * Handler genérico para erros inesperados (500 Internal Server Error).
   * Captura qualquer outra exceção não tratada, evitando vazar detalhes internos.
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleGenericException(Exception ex, WebRequest request) {
    // Loga o erro completo para depuração interna
    log.error("Erro inesperado na requisição: {}", request.getDescription(false), ex);

    return new ErrorResponse(
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      "Erro Interno do Servidor",
      "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.",
      LocalDateTime.now()
    );
  }

}
