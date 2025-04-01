package com.oficina.infrastructure.rest.dto;

import java.math.BigDecimal;

/**
 * DTO para serviços realizados em um período
 */
public class ServicoRealizadoDTO {
    private String tipo;
    private int quantidade;
    private BigDecimal valorTotal;
    private double tempoMedioAtendimento; // em minutos

    public ServicoRealizadoDTO() {
        this.valorTotal = BigDecimal.ZERO;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getTempoMedioAtendimento() {
        return tempoMedioAtendimento;
    }

    public void setTempoMedioAtendimento(double tempoMedioAtendimento) {
        this.tempoMedioAtendimento = tempoMedioAtendimento;
    }
}

