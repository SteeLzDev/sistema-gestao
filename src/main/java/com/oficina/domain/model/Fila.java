package com.oficina.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "fila_atendimento")
public class Fila {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String servico;

    @Column
    private String telefone;

    @Column
    private String observacoes;

    @Column(nullable = false)
    private String prioridade;

    @Column(nullable = false)
    private LocalDateTime chegada;

    @Column(nullable = false)
    private String status;

    public Fila(long id, String nome, String servico, String telefone, String observacoes, String prioridade, String status, LocalDateTime chegada) {
        this.id = id;
        this.nome = nome;
        this.servico = servico;
        this.telefone = telefone;
        this.observacoes = observacoes;
        this.prioridade = prioridade;
        this.status = status;
        this.chegada = chegada;
    }

    public Fila() {
        this.chegada = LocalDateTime.now();
        this.status = "Aguardando";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
}
