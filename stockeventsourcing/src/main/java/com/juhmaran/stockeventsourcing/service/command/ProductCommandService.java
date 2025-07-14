package com.juhmaran.stockeventsourcing.service.command;

import com.juhmaran.stockeventsourcing.dto.ProductRequest;
import com.juhmaran.stockeventsourcing.dto.StockRequest;

/**
 * Created by Juliane Maran
 *
 * @since 13/07/2025
 */
public interface ProductCommandService {

  void addProduct(String sku, ProductRequest request);

  void reserveProduct(String sku, StockRequest request);

  void sellProduct(String sku, StockRequest request);

}
