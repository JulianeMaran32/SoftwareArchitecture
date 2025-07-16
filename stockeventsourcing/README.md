# Stock Event Sourcing

Este projeto é uma aplicação para gerenciamento de estoque de produtos, desenvolvida como uma demonstração prática dos
padrões arquiteturais **Event Sourcing** e **CQRS (Command Query Responsibility Segregation)**.

## Descrição da Arquitetura

O principal objetivo deste projeto é explorar uma arquitetura robusta e escalável, separando completamente as
responsabilidades de escrita (comandos) e leitura (consultas).

### Core Patterns

* **Event Sourcing:** Em vez de armazenar o estado atual de um produto, a aplicação persiste uma sequência cronológica
  de eventos imutáveis que descrevem cada alteração ocorrida (ex: `ProductAdded`, `ProductReserved`, `ProductSold`). O
  estado de um produto pode ser reconstruído a qualquer momento, reproduzindo seus eventos históricos. Isso fornece uma
  trilha de auditoria completa e poderosa.
* **CQRS (Command Query Responsibility Segregation):** A aplicação possui dois modelos distintos:
    1. **Write Model (Lado do Comando):** Responsável por receber comandos, validar regras de negócio complexas e gerar
       eventos. O coração deste modelo é o `ProductAggregate` que garante a consistência das operações. Os eventos são
       persistidos em um **Event Store**.
    2. **Read Model (Lado da Consulta):** Responsável por fornecer dados otimizados para consulta. Um processo
       assíncrono (Projeção) escuta os eventos gerados pelo Write Model e atualiza uma visão desnormalizada dos dados.
       Esta visão (`ProductView`) é projetada para responder rapidamente às consultas da UI ou de outros serviços.

### Fluxo de Dados

1. Um cliente envia um comando para a API (ex: `Reservar Produto).
2. O **Command Controller** direciona o comando para o **Command Service**.
3. O serviço carrega o histórico de eventos do **Event Store** para reconstruir o estado atual do `ProductAggregate`.
4. O `ProductAggregate` executa a lógica de negócio e, se for bem-sucedido, gera um novo evento (ex:
   `ProductReservedEvent`).
5. O novo evento é salvo no **Event Store** e publicado internamente na aplicação.
6. Um **Projection Listener** captura o evento publicado.
7. A projeção atualiza o **Read Model** (um documento no Elasticsearch) com as novas informações.
8. Quando um cliente solicita dados (ex: `Consultar Estoque`), a consulta é direcionada ao **Query Controller**, que
   busca a informação diretamente do **Read Model** otimizado, sem sobrecarregar o modelo de escrita.

### Stack de Tecnologias

| Responsabilidade          | Tecnologia Utilizada (`dev` profile) | Tecnologia Utilizada (`prod` profile) |
|---------------------------|--------------------------------------|---------------------------------------|
| **Linguagem / Framework** | Java 21 / Spring Boot 3.5.3          | Java 21 / Spring Boot 3.5.3           |
| **Event Store (Write)**   | H2 Database (In-Memory)              | PostgreSQL                            |
| **Read Model (Query)**    | Elasticsearch                        | Elasticsearch                         |
| **Containerização**       | Docker / Docker Compose              | Docker / Docker Compose               |
| **Visualização (Read)**   | Kibana                               | Kibana                                |

---

## Como Executar o Projeto

**Pré-requisitos:**

* Docker e Docker Compose
* Java 21 e Maven (apenas para execução local fora do Docker)

### 1. Modo de Produção (Recomendado)

Este modo utiliza a pilha completa: **PostgreSQL** (Event Store) + **Elasticsearch** (Read Model) + **Kibana**.

```bash
# Na raiz do projeto, execute:
docker compose up --build
```

Após a inicialização, os serviços estarão disponíveis nos seguintes endereços:

* **API da Aplicação**: `http://localhost:8081`
* **Swagger UI**: `http://localhost:8081/swagger-ui.html`
* **Documentação API (json)**: `http://localhost:8081/api-docs`
* **Kibana**: `http://localhost:5601`
* **Elasticsearch**: `http://localhost:9200`
* **PostgreSQL**: `http://localhost:5432`

### 2. Modo de Desenvolvimento

Este modo utiliza **H2 em memória** (Event Store), sendo mais leve para desenvolvimento.

```bash
# Na raiz do projeto, execute:
docker compose -f docker-compose.yml -f docker-compose.dev.yml up --build
```

A aplicação e o Kibana estarão disponíveis nas mesmas portas. O PostgreSQL não será iniciado.

## Exemplos de Uso (Postman Collection)

Você pode importar a coleção abaixo no Postman para testar a API facilmente.

[![Run in Postman](https://run.pstmn.io/button.svg)](https://god.postman.co/run-collection/9f5c2a1b948f936c5352?action=collection%2Fimport)

<details>
<summary>Ou copie o JSON da coleção aqui</summary>

```json
{
  "info": {
    "_postman_id": "f4e1f72d-1234-5678-abcd-c3a9f0d4b2e1",
    "name": "Stock Event Sourcing",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Commands",
      "item": [
        {
          "name": "1. Add Product",
          "request": {
            "method": "POST",
            "header": [],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"name\": \"Classic Sneaker\",\n  \"color\": \"White\",\n  \"material\": \"Leather\",\n  \"quantity\": 100\n}",
              "options": {
                "raw": {
                  "language": "json"
                }
              }
            },
            "url": {
              "raw": "http://localhost:8081/api/v1/products/SHOE-001/add",
              "protocol": "http",
              "host": [
                "localhost"
              ],
              "port": "8081",
              "path": [
                "api",
                "v1",
                "products",
                "SHOE-001",
                "add"
              ]
            }
          },
          "response": []
        },
        {
          "name": "2. Reserve Product",
          "request": {
            "method": "POST",
            "header": [],
            "body": {
              "mode": "raw",
              "raw": "{\n    \"quantity\": 10\n}",
              "options": {
                "raw": {
                  "language": "json"
                }
              }
            },
            "url": {
              "raw": "http://localhost:8081/api/v1/products/SHOE-001/reserve",
              "protocol": "http",
              "host": [
                "localhost"
              ],
              "port": "8081",
              "path": [
                "api",
                "v1",
                "products",
                "SHOE-001",
                "reserve"
              ]
            }
          },
          "response": []
        },
        {
          "name": "3. Sell Product",
          "request": {
            "method": "POST",
            "header": [],
            "body": {
              "mode": "raw",
              "raw": "{\n    \"quantity\": 7\n}",
              "options": {
                "raw": {
                  "language": "json"
                }
              }
            },
            "url": {
              "raw": "http://localhost:8081/api/v1/products/SHOE-001/sell",
              "protocol": "http",
              "host": [
                "localhost"
              ],
              "port": "8081",
              "path": [
                "api",
                "v1",
                "products",
                "SHOE-001",
                "sell"
              ]
            }
          },
          "response": []
        }
      ]
    },
    {
      "name": "Queries",
      "item": [
        {
          "name": "Get Product Stock",
          "protocolProfileBehavior": {
            "disableBodyPruning": true
          },
          "request": {
            "method": "GET",
            "header": [],
            "body": {
              "mode": "raw",
              "raw": "",
              "options": {
                "raw": {
                  "language": "json"
                }
              }
            },
            "url": {
              "raw": "http://localhost:8081/api/v1/products/SHOE-001/stock",
              "protocol": "http",
              "host": [
                "localhost"
              ],
              "port": "8081",
              "path": [
                "api",
                "v1",
                "products",
                "SHOE-001",
                "stock"
              ]
            }
          },
          "response": []
        },
        {
          "name": "Get Product History",
          "request": {
            "method": "GET",
            "header": [],
            "url": {
              "raw": "http://localhost:8081/api/v1/products/SHOE-001/history",
              "protocol": "http",
              "host": [
                "localhost"
              ],
              "port": "8081",
              "path": [
                "api",
                "v1",
                "products",
                "SHOE-001",
                "history"
              ]
            }
          },
          "response": []
        }
      ]
    }
  ]
}
```

</details>

## Explorando o Read Model (Elasticsearch)

Você pode consultar o Read Model diretamente no Elasticsearch ou através do Kibana.

### 1. Configurar o Kibana

1. Acesse `http://localhost:5601`.
2. No menu, vá para **Management > Stack Management**.
3. Clique em **Data Views** e depois em **Create data view**.
4. Use `products` como o **Index pattern** e salve.
5. Agora, na seção **Discover**, você pode explorar visualmente todos os dados do estoque.

### 2. Consultas Diretas no Elasticsearch

Você pode usar `curl` ou qualquer cliente REST para consultar o Elasticsearch na porta `9200`.

**Buscar todos os produtos:**

```
GET /products/_search?pretty
```

**Response 200 OK**

```json
{
  "took": 3,
  "timed_out": false,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": {
      "value": 1,
      "relation": "eq"
    },
    "max_score": 1,
    "hits": [
      {
        "_index": "products",
        "_id": "SHOE-001",
        "_score": 1,
        "_source": {
          "_class": "com.juhmaran.stockeventsourcing.projection.model.ProductView",
          "sku": "SHOE-001",
          "name": "Classic Sneaker",
          "color": "White",
          "material": "Leather",
          "availableQuantity": 100,
          "reservedQuantity": 0,
          "soldQuantity": 0
        }
      }
    ]
  }
}
```

**Buscar um produto específico pelo SKU:**

```
GET /products/_doc/SHOE-001?pretty
```

**Response 200 OK**

```json
{
  "_index": "products",
  "_id": "SHOE-001",
  "_version": 1,
  "_seq_no": 0,
  "_primary_term": 1,
  "found": true,
  "_source": {
    "_class": "com.juhmaran.stockeventsourcing.projection.model.ProductView",
    "sku": "SHOE-001",
    "name": "Classic Sneaker",
    "color": "White",
    "material": "Leather",
    "availableQuantity": 100,
    "reservedQuantity": 0,
    "soldQuantity": 0
  }
}
```

**Busca avançada (ex: todos os produtos da cor "White"):**

```
GET /products/_search?pretty
{
  "query": {
    "match": {
      "color": "White"
    }
  }
}
```

**Response 200 OK**

```json
{
  "took": 8,
  "timed_out": false,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": {
      "value": 1,
      "relation": "eq"
    },
    "max_score": 0.2876821,
    "hits": [
      {
        "_index": "products",
        "_id": "SHOE-001",
        "_score": 0.2876821,
        "_source": {
          "_class": "com.juhmaran.stockeventsourcing.projection.model.ProductView",
          "sku": "SHOE-001",
          "name": "Classic Sneaker",
          "color": "White",
          "material": "Leather",
          "availableQuantity": 100,
          "reservedQuantity": 0,
          "soldQuantity": 0
        }
      }
    ]
  }
}
```

---

### Dockerfile

* **Finalidade**
    * Criar uma imagem Docker leve e segura para a aplicação Spring Boot, utilizando uma build multi-stage.
* **Principais Pontos**
    * A build multi-stage garante que as ferramentas de compilação (Maven) não sejam incluídas na imagem final,
      resultando em uma imagem menor e mais segura.
    * O `ENTRYPOINT` é genérico; o perfil Spring a ser ativado (`dev` ou `prod`) é injetado como uma variável de
      ambiente pelo Docker Compose.

---