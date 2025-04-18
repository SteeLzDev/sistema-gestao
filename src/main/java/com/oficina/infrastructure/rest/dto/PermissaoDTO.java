package com.oficina.infrastructure.rest.dto;

public class PermissaoDTO {



    private Long id;
    private String nome;
    private String descricao;
    private boolean selecionada;


    public PermissaoDTO(){
    }

    public PermissaoDTO(Long id, String nome, String descricao, boolean selecionada) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.selecionada = selecionada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isSelecionada() {
        return selecionada;
    }

    public void setSelecionada(boolean selecionada) {
        this.selecionada = selecionada;
    }
}
