package com.oficina.infrastructure.rest.dto;

public class AtribuirPerfilDTO {

    private Long usuarioId;
    private String perfil;

    public AtribuirPerfilDTO(){

    }

    public AtribuirPerfilDTO(Long usuarioId, String perfil) {
        this.usuarioId = usuarioId;
        this.perfil = perfil;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
