package com.oficina.infrastructure.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

@Component
public class PermissaoAuthorizationManager {

    private static final Logger logger = LoggerFactory.getLogger(PermissaoAuthorizationManager.class);

    public AuthorizationManager<RequestAuthorizationContext> hasAnyPermission(String... permissoes) {
        return (authentication, context) -> {
            logger.info("Verificando permissões para: " + context.getRequest().getRequestURI());
            logger.info("Método HTTP: " + context.getRequest().getMethod());
            logger.info("Permissões necessárias: " + Arrays.toString(permissoes));

            if (authentication == null) {
                logger.error("Authentication é null");
                return new AuthorizationDecision(false);
            }

            Authentication auth = authentication.get();
            if (auth == null || !auth.isAuthenticated()) {
                logger.error("Usuário não autenticado");
                return new AuthorizationDecision(false);
            }

            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            logger.info("Autoridades do usuário: " + authorities);

            if (authorities == null || authorities.isEmpty()) {
                logger.error("Usuário não tem autoridades");
                return new AuthorizationDecision(false);
            }

            boolean hasPermission = Arrays.stream(permissoes)
                    .anyMatch(permissao -> authorities.stream()
                            .anyMatch(authority -> {
                                boolean matches = authority.getAuthority().equals(permissao);
                                logger.info("Comparando: " + authority.getAuthority() + " com " + permissao + " = " + matches);
                                return matches;
                            }));

            logger.info("Tem permissão? " + hasPermission);
            return new AuthorizationDecision(hasPermission);
        };
    }
}
