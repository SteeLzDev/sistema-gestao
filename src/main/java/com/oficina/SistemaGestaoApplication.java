package com.oficina;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EntityScan(basePackages = "com.oficina.domain.model")
@EnableJpaRepositories(basePackages = "com.oficina.infrastructure.persistence.repository")
@ComponentScan(basePackages = "com.oficina")
public class SistemaGestaoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SistemaGestaoApplication.class, args);
	}
}

