package com.juhmaran.stockeventsourcing.projection.repository;

import com.juhmaran.stockeventsourcing.projection.model.ProductView;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Juliane Maran
 *
 * @since 14/07/2025
 */
@Repository
public interface ProductViewRepository extends ElasticsearchRepository<ProductView, String> {
}
