package com.oficina.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("GERAL")

public class ConfiguracaoGeral  extends Configuracao {

    @NotBlank(message = "Nome da empresa é obrigatório")
    @Column(name = "nome_empresa")
    private String nomeEmpresa;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefone")
    private String telefone;

    @Email(message = "Email deve ser válido")
    @Column(name = "email")
    private String email;

    @Column(name = "tema_escuro")
    private Boolean temaEscuro = false;

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getTemaEscuro() {
        return temaEscuro;
    }

    public void setTemaEscuro(Boolean temaEscuro) {
        this.temaEscuro = temaEscuro;
    }
}
