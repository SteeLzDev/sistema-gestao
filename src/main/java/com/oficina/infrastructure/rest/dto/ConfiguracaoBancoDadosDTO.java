package com.oficina.infrastructure.rest.dto;

import com.oficina.domain.model.ConfiguracaoBancoDados;
import jakarta.validation.constraints.NotBlank;

public class ConfiguracaoBancoDadosDTO {

    private Long id;

    @NotBlank(message = "Host é Obrigatório")
    private String host;

    @NotBlank(message = "Porta é obrigatória")
    private String porta;

    @NotBlank(message = "Nome do banco é obrigatório")
    private String nomeBanco;

    @NotBlank(message = "Usuário é obrigatório")
    private String usuario;

    private String senha;

    private ConfiguracaoBancoDados.TipoBancoDados tipoBanco;

    public ConfiguracaoBancoDadosDTO() {

    }

    public ConfiguracaoBancoDadosDTO(ConfiguracaoBancoDados entity) {
        this.id = entity.getId();
        this.host = entity.getHost();
        this.porta = entity.getPorta();
        this.nomeBanco = entity.getNomeBanco();
        this.usuario = entity.getUsuario();
        this.senha = entity.getSenha();
        this.tipoBanco = entity.getTipoBanco();
    }

    //Converter DTO para Entity
    public ConfiguracaoBancoDados toEntity() {
        ConfiguracaoBancoDados entity = new ConfiguracaoBancoDados();
        entity.setId(this.id);
        entity.setHost(this.host);
        entity.setPorta(this.porta);
        entity.setNomeBanco(this.nomeBanco);
        entity.setUsuario(this.usuario);
        if (this.senha != null && !this.senha.isEmpty()) {
            entity.setSenha(this.senha);
        }
        entity.setTipoBanco(this.tipoBanco);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ConfiguracaoBancoDados.TipoBancoDados getTipoBanco() {
        return tipoBanco;
    }

    public void setTipoBanco(ConfiguracaoBancoDados.TipoBancoDados tipoBanco) {
        this.tipoBanco = tipoBanco;
    }
}
