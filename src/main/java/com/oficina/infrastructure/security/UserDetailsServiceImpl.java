package com.oficina.infrastructure.security;

import com.oficina.domain.model.Usuario;
import com.oficina.infrastructure.persistence.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Tentando carregar usuário com username: {}", username);
        try {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o username: " + username));

        logger.info("Usuário encontrado: {}, Senha: {}", usuario.getUsername(), usuario.getSenha());

        return new User(
                usuario.getUsername(),
                usuario.getSenha(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getPerfil().toUpperCase()))
        );


    } catch (Exception e) {
            logger.error("Erro ao carregar usuário: {}", e.getMessage(), e);
            throw  e;
        }
    }

    public Usuario findUserByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o username: " + username));

    }

    public Usuario findUserByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email:" + email));
    }


}
