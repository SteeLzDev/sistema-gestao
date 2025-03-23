package com.oficina.infrastructure.rest.dto;

import java.time.LocalDateTime;

public class FilaDTO {

    private Long id;
    private String nome;
    private String servico;
    private String telefone;
    private String observacoes;
    private String prioridade;
    private LocalDateTime chegada;
    private String status;
    private String espera;

    public FilaDTO(Long id, String nome, String servico, String telefone, String observacoes, String prioridade, LocalDateTime chegada, String status, String espera) {
        this.id = id;
        this.nome = nome;
        this.servico = servico;
        this.telefone = telefone;
        this.observacoes = observacoes;
        this.prioridade = prioridade;
        this.chegada = chegada;
        this.status = status;
        this.espera = espera;
    }

    public FilaDTO(){

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

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String teledone) {
        this.telefone = teledone;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getChegada() {
        return chegada;
    }

    public void setChegada(LocalDateTime chegada) {
        this.chegada = chegada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEspera() {
        return espera;
    }

    public void setEspera(String espera) {
        this.espera = espera;
    }
}
