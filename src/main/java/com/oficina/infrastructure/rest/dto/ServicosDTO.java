package com.oficina.infrastructure.rest.dto;

import java.util.List;

/**
 * DTO para informações de serviços no dashboard
 */
public class ServicosDTO {
    private int pendentes;
    private int concluidos;
    private List<ServicoResumoDTO> itens;

    public int getPendentes() {
        return pendentes;
    }

    public void setPendentes(int pendentes) {
        this.pendentes = pendentes;
    }

    public int getConcluidos() {
        return concluidos;
    }

    public void setConcluidos(int concluidos) {
        this.concluidos = concluidos;
    }

    public List<ServicoResumoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ServicoResumoDTO> itens) {
        this.itens = itens;
    }
}

