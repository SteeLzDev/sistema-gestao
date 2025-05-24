package com.oficina.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@Order(1)
public class DatabaseConfig {


    @Autowired
    private Environment env;


    @Bean
    @Primary
    public DataSource dataSource(){

        //Credenciais das Variáveis de ambiente ou user valores padrão
        String dbHost = getEnvProperty("DB_HOST", "localhost");
        String dbPort = getEnvProperty("DB_PORT", "5432");
        String dbName = getEnvProperty("DB_NAME", "sistema_gestao");
        String dbUser = getEnvProperty("DB_USER", "postgres");
        String dbPassword = getEnvProperty("DB_PASSWORD", "admin");

        // Primeiro, tente criar o banco de dados se ele não existir
        createDatabaseIfNotExists(dbHost, dbPort, dbName, dbUser, dbPassword);

        //Configurar o DataSource normalmente
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(String.format("jdbc:postgresql://%s:%s/%s", dbHost, dbPort, dbName));
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    private void createDatabaseIfNotExists(String host, String port, String dbName, String user, String password) {
        try (Connection connection = DriverManager.getConnection(String.format(
                "jdbc:postgresql://%s:%s/postgres", host, port), user, password)) {

            // Verificar se o banco sistema_gestao existe
            try(Statement statement = connection.createStatement()) {
                // Tentar criar o banco de dados
                statement.executeUpdate(String.format("CREATE DATABASE %s WITH OWNER %s ENCODING 'UTF8'", dbName, user));
                System.out.println("Banco de dados" + dbName + "criado com sucesso! ");
            } catch (SQLException e ) {
                // Se o banco já existir, vai gerar um erro, mas podemos ignorar
                if (e.getMessage().contains("already exists") || e.getMessage().contains("já Existe")) {
                    System.out.println("Banco de dados" + dbName + "já existe");
                } else {
                    throw e;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //Método auxiliar para obter propriedades do ambiente ou valores padrão
    private String getEnvProperty(String key, String defaultValue) {
        //Tenta obter variável de ambiente
        String value = System.getenv(key);

        //Se Não encontrar, tenta obter das pripriedades do Spring
        if (value == null || value.isEmpty()) {
            value = env.getProperty(key);
        }
        //Se ainda não encontrar usar o valor padrão
        return (value != null) ? value : defaultValue;
    }




}
