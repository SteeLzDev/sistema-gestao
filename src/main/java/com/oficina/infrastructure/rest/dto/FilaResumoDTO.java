package com.oficina.infrastructure.rest.dto;

import java.util.List;

/**
 * DTO para resumo da fila de atendimento no dashboard
 */
public class FilaResumoDTO {
    private int aguardando;
    private int emAtendimento;
    private List<ClienteFilaDTO> itens;

    public int getAguardando() {
        return aguardando;
    }

    public void setAguardando(int aguardando) {
        this.aguardando = aguardando;
    }

    public int getEmAtendimento() {
        return emAtendimento;
    }

    public void setEmAtendimento(int emAtendimento) {
        this.emAtendimento = emAtendimento;
    }

    public List<ClienteFilaDTO> getItens() {
        return itens;
    }

    public void setItens(List<ClienteFilaDTO> itens) {
        this.itens = itens;
    }
}

