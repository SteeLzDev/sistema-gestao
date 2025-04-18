package com.oficina.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        logger.debug("Requisição para: " + path);

        if (path.startsWith("/api/fila") || path.startsWith("/api/auth/login") ||
                path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            chain.doFilter(request, response);
            return;
        }
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        logger.debug("Token Recebido: " + authorizationHeader);

        if (authorizationHeader != null) {
            if (authorizationHeader.startsWith("Bearer ")) {

                jwt = authorizationHeader.substring(7);
            logger.debug("Token extraído após remover 'Bearer' : " + jwt);

        } else {
            // Se não começar com "Bearer ", usar o token como está
            jwt = authorizationHeader.trim();
            logger.debug("Token usado diretamente: " + jwt);
        }
        try {
            username = jwtTokenUtil.extractUsername(jwt);
        } catch (Exception e) {
            logger.error("Não foi possível extrair o usuário do token " + e.getMessage(), e);
        }
    } else {
        logger.warn("JWT Token está ausente");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            try {

                if (jwtTokenUtil.validateToken(jwt, userDetails)) {
                    //Extrair permissões do token
                    List<String> permissoes = jwtTokenUtil.extractPermissoes(jwt);

                    // IMPORTANTE: Verificar se as permissões estão sendo extraídas corretamente
                    logger.info("Permissões extraídas do token: " + permissoes);

                    List<SimpleGrantedAuthority> authorities;
                    if (permissoes != null) {
                        authorities = permissoes.stream()

                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
                    } else {
                        authorities = List.of();
                    }

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                logger.error("Erro ao validar token: " + e.getMessage(), e);
            }
        }
        chain.doFilter(request, response);

    }


}
