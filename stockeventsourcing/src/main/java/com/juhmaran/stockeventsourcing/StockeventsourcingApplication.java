package com.juhmaran.stockeventsourcing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@OpenAPIDefinition(
  info = @Info(
    title = "API de Estoque com Event Sourcing",
    version = "1.0.0",
    description = "API para gerenciamento de estoque de produtos utilizando o padrão Event Sourcing e CQRS. " +
      "Permite a criação de produtos, controle de estoque (reserva e venda) e consulta do estado atual " +
      "e histórico de eventos.",
    termsOfService = "https://example.com/terms",
    contact = @Contact(
      name = "Juliane Maran",
      email = "julianemaran@gmail.com",
      url = "https://github.com/JulianeMaran32/SoftwareArchitecture"),
    license = @License(
      name = "Apache License 2.0",
      url = "http://www.apache.org/licenses/")
  ),
  servers = @Server(
    url = "http://localhost:{port}",
    description = "Servidor de desenvolvimento local.",
    variables = {
      @ServerVariable(
        name = "port",
        allowableValues = {"8081"},
        defaultValue = "8081",
        description = "Porta da aplicação principal."
      )
    }
  ),
  externalDocs = @ExternalDocumentation(
    description = "Repositório do projeto no GitHub",
    url = "https://github.com/JulianeMaran32/SoftwareArchitecture"
  )
)
@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.juhmaran.stockeventsourcing.domain.repository")
@EnableElasticsearchRepositories(basePackages = "com.juhmaran.stockeventsourcing.projection.repository")
public class StockeventsourcingApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockeventsourcingApplication.class, args);
  }

}
