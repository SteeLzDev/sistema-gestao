package com.oficina.infrastructure.rest;


import com.oficina.application.port.PermissaoService;
import com.oficina.infrastructure.rest.dto.AtribuirPerfilDTO;
import com.oficina.infrastructure.rest.dto.AtualizarPermissoesDTO;
import com.oficina.infrastructure.rest.dto.PermissaoDTO;
import com.oficina.infrastructure.rest.dto.UsuarioPermissoesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/permissoes")
public class PermissaoController {


    @Autowired
    private PermissaoService permissaoService;

    @GetMapping
    public ResponseEntity<List<PermissaoDTO>> listarPermissoes() {
        return ResponseEntity.ok(permissaoService.listarTodasPermissoes());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<UsuarioPermissoesDTO> obterPermissoesDoUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(permissaoService.obterPermissoesDoUsuario(usuarioId));
    }

    @PostMapping("/atualizar")
    public ResponseEntity<Void> atualizarPermissoesDoUsuario(@RequestBody AtualizarPermissoesDTO dto){
        permissaoService.atualizarPermissoesDoUsuario(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verificar/{usuarioId}/{permissao}")
    public ResponseEntity<Boolean> verificarPermissao(
            @PathVariable Long usuarioId, @PathVariable String permissao){
        return ResponseEntity.ok(permissaoService.verificarPermissao(usuarioId, permissao));
    }


    @GetMapping("/usuario/{usuaruioId}/todas")
    public ResponseEntity<List<String>> obterTodasPermissoesDoUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(permissaoService.obterNomesPermissoesDoUsuario(usuarioId));
    }

    @PostMapping("/atribuir-perfil")
    public ResponseEntity<Void> atribuirPermissoesPorPerfil(@RequestBody AtribuirPerfilDTO dto) {
        permissaoService.atribuirPermissoesPorPerfil(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
