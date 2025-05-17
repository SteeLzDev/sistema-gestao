package com.oficina.infrastructure.rest.dto;

import com.oficina.domain.model.ConfiguracaoNotificacao;

public class ConfiguracaoNotificacaoDTO {

    private Long id;

    private Boolean alertaEstoqueBaixo;

    private Boolean relatoriosVendas;

    private Boolean alertasLogin;

    private Boolean notificacoesEmail;

    public ConfiguracaoNotificacaoDTO() {

    }

    public ConfiguracaoNotificacaoDTO(ConfiguracaoNotificacao entity) {
        this.id = entity.getId();
        this.alertaEstoqueBaixo = entity.getAlertasEstoqueBaixo();
        this.relatoriosVendas = entity.getRelatoriosVendas();
        this.alertasLogin = entity.getAlertasLogin();
        this.notificacoesEmail = entity.getNotificacoesEmail();
    }

    //Converter DTO em Entity
    public ConfiguracaoNotificacao toEntity() {
        ConfiguracaoNotificacao entity = new ConfiguracaoNotificacao();
        entity.setId(this.id);
        entity.setAlertasEstoqueBaixo(this.alertaEstoqueBaixo);
        entity.setRelatoriosVendas(this.relatoriosVendas);
        entity.setAlertasLogin(this.alertasLogin);
        entity.setNotificacoesEmail(this.notificacoesEmail);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAlertaEstoqueBaixo() {
        return alertaEstoqueBaixo;
    }

    public void setAlertaEstoqueBaixo(Boolean alertaEstoqueBaixo) {
        this.alertaEstoqueBaixo = alertaEstoqueBaixo;
    }

    public Boolean getRelatoriosVendas() {
        return relatoriosVendas;
    }

    public void setRelatoriosVendas(Boolean relatoriosVendas) {
        this.relatoriosVendas = relatoriosVendas;
    }

    public Boolean getAlertasLogin() {
        return alertasLogin;
    }

    public void setAlertasLogin(Boolean alertasLogin) {
        this.alertasLogin = alertasLogin;
    }

    public Boolean getNotificacoesEmail() {
        return notificacoesEmail;
    }

    public void setNotificacoesEmail(Boolean notificacoesEmail) {
        this.notificacoesEmail = notificacoesEmail;
    }
}
