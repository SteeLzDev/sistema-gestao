package com.oficina.infrastructure.rest;

import com.oficina.application.port.PermissaoService;
import com.oficina.domain.model.Usuario;
import com.oficina.infrastructure.rest.dto.AuthRequest;
import com.oficina.infrastructure.rest.dto.AuthResponse;
import com.oficina.infrastructure.security.JwtTokenUtil;
import com.oficina.infrastructure.security.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PermissaoService permissaoService;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService,
                          JwtTokenUtil jwtTokenUtil, PermissaoService permissaoService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.permissaoService = permissaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception{
        try {
            //Autenticar Username
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getSenha())
            );
        } catch (BadCredentialsException e ) {
            return ResponseEntity.status(401).body("Credenciais Inválidas");
        }

        //Carregar detalhes do Usuário pelo username
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        //Buscar Usuario Completo pelo username
        Usuario usuario = userDetailsService.findUserByUsername(authRequest.getUsername());

        //Obter permissões do usuário
        List<String> permissoes = permissaoService.obterNomesPermissoesDoUsuario(usuario.getId());

        // Adicionar log para depuração
        System.out.println("Permissões do usuário " + usuario.getUsername() + ": " + permissoes);

        final String jwt = jwtTokenUtil.generateToken(userDetails, permissoes);

        return ResponseEntity.ok(new AuthResponse(jwt, usuario, permissoes));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        try {
            // Obter o token do cabeçalho Authorization
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Token não fornecido ou inválido");
            }

            String token = authHeader.substring(7);

            // Extrair o username do token
            String username = jwtTokenUtil.extractUsername(token);

            // Carregar detalhes do usuário
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Verificar se o token é válido
            if (!jwtTokenUtil.validateToken(token, userDetails)) {
                return ResponseEntity.status(401).body("Token inválido ou expirado");
            }

            // Buscar usuário completo pelo username
            Usuario usuario = userDetailsService.findUserByUsername(username);

            // Obter permissões do usuário
            List<String> permissoes = permissaoService.obterNomesPermissoesDoUsuario(usuario.getId());

            // Criar resposta
            Map<String, Object> response = new HashMap<>();
            response.put("id", usuario.getId());
            response.put("nome", usuario.getNome());
            response.put("username", usuario.getUsername());
            response.put("email", usuario.getEmail());
            response.put("perfil", usuario.getPerfil());
            response.put("cargo", usuario.getCargo());
            response.put("status", usuario.getStatus());
            response.put("permissoes", permissoes);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao obter informações do usuário: " + e.getMessage());
        }
    }

    // renovar o token
    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        try {
            // Obter o token do cabeçalho Authorization
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Token não fornecido ou inválido");
            }

            String token = authHeader.substring(7);

            // Extrair o username do token
            String username = jwtTokenUtil.extractUsername(token);

            // Carregar detalhes do usuário
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Verificar se o token é válido
            if (!jwtTokenUtil.validateToken(token, userDetails)) {
                return ResponseEntity.status(401).body("Token inválido ou expirado");
            }

            // Buscar usuário completo pelo username
            Usuario usuario = userDetailsService.findUserByUsername(username);

            // Obter permissões do usuário
            List<String> permissoes = permissaoService.obterNomesPermissoesDoUsuario(usuario.getId());

            // Gerar novo token
            String newToken = jwtTokenUtil.generateToken(userDetails, permissoes);

            // Criar resposta
            Map<String, Object> response = new HashMap<>();
            response.put("token", newToken);
            response.put("user", usuario);
            response.put("permissoes", permissoes);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao renovar token: " + e.getMessage());
        }
    }
}