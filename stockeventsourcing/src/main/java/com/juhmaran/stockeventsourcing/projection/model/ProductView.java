package com.juhmaran.stockeventsourcing.projection.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products")
@Schema(description = "Representa a visão de leitura (projeção) do estado atual de um produto.")
public class ProductView {

  @Id
  @Schema(description = "Identificador único do produto (SKU).", example = "SKU-001-XYZ")
  private String sku;

  @Field(type = FieldType.Text)
  @Schema(description = "Nome do produto.", example = "Camiseta Básica")
  private String name;

  @Field(type = FieldType.Keyword)
  @Schema(description = "Cor do produto.", example = "Azul Marinho")
  private String color;

  @Field(type = FieldType.Keyword)
  @Schema(description = "Material de fabricação do produto.", example = "Algodão")
  private String material;

  @Field(type = FieldType.Integer)
  @Schema(description = "Quantidade de itens disponíveis para reserva/venda.", example = "85")
  private int availableQuantity;

  @Field(type = FieldType.Integer)
  @Schema(description = "Quantidade de itens que foram reservados, mas ainda não vendidos.", example = "10")
  private int reservedQuantity;

  @Field(type = FieldType.Integer)
  @Schema(description = "Quantidade total de itens que já foram vendidos.", example = "5")
  private int soldQuantity;

}
