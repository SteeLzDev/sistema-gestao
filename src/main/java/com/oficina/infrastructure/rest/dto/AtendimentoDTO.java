package com.oficina.infrastructure.rest.dto;

import java.time.LocalDateTime;

public class AtendimentoDTO {

    private Long id;
    private String nome;
    private String servico;
    private LocalDateTime inicio;
    private String atendente;
    private String status;

    public AtendimentoDTO(Long id, String nome, String servico, LocalDateTime inicio, String atendente, String status) {
        this.id = id;
        this.nome = nome;
        this.servico = servico;
        this.inicio = inicio;
        this.atendente = atendente;
        this.status = status;
    }
    public AtendimentoDTO (){

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

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public String getAtendente() {
        return atendente;
    }

    public void setAtendente(String atendente) {
        this.atendente = atendente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
