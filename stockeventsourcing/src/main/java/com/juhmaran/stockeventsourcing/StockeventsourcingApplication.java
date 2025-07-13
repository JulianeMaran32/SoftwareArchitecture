package com.juhmaran.stockeventsourcing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Habilita a auditoria para timestamps automáticos
public class StockeventsourcingApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockeventsourcingApplication.class, args);
  }

}
