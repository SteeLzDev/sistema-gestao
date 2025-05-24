package com.oficina.infrastructure.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @GetMapping("/jwt-config")
    public String testJwtConfig() {
        return "JWT Secret configurado: " + (jwtSecret.length() > 10 ? jwtSecret.substring(0, 10) + "..." : "Chave muito curta");
    }
}
