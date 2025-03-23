package com.oficina.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "atendimentos")
public class Atendimento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String servico;

    @Column(nullable = false)
    private LocalDateTime inicio;

    @Column(nullable = false)
    private String atendente;

    @Column(nullable = false)
    private String status;

    public Atendimento(Long id, String nome, String servico, LocalDateTime inicio, String atendente, String status) {
        this.id = id;
        this.nome = nome;
        this.servico = servico;
        this.inicio = inicio;
        this.atendente = atendente;
        this.status = status;
    }
    public Atendimento(){
        this.inicio = LocalDateTime.now();
        this.status = "Em andamento";
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
