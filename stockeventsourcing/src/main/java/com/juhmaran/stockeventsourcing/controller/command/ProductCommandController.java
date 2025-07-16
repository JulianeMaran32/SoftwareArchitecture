package com.juhmaran.stockeventsourcing.controller.command;

import com.juhmaran.stockeventsourcing.dto.ProductRequest;
import com.juhmaran.stockeventsourcing.dto.StockRequest;
import com.juhmaran.stockeventsourcing.service.command.ProductCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(
  name = "Product Command",
  description = "Endpoints para criar e alterar o estado de produtos (operações de escrita).")
public class ProductCommandController {

  private final ProductCommandService commandService;

  @Operation(
    operationId = "addProduct",
    summary = "Adicionar um novo produto",
    description = "Cria um novo produto no sistema com base no SKU e nos dados fornecidos. " +
      "A operação é processada de forma assíncrona.")
  @ApiResponse(
    responseCode = "202",
    description = "Requisição aceita. O evento de criação do produto foi enviado para processamento.")
  @ApiResponse(
    responseCode = "400",
    description = "Requisição inválida. Verifique os dados enviados no corpo da requisição.")
  @ApiResponse(
    responseCode = "409",
    description = "Conflito. O produto com o SKU informado já existe no sistema.")
  @ApiResponse(
    responseCode = "422",
    description = "Entidade não processável. A requisição está bem formada, " +
      "mas não pôde ser processada devido a erros de negócio.")
  @ApiResponse(
    responseCode = "500",
    description = "Erro interno no servidor.")
  @PostMapping("/{sku}/add")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void addProduct(
    @Parameter(description = "Identificador único do produto (Stock Keeping Unit).",
      required = true, example = "SKU-001-XYZ")
    @PathVariable(name = "sku") String sku,
    @RequestBody @Valid ProductRequest request) {
    commandService.addProduct(sku, request);
  }

  @Operation(
    operationId = "reserveProduct",
    summary = "Reservar uma quantidade de um produto",
    description = "Reserva uma quantidade específica de um produto existente, diminuindo o estoque disponível, " +
      "mas ainda não o confirmando como vendido. A operação é processada de forma assíncrona.")
  @ApiResponse(
    responseCode = "202",
    description = "Requisição aceita. O evento de reserva de produto foi enviado para processamento.")
  @ApiResponse(
    responseCode = "400",
    description = "Requisição inválida. Por exemplo, a quantidade para reserva é zero ou negativa.")
  @ApiResponse(
    responseCode = "404",
    description = "Produto não encontrado. O SKU informado não corresponde a nenhum produto existente.")
  @ApiResponse(
    responseCode = "409",
    description = "Conflito de estado. Por exemplo, tentar reservar uma quantidade maior que a disponível em estoque.")
  @ApiResponse(
    responseCode = "422",
    description = "Entidade não processável. A requisição está bem formada, mas não pôde ser processada.")
  @ApiResponse(
    responseCode = "500",
    description = "Erro interno no servidor.")
  @PostMapping("/{sku}/reserve")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void reserveProduct(
    @Parameter(description = "Identificador único do produto (Stock Keeping Unit).",
      required = true, example = "SKU-001-XYZ")
    @PathVariable(name = "sku") String sku,
    @RequestBody @Valid StockRequest request) {
    commandService.reserveProduct(sku, request);
  }

  @Operation(
    operationId = "sellProduct",
    summary = "Vender uma quantidade de um produto",
    description = "Registra a venda de uma quantidade de um produto, efetivamente removendo-a do estoque. " +
      "A operação é processada de forma assíncrona.")
  @ApiResponse(
    responseCode = "202",
    description = "Requisição aceita. O evento de venda de produto foi enviado para processamento.")
  @ApiResponse(
    responseCode = "400",
    description = "Requisição inválida. Por exemplo, a quantidade para venda é zero ou negativa.")
  @ApiResponse(
    responseCode = "404",
    description = "Produto não encontrado. O SKU informado não corresponde a nenhum produto existente.")
  @ApiResponse(
    responseCode = "409",
    description = "Conflito de estado. Por exemplo, tentar vender uma quantidade maior que a disponível em estoque.")
  @ApiResponse(
    responseCode = "422",
    description = "Entidade não processável. A requisição está bem formada, mas não pôde ser processada.")
  @ApiResponse(
    responseCode = "500",
    description = "Erro interno no servidor.")
  @PostMapping("/{sku}/sell")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void sellProduct(
    @Parameter(description = "Identificador único do produto (Stock Keeping Unit).",
      required = true, example = "SKU-001-XYZ")
    @PathVariable(name = "sku") String sku,
    @RequestBody @Valid StockRequest request) {
    commandService.sellProduct(sku, request);
  }

}