package com.oficina.infrastructure.rest.dto;

import com.oficina.domain.model.ConfiguracaoSeguranca;
import jakarta.validation.constraints.Min;

public class ConfiguracaoSegurancaDTO {


    private Long id;

    @Min(value = 1, message = "Tempo de sessão deve ser pelo menos 1 minuto")
    private Integer tempoSessao;

    @Min(value = 1, message = "Número de tentativas de login deve ser pelo menos 1")
    private Integer tentativasLogin;

    private Boolean autenticacaoDoisFatores;

    private Boolean politicaSenhasForte;

    public ConfiguracaoSegurancaDTO() {

    }

    public ConfiguracaoSegurancaDTO(ConfiguracaoSeguranca entity) {
        this.id = entity.getId();
        this.tempoSessao = entity.getTempoSessao();
        this.tentativasLogin = entity.getTentativasLogin();
        this.autenticacaoDoisFatores = entity.getAutenticacaoDoisFatores();
        this.politicaSenhasForte = entity.getPoliticaSenhaForte();
    }

    //Converter DTO para Entity

    public ConfiguracaoSeguranca toEntity(){
        ConfiguracaoSeguranca entity = new ConfiguracaoSeguranca();
        entity.setId(this.id);
        entity.setTempoSessao(this.tempoSessao);
        entity.setTentativasLogin(this.tentativasLogin);
        entity.setAutenticacaoDoisFatores(this.autenticacaoDoisFatores);
        entity.setPoliticaSenhaForte(this.politicaSenhasForte);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getPoliticaSenhasForte() {
        return politicaSenhasForte;
    }

    public void setPoliticaSenhasForte(Boolean politicaSenhasForte) {
        this.politicaSenhasForte = politicaSenhasForte;
    }
}
