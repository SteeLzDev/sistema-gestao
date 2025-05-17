package com.oficina.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public class Sqlinitializer implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception{
        try {
            // Executar o script SQL após a criação do banco de dados
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
            resourceDatabasePopulator.addScript(new ClassPathResource("db/migration/V1__create_configuracoes_tables.sql"));
            resourceDatabasePopulator.execute(dataSource);
            System.out.println("Script SQL Executado com Sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao executar script SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
