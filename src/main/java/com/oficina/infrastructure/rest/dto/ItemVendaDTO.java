package com.oficina.infrastructure.rest.dto;



import java.math.BigDecimal;

public class ItemVendaDTO {

    private Long id;
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;

    public ItemVendaDTO(Long id, Long produtoId, String produtoNome, BigDecimal precoUnitario, Integer quantidade, BigDecimal subtotal) {
        this.id = id;
        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
    }
    public ItemVendaDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
