# Stock Event Sourcing

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3+-green.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com)
[![License](https://img.shields.io/badge/License-Apache%202.0-yellow.svg)](https://opensource.org/licenses/Apache-2.0)

Este projeto é uma API de gerenciamento de estoque que demonstra os padrões arquiteturais **Event Sourcing** e **CQRS**
usando Java, Spring Boot, Docker, PostgreSQL e a Stack Elastic (Elasticsearch, Kibana, Logstash).

> Para uma explicação detalhada da arquitetura, conceitos e fluxo de dados, por
> favor, [consulte a Wiki do projeto](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki).

---

## Como Executar (com Docker)

**Pré-requisitos:**

* Docker
* Docker Compose

### 1. Iniciar o Ambiente

Este comando irá construir e iniciar todos os serviços necessários: a aplicação, PostgreSQL, Elasticsearch, Kibana e
Logstash.

```bash
# Na raiz do projeto, execute:
docker compose up --build -d
```

*(Use `docker-compose` se sua versão for mais antiga)*

### 2. Serviços Disponíveis

Após a inicialização, os serviços estarão disponíveis nos seguintes endereços:

| Serviço                   | URL                                     | Descrição                                      |
|:--------------------------|:----------------------------------------|:-----------------------------------------------|
| API de Estoque            | `http://localhost:8081`                 | Endpoint principal da aplicação.               |
| Swagger UI (Documentação) | `http://localhost:8081/swagger-ui.html` | Interface para explorar e testar os endpoints. |
| Kibana (Logs e Dados)     | `http://localhost:5601`                 | Visualize os dados de projeção e os logs.      |
| Elasticsearch             | `http://localhost:9200`                 | Datastore para projeções (read model) e logs.  |
| PostgreSQL                | `http://localhost:5432`                 | Datastore para o Event Store (write model).    |
| Actuator                  | `http://localhost:8081/actuator`        | Endpoints de monitoramento da aplicação.       |

### Parar o Ambiente

Para parar todos os containers, execute:

```shell
docker compose down
```

---

## Testando a API Usando a Coleção do Postman

A maneira mais fácil de interagir com a API é usando a nossa coleção pré-configurada do Postman.

<a href="https://god.postman.co/run-collection/9f5c2a1b948f936c5352?action=collection%2Fimport" target="_blank">
    <img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">
</a>

### Usando `curl` (Exemplos)

**1. Adicionar um novo produto:**

```bash
curl -X POST "http://localhost:8081/products/SKU-TSHIRT-01/add" \
-H "Content-Type: application/json" \
-d '{
  "name": "Camiseta Algodão Premium",
  "color": "Azul Marinho",
  "material": "Algodão Pima",
  "quantity": 150
}'
```

**2. Consultar o estoque do produto:**

```bash
curl http://localhost:8081/products/SKU-TSHIRT-01/stock
```

**3. Consultar o histórico de eventos:**

```bash
curl http://localhost:8081/products/SKU-TSHIRT-01/history
```

---

## Documentação e Scripts

Toda a documentação detalhada, guias de configuração e scripts úteis foram movidos para a Wiki do projeto para manter
este README limpo.

| Links                                                                                                                                     | Descrição                                               |
|:------------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------|
| [Wiki Home](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki)                                                                  | Visão geral e links.                                    |
| [Explicação da Arquitetura](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki/1.-Explica%C3%A7%C3%A3o-da-Arquitetura)           | Detalhes sobre CQRS, Event Sourcing e o fluxo de dados. |
| [Guia de Configuração do Kibana](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki/2.-Guia-de-Configura%C3%A7%C3%A3o-do-Kibana) | Passo a passo para visualizar os dados.                 | 
| [Scripts SQL](https://github.com/JulianeMaran32/SoftwareArchitecture/tree/main/database-scripts/postgres)                                 | para inspecionar o Event Store no PostgreSQL.           |
| [Scripts para Kibana Dev Tools](https://github.com/JulianeMaran32/SoftwareArchitecture/tree/main/kibana-scripts)                          | Queries para explorar os dados no Elasticsearch.        |

---

## 📜 Licença

Este projeto está licenciado sob a Licença Apache 2.0. Veja o arquivo [LICENSE](./../LICENSE) para mais detalhes.

---
