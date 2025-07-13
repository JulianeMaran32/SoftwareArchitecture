package com.juhmaran.stockeventsourcing.view;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

/**
 * Esta classe será um documento no Elasticsearch, projetada para responder rapidamente à pergunta:
 * "Qual o estado atual do estoque do produto X?".
 * <p>
 * Representa a visão de leitura (Read Model) do estado atual de um produto no estoque. <br>
 * Este documento no Elasticsearch é otimizado para consultas rápidas.
 * <p>
 * Created by Juliane Maran
 *
 * @since 12/07/2025
 */
@Data
@Document(indexName = "product_inventory") // Define o nome do índice no Elasticsearch
public class ProductInventoryView {

  @Id
  private String sku;

  @Field(type = FieldType.Text, name = "name") // Campo para busca textual (ex: "camiseta azul")
  private String name;

  @Field(type = FieldType.Keyword, name = "color") // Campo para filtro e agregação exata (ex: "Azul")
  private String color;

  @Field(type = FieldType.Keyword, name = "material") // Campo para filtro e agregação exata (ex: "Algodão")
  private String material;

  @Field(type = FieldType.Integer)
  private int availableQuantity;

  @Field(type = FieldType.Integer)
  private int reservedQuantity;

  @Field(type = FieldType.Integer)
  private int soldQuantity;

  @Field(type = FieldType.Date)
  private Instant lastModified;

}
