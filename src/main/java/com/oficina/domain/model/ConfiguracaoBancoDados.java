package com.oficina.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("BANCO_DADOS")
public class ConfiguracaoBancoDados extends Configuracao {

    @NotBlank(message = "Host é obrigatório")
    @Column(name = "host")
    private String host;

    @NotBlank(message = "Porta é obrigatória")
    @Column(name = "porta")
    private String porta;

    @NotBlank(message = "Banco de dados é obrigatório")
    @Column(name = "nome_banco")
    private String nomeBanco;

    @NotBlank(message = "Usuário é obrigatório")
    @Column(name = "usuario")
    private String usuario;

    @Column(name = "senha")
    private String senha;

    @Column(name = "tipo_banco")
    @Enumerated(EnumType.STRING)
    private TipoBancoDados tipoBanco = TipoBancoDados.POSTGRES;

    //Tipo de banco de dados
    public enum TipoBancoDados {
        H2, POSTGRES, MYSQL, ORACLE
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

    public TipoBancoDados getTipoBanco() {
        return tipoBanco;
    }

    public void setTipoBanco(TipoBancoDados tipoBanco) {
        this.tipoBanco = tipoBanco;
    }
}
