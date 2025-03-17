//package com.oficina.infrastructure.rest;
//
//import com.oficina.domain.model.Usuario;
//import com.oficina.application.port.UsuarioService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final UsuarioService usuarioService;
//
//    @Autowired
//    public AuthController(UsuarioService usuarioService) {
//        this.usuarioService = usuarioService;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
//        String username = credentials.get("username");
//        String senha = credentials.get("senha");
//
//        try {
//            Usuario usuario = usuarioService.autenticarPorUsername(username, senha);
//
//            // Gerar token JWT (simplificado para exemplo)
//            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwibmFtZSI6IkNhcmxvcyBPbGl2ZWlyYSIsInJvbGUiOiJBZG1pbmlzdHJhZG9yIn0.8tat9AtmGHLz9WMqYG5OLBe4BjqXDkqPFMI7_w";
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("token", token);
//
//            Map<String, Object> user = new HashMap<>();
//            user.put("id", usuario.getId());
//            user.put("nome", usuario.getNome());
//            user.put("username", usuario.getUsername());
//            user.put("perfil", usuario.getPerfil());
//
//            response.put("user", user);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("message", "Credenciais inválidas");
//            return ResponseEntity.status(401).body(error);
//        }
//    }
//}