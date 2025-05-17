package com.oficina.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;

@Entity
@DiscriminatorValue("SEGURANCA")
public class ConfiguracaoSeguranca extends Configuracao {

    @Min(value = 1, message = "Tempo de sessão deve ser pelo menos 1 minuto")
    @Column(name = "tempo_sessao")
    private Integer tempoSessao = 30;

    @Min(value = 1, message = "Número de tentativas de login deve ser pelo menos 1")
    @Column(name = "tentativas_login")
    private Integer tentativasLogin = 3;

    @Column(name = "autenticacao_dois_fatores")
    private Boolean autenticacaoDoisFatores = false;

    @Column(name = "politica_senha_forte")
    private Boolean politicaSenhaForte = true;

    public Integer getTempoSessao() {
        return tempoSessao;
    }

    public void setTempoSessao(Integer tempoSessao) {
        this.tempoSessao = tempoSessao;
    }

    public Integer getTentativasLogin() {
        return tentativasLogin;
    }

    public void setTentativasLogin(Integer tentativasLogin) {
        this.tentativasLogin = tentativasLogin;
    }

    public Boolean getAutenticacaoDoisFatores() {
        return autenticacaoDoisFatores;
    }

    public void setAutenticacaoDoisFatores(Boolean autenticacaoDoisFatores) {
        this.autenticacaoDoisFatores = autenticacaoDoisFatores;
    }

    public Boolean getPoliticaSenhaForte() {
        return politicaSenhaForte;
    }

    public void setPoliticaSenhaForte(Boolean politicaSenhaForte) {
        this.politicaSenhaForte = politicaSenhaForte;
    }
}
