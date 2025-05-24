package com.oficina;

import io.github.cdimascio.dotenv.Dotenv;
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


		//Carregar variáveis de ambiente do arquivo .env se existir
		try {
			Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		} catch (Exception e) {
			//Ignorar se o arquivo não existir
		}
		SpringApplication.run(SistemaGestaoApplication.class, args);
	}

}

