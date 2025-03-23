package com.oficina.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_venda")
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    private Venda venda;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    public ItemVenda() {

    }

    public ItemVenda(Produto produto, BigDecimal precoUnitario, Integer quantidade, BigDecimal subtotal) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
        this.subtotal = this.precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        if( produto != null && this.quantidade != null) {
            this.precoUnitario = produto.getPreco();
            this.subtotal = this.precoUnitario.multiply(BigDecimal.valueOf(this.quantidade));
        }
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        if(this.precoUnitario != null && quantidade != null){
            this.subtotal = this.precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        }
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setProcoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
        if(precoUnitario != null && this.quantidade != null) {
            this.subtotal = precoUnitario.multiply(BigDecimal.valueOf(this.quantidade));
        }
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
