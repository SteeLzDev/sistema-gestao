package com.oficina.infrastructure.config;

import com.oficina.domain.model.Permissao;
import com.oficina.domain.model.Usuario;
import com.oficina.infrastructure.persistence.repository.PermissaoRepository;
import com.oficina.infrastructure.persistence.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    public CommandLineRunner initData(
            UsuarioRepository usuarioRepository,
            PermissaoRepository permissaoRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            // Primeiro, criar as permissões padrão
            criarPermissoesPadrao(permissaoRepository);

            // Depois, verificar se já existe um usuário admin
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                System.out.println("Criando usuário admin padrão...");
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setUsername("admin");
                admin.setSenha(passwordEncoder.encode("admin123"));
                admin.setPerfil("Administrador");
                admin.setCargo("Administrador");
                admin.setEmail("admin@sistema.com"); // Adicionando email que é obrigatório
                admin.setStatus("ATIVO");

                // Adicionar todas as permissões ao admin
                List<Permissao> todasPermissoes = permissaoRepository.findAll();
                admin.setPermissoes(todasPermissoes);

                usuarioRepository.save(admin);

                System.out.println("Usuário Criado com Sucesso!");
            } else {
                System.out.println("Usuário admin já existe no banco de dados");

                usuarioRepository.findByUsername("admin").ifPresent(admin -> {
                    // Verificar e atualizar senha se necessário
                    if (!admin.getSenha().startsWith("$2a$")) {
                        admin.setSenha(passwordEncoder.encode("admin123"));
                        System.out.println("Senha do usuário admin atualizada para BCrypt");
                    }

                    // Verificar e adicionar permissões se necessário
                    if (admin.getPermissoes() == null || admin.getPermissoes().isEmpty()) {
                        List<Permissao> todasPermissoes = permissaoRepository.findAll();
                        admin.setPermissoes(todasPermissoes);
                        System.out.println("Permissões adicionadas ao usuário admin");
                    }

                    usuarioRepository.save(admin);
                });
            }
        };
    }

    private void criarPermissoesPadrao(PermissaoRepository permissaoRepository) {
        List<String> permissoesNomes = Arrays.asList(
                "USUARIOS_VISUALIZAR", "USUARIOS_CRIAR", "USUARIOS_EDITAR", "USUARIOS_REMOVER", "USUARIOS_PERMISSOES",
                "ESTOQUE_VISUALIZAR", "ESTOQUE_ADICIONAR", "ESTOQUE_EDITAR", "ESTOQUE_REMOVER",
                "VENDAS_VISUALIZAR", "VENDAS_CRIAR", "VENDAS_CANCELAR",
                "FILA_VISUALIZAR", "FILA_GERENCIAR",
                "CONFIGURACOES_VISUALIZAR", "CONFIGURACOES_EDITAR"
        );

        for (String nome : permissoesNomes) {
            if (!permissaoRepository.existsByNome(nome)) {
                Permissao permissao = new Permissao();
                permissao.setNome(nome);
                permissao.setDescricao("Permissão para " + nome.toLowerCase().replace('_', ' '));
                permissaoRepository.save(permissao);
                System.out.println("Permissão criada: " + nome);
            }
        }
    }
}