package com.oficina.infrastructure.config;

import com.oficina.infrastructure.security.JwtAuthenticationEntryPoint;
import com.oficina.infrastructure.security.JwtRequestFilter;
import com.oficina.infrastructure.security.PermissaoAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Primary
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig (UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private PermissaoAuthorizationManager permissaoAuthorizationManager;

    @Bean
    @Primary
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
                .authorizeHttpRequests(auth -> auth

                        //Endpoints públicos
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("actuator/**").permitAll()
                        .requestMatchers("api/auth/registes").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        //.requestMatchers("/api/produtos/**").authenticated()


                        //Endpoints protegidos por permissões
                        .requestMatchers(HttpMethod.GET, "/api/produtos/**").access(permissaoAuthorizationManager.hasAnyPermission("ESTOQUE_VISUALIZAR"))
                        .requestMatchers(HttpMethod.POST, "/api/produtos/**").access(permissaoAuthorizationManager.hasAnyPermission("ESTOQUE_ADICIONAR"))
                        .requestMatchers(HttpMethod.PUT, "/api/produtos/**").access(permissaoAuthorizationManager.hasAnyPermission("ESTOQUE_EDITAR"))
                        .requestMatchers(HttpMethod.DELETE, "/api/produtos/**").access(permissaoAuthorizationManager.hasAnyPermission("ESTOQUE_REMOVER"))

                        .requestMatchers("/api/vendas/**").access(permissaoAuthorizationManager.hasAnyPermission("VENDAS_VISUALIZAR"))
                        .requestMatchers(HttpMethod.POST, "/api/vendas/**").access(permissaoAuthorizationManager.hasAnyPermission("VENDAS_CRIAR"))
                        .requestMatchers("/api/vendas/cancelar/**").access(permissaoAuthorizationManager.hasAnyPermission("VENDAS_CANCELAR"))

                        .requestMatchers("/api/fila/**").access(permissaoAuthorizationManager.hasAnyPermission("FILA_VISUALIZAR"))
                        .requestMatchers("/api/fila/gerenciar/**").access(permissaoAuthorizationManager.hasAnyPermission("FILA_GERENCIAR"))
                        .requestMatchers(HttpMethod.DELETE, "/api/fila/**").access(permissaoAuthorizationManager.hasAnyPermission("FILA_GERENCIAR"))

                        .requestMatchers("/api/relatorios/**").access(permissaoAuthorizationManager.hasAnyPermission("RELATORIOS_VISUALIZAR"))
                        .requestMatchers("/api/relatorios/exportar/**").access(permissaoAuthorizationManager.hasAnyPermission("RELATORIOS_EXPORTAR"))

                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").access(permissaoAuthorizationManager.hasAnyPermission("USUARIOS_REMOVER"))
                        .requestMatchers("/api/usuarios/**").access(permissaoAuthorizationManager.hasAnyPermission("USUARIOS_VISUALIZAR"))
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/**").access(permissaoAuthorizationManager.hasAnyPermission("USUARIOS_CRIAR"))
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").access(permissaoAuthorizationManager.hasAnyPermission("USUARIOS_EDITAR"))
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").access(permissaoAuthorizationManager.hasAnyPermission("USUARIOS_REMOVER"))

                        .requestMatchers("/api/permissoes/**").access(permissaoAuthorizationManager.hasAnyPermission("USUARIOS_PERMISSOES"))

                        .requestMatchers("/api/configuraoes/**").access(permissaoAuthorizationManager.hasAnyPermission("CONFIGURACOES_VISUALIZAR"))
                        .requestMatchers(HttpMethod.POST, "/api/configuracoes/**").access(permissaoAuthorizationManager.hasAnyPermission("CONFIGURACOES_EDITAR"))
                        .requestMatchers(HttpMethod.PUT,"/api/configuracoes/**").access(permissaoAuthorizationManager.hasAnyPermission("CONFIGURACOES_EDITAR"))

                        // Qualquer outra requisição precisa de autenticação
                        .anyRequest().authenticated()

                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
