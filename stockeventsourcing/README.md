# Stock Event Sourcing

Implemente uma aplicação que utilize event sourcing para gerenciar estoque de produto, em que um produto possui os
seguintes atributos:

- SKU
- Nome
- Cor
- Material

No estoque, ele pode:

- Ter uma quantidade reservada
- Ter uma quantidade disponível
- Ter uma quantidade removida/vendida

A aplicação deve possuir endpoints para reservar, vender e adicionar produtos.
Deve possuir também possuir endpoints para consulta: histórico de transações, quantidade disponível em estoque;

### Banco de Dados (opções)

Utilizar o MELHOR banco de dados para aplicação

- OPÇÃO 1: NoSQL (Elastic) para escrita e NoSQL (Elastic) para leitura (réplica)
- OPÇÃO 2: SQL (H2) para escrita e NoSQL (Elastic) para leitura (réplica)
- OPÇÃO 3: OU outra opção que julgar mais adequada

### Tecnologias:

- Java 21
- Spring Boot 3.5.0 (application.yml)
- Dockerfile e Docker composse
- Build: Maven
- Dependências:
    - Developer Tools: Lombok, Spring Boot DevTools, Spring Configuration Processor
    - Web: Spring WEbr
    - OPS: Spring Boot Actuator
    - Outros: MapStruct

---

## Estrutura de Pastas/Pacotes

A estrutura de pacotes e pastas é a espinha dorsal de um projeto bem organizado. Para uma aplicação com Event Sourcing e
CQRS, uma estrutura bem definida não é apenas uma boa prática, é **essencial** para manter a clareza, a separação de
responsabilidades e a manutenibilidade.

A seguir, apresento duas abordagens populares, com uma recomendação clara da melhor opção para o cenário do desafio
proposto, explicando o propósito de cada pacote.

### Abordagem Recomendada: Híbrida (Técnica + por Domínio/CQRS)

Esta é a estrutura mais eficaz e comum para projetos Spring Boot que implementam padrões como DDD e CQRS. Ela organiza o
código em camadas técnicas no nível superior, mas agrupa a lógica de negócio principal de acordo com os conceitos do
Event Sourcing.

**Vantagens**

* **Clareza Arquitetural:** A estrutura do código reflete diretamente o padrão CQRS (separação de Comando e Consulta).
* **Baixo Acoplamento:** O `domain` principal permanece isolado de frameworks (Spring, JPA, etc.).
* **Escalabilidade:** É fácil adicionar novos agregados ou contextos de negócio sem afetar os existentes.
* **Intuitiva:** Desenvolvedores familiarizados com Spring se sentirão em casa, ao mesmo tempo que a intenção do
  design (CQRS/ES) é óbvia.

**Estrutura de Pacotes Detalhada**

```text
src/main/java/com/example/stockeventsourcing/
│
├── StockEventSourcingApplication.java   // Ponto de entrada da aplicação Spring Boot
├── config/                              // Configurações da aplicação (ex: Beans do Elasticsearch, Segurança)
├── controller/                          // Camada da API REST (Endpoints)
│   ├── dto/                             // Data Transfer Objects (Records para requisições e respostas)
│   │   └── ProductDtos.java
│   └── mapper/                          // Mapeadores (MapStruct) para converter DTOs para Comandos/Views
│   │   └── ProductMapper.java
│   └── ProductStockController.java      // Controlador REST para as operações de estoque
├── domain/                              // O CORAÇÃO DA APLICAÇÃO: Lógica de negócio pura
│   ├── aggregate/                       // Agregados - Entidades que garantem a consistência das transações
│   │   └── ProductAggregate.java
│   ├── command/                         // Objetos que representam a intenção de mudar o estado (imperativo)
│   │   └── ProductCommands.java         // (ex: AddProductStockCommand, ReserveProductCommand)
│   └── event/                           // Objetos que representam um fato que ocorreu no passado (imutável)
│       └── ProductEvents.java           // (ex: ProductStockAddedEvent, ProductReservedEvent)
├── exception/                           // Classes de exceção customizadas e o handler global
│   ├── GlobalExceptionHandler.java
│   └── ProductNotFoundException.java
│   └── InsufficientStockException.java
├── projection/                          // Lógica responsável por construir e atualizar o Read Model
│   └── InventoryProjectionHandler.java  // "Ouve" os eventos e atualiza a view no Elasticsearch
├── repository/                          // Abstração da camada de persistência
│   ├── eventstore/                      // Repositório para o WRITE MODEL (log de eventos)
│   │   ├── EventStoreEntity.java
│   │   └── EventStoreRepository.java    // Interface Spring Data JPA
│   └── view/                            // Repositório para o READ MODEL (visão de consulta)
│       └── ProductInventoryViewRepository.java // Interface Spring Data Elasticsearch
├── service/                             // Camada de orquestração (coordena o fluxo de trabalho)
│   ├── command/                         // Serviços para o lado da ESCRITA (Command Side)
│   │   └── ProductCommandService.java   // Orquestra a execução de comandos
│   └── query/                           // Serviços para o lado da LEITURA (Query Side)
│       └── ProductQueryService.java     // Orquestra a execução de consultas
└── view/                                // Entidades do READ MODEL (as projeções)
    └── ProductInventoryView.java        // O documento que será salvo no Elasticsearch
```

---

### Resumo e Conclusão

A **Abordagem Recomendada (Híbrida)** é superior para este projeto. Ela equilibra perfeitamente a organização técnica
padrão do Spring com a clareza conceitual exigida pela arquitetura de Event Sourcing e CQRS.

Aqui está um resumo de como os conceitos do padrão se mapeiam para a estrutura de pacotes recomendada:

| Conceito CQRS/ES              | Pacote na Estrutura Recomendada | Propósito                                                                     |
|-------------------------------|---------------------------------|-------------------------------------------------------------------------------|
| **Comando (Command)**         | `domain.command`                | Define a intenção de mutação (ex: "Reserve 10 itens").                        |
| **Agregado (Aggregate)**      | `domain.aggregate`              | Valida comandos e produz eventos. É o guardião da consistência.               |
| **Evento (Event)**            | `domain.event`                  | Representa um fato que aconteceu. A fonte da verdade.                         |
| **Serviço de Comando**        | `service.command`               | Orquestra o fluxo: carrega o agregado, passa o comando, salva os eventos.     |
| **Event Store (Write Model)** | `repository.eventstore`         | Persiste e recupera a sequência de eventos (usando JPA/H2).                   |
| **Projeção (Read Model)**     | `view` e `projection`           | Cria e mantém a `ProductInventoryView` (o estado atual) a partir dos eventos. |
| **Serviço de Consulta**       | `service.query`                 | Fornece acesso rápido e eficiente ao Read Model.                              |
| **Repositório de Consulta**   | `repository.view`               | Acessa o Read Model (usando Elasticsearch).                                   |
| **API/Controlador**           | `controller`                    | Expõe os endpoints para os clientes interagirem com os serviços.              |

Adotar esta estrutura desde o início tornará o desenvolvimento muito mais lógico e organizado.

---

## Exemplos de Requisições (cURL)

### 1. Adicionar um Novo Produto ao Estoque

**Request**

```
curl --location 'http://localhost:8081/products' \
--header 'Content-Type: application/json' \
--data '{
  "sku": "TSHIRT-001",
  "name": "T-Shirt Básica",
  "color": "Branco",
  "material": "Algodão",
  "initialQuantity": 100
}'
```

**Response**

- Status HTTP: 201 Created (Corpo vazio)

### 2. Reservar 10 unidades do produto

**Request**

```
curl -X POST http://localhost:8081/products/TSHIRT-001/reserve \
-H "Content-Type: application/json" \
-d '{
  "quantity": 10
}'
```

**Response**

- Status HTTP: 200 OK (Corpo vazio)

### 3. Vender 5 unidades (previamente reservadas)

**Request**

```
curl -X POST http://localhost:8081/products/TSHIRT-001/sell \
-H "Content-Type: application/json" \
-d '{
  "quantity": 5
}'
```

**Response**

- Status HTTP: 200 OK (Corpo vazio)

### 4. Consultar o Estoque Atual

**Request**

```
curl -X GET http://localhost:8081/products/TSHIRT-001
```

**Response**

- Status HTTP: 200 OK

```json
{
  "sku": "TSHIRT-001",
  "name": "T-Shirt Básica",
  "color": "Branco",
  "material": "Algodão",
  "availableQuantity": 90,
  "reservedQuantity": 5,
  "soldQuantity": 5,
  "lastModified": "2024-05-21T18:30:00Z"
}
```

### 5. Consultar o Histórico de Transações

**Request**

```
curl -X GET http://localhost:8081/products/TSHIRT-001/history
```

**Response**

- Status HTTP: 200 OK

```json
[
  {
    "eventType": "ProductStockAddedEvent",
    "version": 1,
    "eventData": "{\"quantity\":100,\"name\":\"T-Shirt Básica\",\"color\":\"Branco\",\"material\":\"Algodão\"}",
    "timestamp": "2024-05-21T18:25:00Z"
  },
  {
    "eventType": "ProductReservedEvent",
    "version": 2,
    "eventData": "{\"quantity\":10}",
    "timestamp": "2024-05-21T18:28:00Z"
  },
  {
    "eventType": "ProductSoldEvent",
    "version": 3,
    "eventData": "{\"quantity\":5}",
    "timestamp": "2024-05-21T18:30:00Z"
  }
]
```

---

## Saiba Mais

### Logs (`application.yml` e com `@Slf4j`)

Os logs são fundamentais para a manutenibilidade e observabilidade de qualquer aplicação.

O Spring Boot utiliza o Logback como implementação de logging padrão. Podemos configurá-lo de forma extensiva
diretamente no `application.yml`.

**Boas Práticas de Níveis de Log**

* `ERROR`: Falhas críticas que impedem a operação de continuar (ex: falha de conexão com DB, exceções não tratadas).
* `WARN`: Situações inesperadas que não quebram a aplicação, mas indicam um problema potencial (ex: API externa lenta,
  configuração obsoleta).
* `INFO`: Eventos importantes do fluxo de negócio (ex: "Comando de venda recebido para SKU X", "Produto Y criado com
  sucesso").
* `DEBUG`: Informações detalhadas para depuração do fluxo técnico (ex: "Entrando no método X", "Estado do agregado antes
  de aplicar o evento", "Payload do evento").
* `TRACE`: O nível mais detalhado, para informações de baixíssimo nível (ex: valores de parâmetros de queries SQL).

---

## Referências

* [Using MapStruct With Lombok - Baeldung](https://www.baeldung.com/java-mapstruct-lombok)
