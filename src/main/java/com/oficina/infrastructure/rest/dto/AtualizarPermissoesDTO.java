package com.oficina.infrastructure.rest.dto;

import java.util.List;

public class AtualizarPermissoesDTO {

    private Long usuarioId;
    private List<Long> permissaoIds;

    public AtualizarPermissoesDTO(){
    }

    public AtualizarPermissoesDTO(Long usuarioId, List<Long> permissaoIds) {
        this.usuarioId = usuarioId;
        this.permissaoIds = permissaoIds;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<Long> getPermissaoIds() {
        return permissaoIds;
    }

    public void setPermissaoIds(List<Long> permissaoIds) {
        this.permissaoIds = permissaoIds;
    }
}
