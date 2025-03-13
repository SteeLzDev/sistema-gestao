package com.oficina.application.service;

import com.oficina.application.port.UsuarioService;
import com.oficina.domain.model.Usuario;
import com.oficina.domain.exception.EntidadeNaoEncontradaException;
import com.oficina.infrastructure.persistence.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário", id));
    }

    @Override
    public Usuario buscarUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário", username));
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        // Criptografar a senha antes de salvar
        if (usuario.getSenha() != null && !usuario.getSenha().startsWith("$2a$")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = buscarUsuarioPorId(id);

        // Atualizar campos
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setUsername(usuario.getUsername());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setCargo(usuario.getCargo());
        usuarioExistente.setPerfil(usuario.getPerfil());
        usuarioExistente.setStatus(usuario.getStatus());

        // Atualizar senha apenas se fornecida
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty() && !usuario.getSenha().startsWith("$2a$")) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    @Transactional
    public void removerUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }

    @Override
    public Usuario autenticarPorUsername(String username, String senha) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new IllegalArgumentException("Senha inválida");
        }

        return usuario;
    }
}