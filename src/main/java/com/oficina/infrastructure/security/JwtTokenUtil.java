package com.oficina.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenUtil {


    private static final Logger  logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    // Usar uma chave segura para assinatura JWT (em produção, isso deve estar em application.yml)

    private final SecretKey key;

    @Value("${jwt.expiration:86400000}") //24Horas
    private long jwtExpiration;

    public JwtTokenUtil(@Value("${jwt.secret:defaultSecretKeyForDevelopmentEnvironmentOnly}") String secret) {
        //Converter a string secreta em chave segura
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        logger.info("JwtTokenUtil inicializado com chave secreta");
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    public String generateToken(UserDetails userDetails, List<String> permissoes ) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("permissoes", permissoes);
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration (String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim (String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims (String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpired (String token) {
        return extractExpiration(token).before(new Date());
    }

    @SuppressWarnings("unchecked")
    public List<String> extractPermissoes(String token) {
        try {
            Claims claims = extractAllClaims(token);
            List<String> permissoes = claims.get("permissoes", List.class);
            return permissoes != null ? permissoes : List.of();
        } catch (Exception e) {
            logger.error("Erro ao extrair permissoes do token", e);
            return  List.of();
        }
    }





}
