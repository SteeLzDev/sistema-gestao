package com.oficina.infrastructure.rest;

import com.oficina.domain.model.Usuario;
import com.oficina.infrastructure.rest.dto.AuthRequest;
import com.oficina.infrastructure.rest.dto.AuthResponse;
import com.oficina.infrastructure.security.JwtTokenUtil;
import com.oficina.infrastructure.security.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;


    public AuthController (AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService,
                           JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;

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
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        //Buscar Usuario Completo pelo username
        Usuario usuario = userDetailsService.findUserByUsername(authRequest.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt, usuario));


    }




}