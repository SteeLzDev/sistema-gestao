package com.oficina.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "permissoes")
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column
    private String descricao;

    public Permissao() {
    }

    public Permissao(String nome) {
        this.nome = nome;
    }

    public Permissao(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permissao permissao = (Permissao) o;
        return nome != null && nome.equals(permissao.nome);
    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }

    @Override
    public String toString() {
        return nome;
    }
}