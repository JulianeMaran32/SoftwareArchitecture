package com.juhmaran.stockeventsourcing.controller.command;

import com.juhmaran.stockeventsourcing.dto.ProductRequest;
import com.juhmaran.stockeventsourcing.dto.StockRequest;
import com.juhmaran.stockeventsourcing.service.command.ProductCommandService;
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
public class ProductCommandController {

  private final ProductCommandService commandService;

  @PostMapping("/{sku}/add")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void addProduct(@PathVariable String sku, @RequestBody @Valid ProductRequest request) {
    commandService.addProduct(sku, request);
  }

  @PostMapping("/{sku}/reserve")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void reserveProduct(@PathVariable String sku, @RequestBody @Valid StockRequest request) {
    commandService.reserveProduct(sku, request);
  }

  @PostMapping("/{sku}/sell")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void sellProduct(@PathVariable String sku, @RequestBody @Valid StockRequest request) {
    commandService.sellProduct(sku, request);
  }

}
