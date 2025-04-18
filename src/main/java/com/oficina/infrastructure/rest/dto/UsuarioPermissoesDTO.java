package com.oficina.infrastructure.rest.dto;

import java.util.List;

public class UsuarioPermissoesDTO {


    private Long usuarioId;
    private String nomeUsuario;
    private String perfil;
    private List<PermissaoDTO> permissoes;

    UsuarioPermissoesDTO() {
    }

    public UsuarioPermissoesDTO(Long usuarioId, String nomeUsuario, String perfil, List<PermissaoDTO> permissoes) {
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
        this.perfil = perfil;
        this.permissoes = permissoes;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public List<PermissaoDTO> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<PermissaoDTO> permissoes) {
        this.permissoes = permissoes;
    }



}
