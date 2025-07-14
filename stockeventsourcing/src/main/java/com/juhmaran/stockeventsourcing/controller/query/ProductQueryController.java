package com.juhmaran.stockeventsourcing.controller.query;

import com.juhmaran.stockeventsourcing.domain.event.Event;
import com.juhmaran.stockeventsourcing.projection.model.ProductView;
import com.juhmaran.stockeventsourcing.service.query.ProductQueryService;
import lombok.RequiredArgsConstructor;
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
public class ProductQueryController {

  private final ProductQueryService queryService;

  @GetMapping("/{sku}/stock")
  public ProductView getProductStock(@PathVariable String sku) {
    return queryService.getProductStock(sku);
  }

  @GetMapping("/{sku}/history")
  public List<Event> getProductHistory(@PathVariable String sku) {
    return queryService.getProductHistory(sku);
  }

}
