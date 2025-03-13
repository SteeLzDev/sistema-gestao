package com.oficina.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table (name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nome;


    private String categoria;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal preco;

    public Produto(){

    }

    public Produto(Long id, String codigo, String nome , String categoria, Integer quantidade, BigDecimal preco) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.preco = preco;
    }
    public void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }
        this.quantidade += quantidade;
    }

    public void removerEstoque (int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A Quantidade deve se maior que zero");
        }
        if (this.quantidade < quantidade) {
            throw new IllegalArgumentException ("Estoque Insuficiente");
        }
        this.quantidade -= quantidade;
    }

    public boolean estoqueAbaixoDoMinimo (int minimoEstoque) {
        return this.quantidade < minimoEstoque;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}




