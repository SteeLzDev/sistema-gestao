package com.oficina.application.port;

import com.oficina.infrastructure.rest.dto.AtualizarPermissoesDTO;
import com.oficina.infrastructure.rest.dto.PermissaoDTO;
import com.oficina.infrastructure.rest.dto.UsuarioPermissoesDTO;

import java.util.List;

public interface PermissaoService {

    void inicializarPermissoes();
    List<PermissaoDTO> listarTodasPermissoes();
    UsuarioPermissoesDTO obterPermissoesDoUsuario(Long usuarioId);
    void atualizarPermissoesDoUsuario(AtualizarPermissoesDTO dto);
    boolean verificarPermissao(Long usuarioId, String nomePermissao);
    List<String> obterNomesPermissoesDoUsuario(Long usuarioId);


}
