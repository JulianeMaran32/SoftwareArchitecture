package com.juhmaran.stockeventsourcing.projection.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by Juliane Maran
 *
 * @since 14/07/2025
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products")
public class ProductView {

  @Id
  private String sku;

  @Field(type = FieldType.Text)
  private String name;

  @Field(type = FieldType.Keyword)
  private String color;

  @Field(type = FieldType.Keyword)
  private String material;

  @Field(type = FieldType.Integer)
  private int availableQuantity;

  @Field(type = FieldType.Integer)
  private int reservedQuantity;

  @Field(type = FieldType.Integer)
  private int soldQuantity;

}
