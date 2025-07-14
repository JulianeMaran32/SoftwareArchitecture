<div align="center">
  <h1>Software Architecture Showcase</h1>
  <p>
    Um laboratório prático e em constante evolução para a exploração e 
    implementação de padrões avançados de arquitetura de software.
  </p>
</div>

<div align="center">
  <img src="https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?logo=spring&logoColor=white" alt="Spring Boot 3.x">
  <img src="https://img.shields.io/badge/Docker-gray?logo=docker&logoColor=white" alt="Docker">
  <img src="https://img.shields.io/badge/Elasticsearch-blue?logo=elasticsearch&logoColor=white" alt="Elasticsearch">
  <img src="https://img.shields.io/badge/License-Apache_2.0-yellow.svg" alt="License">
</div>

---

## Sobre Este Repositório

Bem-vindo(a) a este repositório dedicado à demonstração de padrões avançados de arquitetura. O objetivo aqui é servir
como um **_showcase_ vivo e um guia de referência**, onde conceitos complexos são transformados em código funcional e
bem documentado.

Acreditamos que a melhor forma de dominar a arquitetura de software é através da prática. Por isso, este espaço serve
para:

* **Demonstrar Implementações:** Fornecer exemplos claros de padrões como CQRS, Event Sourcing e DDD.
* **Analisar Trade-offs:** Explorar diferentes abordagens para o mesmo problema.
* **Criar um Guia de Referência:** Servir como ponto de partida para arquitetos e desenvolvedores.

## Base de Conhecimento (Wiki)

Para uma exploração aprofundada dos conceitos teóricos, diagramas e discussões de design, visite nossa Wiki. Ela é a
base de conhecimento que complementa o código-fonte.

<div align="center">
  <h3>
    <a href="https://github.com/JulianeMaran32/SoftwareArchitecture/wiki">
      ➡️ Explore a Wiki para entender o "porquê" por trás do código
    </a>
  </h3>
</div>

Nossa Wiki está organizada nas seguintes categorias:

| Categoria                  | Tópicos Abordados                                                                                                                                                                                                                                                                                                                                                                                                          |
|:---------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Padrões de Arquitetura** | [DDD](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki/DDD-(Domain-Driven-Design)), [Microsserviços](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki/Microservices-Architecture), [CQRS](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki/CQRS-(Command-Query-Responsibility-Segregation)), [Event Sourcing](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki/Event-Sourcing) |
| **Tecnologias**            | [Elasticsearch](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki/Elasticsearch), [Kibana](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki/Kibana), [Docker](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki/Docker-and-Docker-Compose)                                                                                                                                                  |
| **Guias e Práticas**       | [Boas Práticas de Git e GitHub](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki/Git-and-GitHub-Best-Practices), [Diagrama Geral da Arquitetura](https://github.com/JulianeMaran32/SoftwareArchitecture/wiki/Visão-Geral-da-Arquitetura)                                                                                                                                                                        |

---

## Projetos de Referência

Cada projeto neste repositório é um microsserviço autocontido que implementa um conjunto específico de padrões e
tecnologias.

| Projeto                     | Descrição                                                                               | Padrões Demonstrados          | Link                                                         |
|:----------------------------|:----------------------------------------------------------------------------------------|:------------------------------|:-------------------------------------------------------------|
| **User Service (PetStore)** | Implementação da API de usuários do PetStore, focada na separação de responsabilidades. | `DDD` `CQRS` `Microsserviços` | [Acessar Código](https://github.com/JulianeMaran32/petstore) |
| **Stock Event Sourcing**    | Gerenciamento de estoque, onde cada transação é um evento imutável.                     | `Event Sourcing` `CQRS` `DDD` | [Acessar Código](./stockeventsourcing/)                      |

---

## Roadmap de Padrões

Este repositório está em constante evolução. Os próximos padrões a serem explorados e implementados incluem:

* **Padrão Saga:** Para gerenciar a consistência de dados em transações distribuídas (abordagens de Coreografia e
  Orquestração).
* **Outbox Pattern:** Para garantir a entrega atômica de eventos, prevenindo inconsistência entre o banco de dados e o
  message broker.
* **API Gateway:** Para fornecer um ponto de entrada único e unificado para os microsserviços.
* **Circuit Breaker:** Para aumentar a resiliência do sistema, impedindo a propagação de falhas em cascata.

---

## Como Executar

Cada projeto é containerizado com **Docker** e **Docker Compose**, garantindo um ambiente de desenvolvimento simples e
reprodutível. As instruções detalhadas de `build` e `run` estão no `README.md` de cada diretório de projeto.

Em geral, o processo é:

1. Navegue até o diretório do projeto desejado (ex: `cd stockeventsourcing`).
2. Execute o comando: `docker compose up --build`.

## Como Contribuir

Contribuições são a alma de projetos de código aberto e aprendizado. Sugestões, discussões através de _Issues_ e
melhorias via _Pull Requests_ são muito bem-vindas!

## Licença

Este projeto está licenciado sob a [Apache License 2.0](./LICENSE).