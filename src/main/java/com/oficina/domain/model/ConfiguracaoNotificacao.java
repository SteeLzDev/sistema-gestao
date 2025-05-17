package com.oficina.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NOTIFICACAO")
public class ConfiguracaoNotificacao extends Configuracao {

    @Column(name = "alertas_estoque_baixo")
    private Boolean alertasEstoqueBaixo = true;

    @Column(name = "relatorio_vendas")
    private Boolean relatoriosVendas = true;

    @Column(name = "alertas_login")
    private Boolean alertasLogin = false;

    @Column(name = "notificacoes_email")
    private Boolean notificacoesEmail = false;


    public Boolean getAlertasEstoqueBaixo() {
        return alertasEstoqueBaixo;
    }

    public void setAlertasEstoqueBaixo(Boolean alertasEstoqueBaixo) {
        this.alertasEstoqueBaixo = alertasEstoqueBaixo;
    }

    public Boolean getRelatoriosVendas() {
        return relatoriosVendas;
    }

    public void setRelatoriosVendas(Boolean relatoriosVendas) {
        this.relatoriosVendas = relatoriosVendas;
    }

    public Boolean getNotificacoesEmail() {
        return notificacoesEmail;
    }

    public void setNotificacoesEmail(Boolean notificacoesEmail) {
        this.notificacoesEmail = notificacoesEmail;
    }

    public Boolean getAlertasLogin() {
        return alertasLogin;
    }

    public void setAlertasLogin(Boolean alertasLogin) {
        this.alertasLogin = alertasLogin;
    }
}
