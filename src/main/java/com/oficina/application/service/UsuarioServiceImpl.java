package com.oficina.application.service;

import com.oficina.application.port.UsuarioService;
import com.oficina.domain.model.Permissao;
import com.oficina.domain.model.Usuario;
import com.oficina.domain.exception.EntidadeNaoEncontradaException;
import com.oficina.infrastructure.persistence.repository.PermissaoRepository;
import com.oficina.infrastructure.persistence.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PermissaoRepository permissaoRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, PermissaoRepository permissaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.permissaoRepository = permissaoRepository;
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
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário", "username" + username));
    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário", "email" + email));
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId() == 0) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        } else {
            Usuario usuarioExistente = buscarUsuarioPorId(usuario.getId());
            if (usuario.getSenha() != null && !usuario.getSenha().equals(usuarioExistente.getSenha())) {
                if (!usuario.getSenha().startsWith("$2a$")) {
                    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
                }
            } else {
                usuario.setSenha(usuarioExistente.getSenha());
            }
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void removerUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }

    @Transactional
    public void atualizarSenhasExistentes() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuario : usuarios) {
            if (usuario.getSenha() != null && !usuario.getSenha().startsWith("$2a$")) {
                usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
                usuarioRepository.save(usuario);
                System.out.println("Senha criptogradada para o usuário: " + usuario.getUsername());
            }
        }
        System.out.println("Migração concluída! :P");
    }

    @Override
    @Transactional
    public void inicializarPermissoesParaUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario.getPermissoes() == null || usuario.getPermissoes().isEmpty()) {
            List<Permissao> permissoes = new ArrayList<>();

            String perfil = usuario.getPerfil().toUpperCase();
            switch (perfil) {
                case "ADMIN":
                case "ADMINISTRADOR":
                    adicionarPermissaoSeExistir(permissoes, "USUARIOS_VISUALIZAR");
                    adicionarPermissaoSeExistir(permissoes, "USUARIOS_CRIAR");
                    adicionarPermissaoSeExistir(permissoes, "USUARIOS_EDITAR");
                    adicionarPermissaoSeExistir(permissoes, "USUARIOS_REMOVER");
                    adicionarPermissaoSeExistir(permissoes, "USUARIOS_PERMISSOES");
                    break;
            }
            usuario.setPermissoes(permissoes);
            salvarUsuario(usuario);
        }
    }

    private void adicionarPermissaoSeExistir(List<Permissao> permissoes, String nomePermissao) {
        Optional<Permissao> permissaoOpt = permissaoRepository.findByNome(nomePermissao);
        permissaoOpt.ifPresent(permissoes::add);
    }
}