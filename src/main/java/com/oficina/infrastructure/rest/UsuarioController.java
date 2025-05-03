package com.oficina.infrastructure.rest;

import com.oficina.application.port.UsuarioService;
import com.oficina.domain.exception.EntidadeNaoEncontradaException;
import com.oficina.domain.model.Permissao;
import com.oficina.domain.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario usuarioAtualizado = usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok(usuarioAtualizado);

    }

    @PutMapping ("/{id}/permissoes")
    public ResponseEntity<?> atualizarPermissoes (@PathVariable Long id, @RequestBody List<Permissao> permissoes) {
        logger.info("Recebida solicitação para atualizar permissoes do usuário ID: {}", id);
        logger.info("Permissões recebidas: {}", permissoes);

        try{
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);
            usuario.setPermissoes(permissoes);
            usuarioService.salvarUsuario(usuario);

            logger.info("Permissoes atualizadas com sucesso para o usuário ID : {}", id);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Permissões atualizadas com sucesso", "permissoes", permissoes
            ));
        } catch (EntidadeNaoEncontradaException e ) {
            logger.error("Usuario não encontrado: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar permissões: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/permissoes")
    public ResponseEntity<?> obterPermissoes(@PathVariable Long id) {
        logger.info("Recebida solicitação para obter permissão do usuário ID {}", id);

        try {
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);
            List<Permissao> permissoes = usuario.getPermissoes();

            logger.info("Permissões do usuário {} : {}", id, permissoes);
            return ResponseEntity.ok(permissoes != null ? permissoes : List.of());

        } catch (EntidadeNaoEncontradaException e ) {
            logger.error("Usuário não encontrado: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erro ao obter permissões: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/inicializar-permissoes")
    public ResponseEntity<?> inicializarPermissoes() {
        logger.info("Recebida solicitação para inicializar permissões para todos os usuários");

        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();
            int count = 0;
            for (Usuario usuario : usuarios) {
                if (usuario.getPermissoes() == null || usuario.getPermissoes().isEmpty()) {
                    usuarioService.inicializarPermissoesParaUsuario(usuario.getId());
                    count ++;

                }
            }
            logger.info("Permissões inicializadas para {} usuários" , count);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Permissões realizadas com sucesso", "count", count
            ));
        } catch (Exception e) {
            logger.error("Erro ao inicializar permissões {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        logger.info("Recebida solicitação para remover usuário ID: {}" , id);

        try {
            usuarioService.removerUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e ) {
            logger.error("Usuário não encontrado: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erro ao remover usuário: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}