package com.oficina.infrastructure.rest.dto;

import java.math.BigDecimal;

public class ResumoVendasDTO {
    private int quantidadeVendas;
    private BigDecimal valorTotal;
    private BigDecimal ticketMedio;
    private BigDecimal crescimento; // percentual em relação ao período anterior

    public ResumoVendasDTO() {
        this.valorTotal = BigDecimal.ZERO;
        this.ticketMedio = BigDecimal.ZERO;
        this.crescimento = BigDecimal.ZERO;
    }

    public int getQuantidadeVendas() {
        return quantidadeVendas;
    }

    public void setQuantidadeVendas(int quantidadeVendas) {
        this.quantidadeVendas = quantidadeVendas;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getTicketMedio() {
        return ticketMedio;
    }

    public void setTicketMedio(BigDecimal ticketMedio) {
        this.ticketMedio = ticketMedio;
    }

    public BigDecimal getCrescimento() {
        return crescimento;
    }

    public void setCrescimento(BigDecimal crescimento) {
        this.crescimento = crescimento;
    }
}

