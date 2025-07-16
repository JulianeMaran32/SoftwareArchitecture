package com.juhmaran.stockeventsourcing.controller.query;

import com.juhmaran.stockeventsourcing.domain.event.Event;
import com.juhmaran.stockeventsourcing.projection.model.ProductView;
import com.juhmaran.stockeventsourcing.service.query.ProductQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Juliane Maran
 *
 * @since 14/07/2025
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(
  name = "Product Query",
  description = "Endpoints para consultar informações de produtos e seu histórico de eventos (operações de leitura).")
public class ProductQueryController {

  private final ProductQueryService queryService;

  @Operation(
    operationId = "getProductStock",
    summary = "Consultar o estoque de um produto",
    description = "Retorna a visão atualizada do estoque de um produto, incluindo a quantidade total, " +
      "reservada e disponível, com base na projeção dos eventos.")
  @ApiResponse(
    responseCode = "200",
    description = "Sucesso. A visão atual do estoque do produto foi retornada.",
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
      schema = @Schema(implementation = ProductView.class)))
  @ApiResponse(
    responseCode = "404",
    description = "Produto não encontrado. O SKU informado não corresponde a nenhum produto existente.")
  @ApiResponse(
    responseCode = "500",
    description = "Erro interno no servidor.")
  @GetMapping("/{sku}/stock")
  public ProductView getProductStock(
    @Parameter(description = "Identificador único do produto (Stock Keeping Unit).",
      required = true, example = "SKU-001-XYZ")
    @PathVariable String sku) {
    return queryService.getProductStock(sku);
  }

  @Operation(
    operationId = "getProductHistory",
    summary = "Consultar o histórico de eventos de um produto",
    description = "Retorna a lista completa de todos os eventos (criação, reserva, venda, etc.) " +
      "associados a um produto específico, em ordem cronológica.")
  @ApiResponse(
    responseCode = "200",
    description = "Sucesso. A lista de eventos do produto foi retornada.",
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
      array = @ArraySchema(schema = @Schema(implementation = Event.class))))
  @ApiResponse(
    responseCode = "404",
    description = "Produto não encontrado. O SKU informado não corresponde a nenhum produto existente.")
  @ApiResponse(
    responseCode = "500",
    description = "Erro interno no servidor.")
  @GetMapping("/{sku}/history")
  public List<Event> getProductHistory(
    @Parameter(description = "Identificador único do produto (Stock Keeping Unit).",
      required = true, example = "SKU-001-XYZ")
    @PathVariable String sku) {
    return queryService.getProductHistory(sku);
  }

}
