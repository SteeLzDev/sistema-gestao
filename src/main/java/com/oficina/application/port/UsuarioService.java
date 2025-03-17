package com.oficina.application.port;

import com.oficina.domain.model.Usuario;
import java.util.List;

public interface UsuarioService {


        List<Usuario> listarUsuarios();
        Usuario buscarUsuarioPorId(Long id);
        Usuario buscarUsuarioPorUsername(String username);
        Usuario salvarUsuario(Usuario usuario);
        Usuario buscarUsuarioPorEmail (String email);
        //Usuario atualizarUsuario(Long id, Usuario usuario);
        void removerUsuario(Long id);
        //Usuario autenticarPorUsername(String username, String senha);
    }
