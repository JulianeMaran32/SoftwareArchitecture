package com.juhmaran.stockeventsourcing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
  info = @Info(
    title = "Stock Event Sourcing",
    version = "1.0.0",
    termsOfService = "https://example.com/terms",
    contact = @Contact(
      name = "Juliane Maran",
      email = "julianemaran@gmail.com"),
    license = @License(
      name = "Apache License 2.0",
      url = "http://www.apache.org/licenses/")
  ),
  servers = @Server(
    url = "http://localhost:8081",
    description = "Local Server"
  ),
  externalDocs = @ExternalDocumentation()
)
@SpringBootApplication
@EnableJpaAuditing
@EnableElasticsearchRepositories(basePackages = "com.juhmaran.stockeventsourcing.projection.repository")
public class StockeventsourcingApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockeventsourcingApplication.class, args);
  }

}
