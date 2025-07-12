# Software Architecture

Bem-vindo(a) a este repositório dedicado à exploração e implementação de padrões avançados de arquitetura de software. O objetivo aqui é servir como um **laboratório prático e em constante evolução**, demonstrando a aplicação de conceitos-chave em cenários do mundo real.

Este é um _showcase_ vivo, projetado para crescer à medida que novos padrões e desafios emergem no cenário da engenharia de software. Cada projeto é autocontido, focado em resolver um problema específico e construído sobre uma pilha de tecnologia moderna e robusta.

## Filosofia

Acreditamos que a melhor forma de aprender e dominar arquitetura de software é através da prática. Este repositório serve para:

* **Demonstrar Implementações:** Fornecer exemplos de código claros e funcionais para padrões complexos.
* **Analisar Trade-offs:** Explorar diferentes abordagens para o mesmo problema, discutindo suas vantagens e desvantagens.
* **Criar um Guia de Referência:** Servir como um ponto de partida para arquitetos e desenvolvedores que buscam implementar essas soluções em seus próprios projetos.

---

## Padrões Arquitetônicos

Esta seção cataloga os padrões que exploramos ou planejamos explorar.

### Padrões Implementados

* **Microsserviços (MS):** Uma abordagem arquitetônica que estrutura uma aplicação com uma coleção de serviços fracamente acoplados, permitindo escalabilidade e desenvolvimento independentes.
* **CQRS (Command Query Responsibility Segregation):** Separação das operações de escrita (Commands) das de leitura (Queries), permitindo otimizações independentes de performance, escalabilidade e segurança para cada lado.
* **DDD (Domain-Driven Design):** Uma abordagem que foca em modelar o software para corresponder a um domínio de negócios, utilizando uma linguagem ubíqua e modelos ricos para resolver problemas complexos.
* **Event Sourcing (ES):** Persistência do estado de uma aplicação como uma sequência de eventos imutáveis. Em vez de salvar o estado final, salvamos todas as transações que levaram a ele, proporcionando um log de auditoria completo e a capacidade de reconstruir estados passados.

### Padrões no Roadmap

O repositório continuará crescendo para incluir implementações dos seguintes padrões:

* **Padrão Saga:** Para gerenciar a consistência de dados em transações distribuídas que abrangem múltiplos microsserviços. Serão exploradas ambas as abordagens: **Coreografia** (baseada em eventos) e **Orquestração** (com um coordenador central) 
* **Outbox Pattern:** Garante a entrega atômica e confiável de mensagens/eventos para um _message broker_, mesmo em caso de falhas, prevenindo a inconsistência entre o banco de dados do serviço e o estado propagado para o resto do sistema. 
* **API Gateway:** Fornece um ponto de entrada único e unificado para um conjunto de microsserviços, simplificando a comunicação para os clientes e centralizando responsabilidades como autenticação, _rate limiting_ e roteamento. 
* **Circuit Breaker:** Aumenta a resiliência do sistema, impedindo que um serviço faça chamadas repetidas para outro serviço que já demonstrou estar falhando, evitando a propagação de falhas em cascata. 

---

## Projetos de Referência

### 1. PetStore User Service (CQRS & DDD)

Este projeto implementa a parte de gerenciamento de usuários (`/user`) da especificação (Swagger Petstore OpenAPI 3.0)[https://editor.swagger.io/]. Ele serve como um exemplo prático da aplicação dos padrões CQRS e DDD em um contexto de microsserviços.

* **Padrões Demonstrados:** Microsserviços, CQRS, DDD.
* **Tecnologias:** Java 21, Spring Boot 3.5.3, Elasticsearch, Docker.
* **Acessar o Projeto:** 
   * [Clique aqui para ir ao diretório do User Service](https://github.com/JulianeMaran32/petstore)


### 2. Gerenciamento de Estoque com Event Sourcing

Esta aplicação demonstra o poder do **Event Sourcing** combinado com **CQRS** para gerenciar o estoque de um produto, um domínio onde o histórico de transações é tão importante quanto o estado atual.

* **Padrões Demonstrados:** Microsserviços, Event Sourcing, CQRS.
* **Tecnologias:** Utiliza **PostgreSQL** como Event Store (fonte da verdade), **Apache Kafka** como um event bus para comunicação assíncrona, e **Elasticsearch** para as projeções de leitura otimizadas.
* **Acessar o Projeto:** 
   * [Clique aqui para ir ao diretório do Sistema de Estoque](https://github.com/JulianeMaran32/SoftwareArchitecture)

---

## Como Executar os Projetos

Cada projeto contém seu próprio `README.md` com instruções detalhadas de configuração, build e execução. Em geral, todos os projetos são containerizados com **Docker** e podem ser iniciados com um único comando `docker compose up --build` a partir de seu respectivo diretório.

## Como Contribuir

Este é um projeto que visa o aprendizado e a colaboração. Sugestões, discussões através de _Issues_ e contribuições via _Pull Requests_ são muito bem-vindas.

## Licença

Este repositório está licenciado sob a (Apache 2.0)[LICENSE].