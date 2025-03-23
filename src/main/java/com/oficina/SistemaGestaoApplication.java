package com.oficina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// Temporarily exclude security auto-configuration
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SistemaGestaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaGestaoApplication.class, args);
	}
}

