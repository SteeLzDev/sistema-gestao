package com.oficina.infrastructure.config;

import com.oficina.domain.model.Usuario;
import com.oficina.infrastructure.persistence.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {


    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository) {
        return args -> {
            // Verificar se já existe um usuário admin
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                System.out.println("Criando usuário admin padrão...");
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setUsername("admin");
                admin.setSenha("admin123");
                admin.setPerfil("Administrador");
                admin.setCargo("Administrador");
                admin.setStatus("Ativo");

                usuarioRepository.save(admin);

                System.out.println("Usuário Criado com Sucesso!");
            } else {
                System.out.printf("Usuário admin já existe no banco de dados");
            }
        };
    }

}
