package com.oficina.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class DatabaseConfig {


    @Autowired
    private Environment env;


    @Bean
    @Primary
    public DataSource dataSource(){

        // Primeiro, tente criar o banco de dados se ele não existir
        createDatabaseIfNotExists();


        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/sistema_gestao");
        dataSource.setUsername("postgres");
        dataSource.setPassword("admin");
        return dataSource;
    }

    private void createDatabaseIfNotExists() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "admin"
        )){
            // Verificar se o banco sistema_gestao existe
            try(Statement statement = connection.createStatement()) {
                // Tentar criar o banco de dados
                statement.executeUpdate("CREATE DATABASE sistema_gestao WITH OWNER postgres ENCODING 'UTF8'");
                System.out.println("Banco de dados sistema_gestao criado com sucesso! ");
            } catch (SQLException e ) {
                // Se o banco já existir, vai gerar um erro, mas podemos ignorar
                if (e.getMessage().contains("already exists")) {
                    System.out.println("Banco de dados sistema_gestao já existe");
                } else {
                    throw e;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar banco de dados: " + e.getMessage());
            e.printStackTrace();
        }


    }




}
