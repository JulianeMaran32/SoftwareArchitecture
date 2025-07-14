package com.juhmaran.stockeventsourcing.service.query;

import com.juhmaran.stockeventsourcing.domain.event.Event;
import com.juhmaran.stockeventsourcing.projection.model.ProductView;

import java.util.List;

/**
 * Created by Juliane Maran
 *
 * @since 14/07/2025
 */
public interface ProductQueryService {

  ProductView getProductStock(String sku);

  List<Event> getProductHistory(String sku);

}
