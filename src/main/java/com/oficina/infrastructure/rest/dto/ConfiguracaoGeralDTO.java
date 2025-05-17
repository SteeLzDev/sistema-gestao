package com.oficina.infrastructure.rest.dto;

import com.oficina.domain.model.ConfiguracaoGeral;
import com.oficina.domain.model.ConfiguracaoSeguranca;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class    ConfiguracaoGeralDTO {


    private Long id;

    @NotBlank(message = "Nome da Empresa é obrigatório")
    private String nomeEmpresa;

    private String endereco;

    private String telefone;

    @Email(message = "Email deve ser válido")
    private String email;

    private Boolean temaEscuro;

    public ConfiguracaoGeralDTO() {

    }

    public ConfiguracaoGeralDTO(ConfiguracaoGeral entity) {
        this.id = entity.getId();
        this.nomeEmpresa = entity.getNomeEmpresa();
        this.endereco = entity.getEndereco();
        this.telefone = entity.getTelefone();
        this.email = entity.getEmail();
        this.temaEscuro = entity.getTemaEscuro();
    }

    //Converter DTO para Entity
    public ConfiguracaoGeral toEntity() {
        ConfiguracaoGeral entity = new ConfiguracaoGeral();
        entity.setId(this.id);
        entity.setNomeEmpresa(this.nomeEmpresa);
        entity.setEndereco(this.endereco);
        entity.setTelefone(this.telefone);
        entity.setEmail(this.email);
        entity.setTemaEscuro(this.temaEscuro);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
