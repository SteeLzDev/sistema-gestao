package com.oficina.infrastructure.rest.dto;

import java.util.List;

/**
 * DTO principal para o dashboard com dados consolidados
 */
public class DashboardDTO {
    private VendasRecentesDTO vendasRecentes;
    private EstoqueDTO estoque;
    private FilaResumoDTO fila;  // Alterado de FilaDTO para FilaResumoDTO
    private ServicosDTO servicos;

    // Getters e Setters
    public VendasRecentesDTO getVendasRecentes() {
        return vendasRecentes;
    }

    public void setVendasRecentes(VendasRecentesDTO vendasRecentes) {
        this.vendasRecentes = vendasRecentes;
    }

    public EstoqueDTO getEstoque() {
        return estoque;
    }

    public void setEstoque(EstoqueDTO estoque) {
        this.estoque = estoque;
    }

    public FilaResumoDTO getFila() {  // Alterado de FilaDTO para FilaResumoDTO
        return fila;
    }

    public void setFila(FilaResumoDTO fila) {  // Alterado de FilaDTO para FilaResumoDTO
        this.fila = fila;
    }

    public ServicosDTO getServicos() {
        return servicos;
    }

    public void setServicos(ServicosDTO servicos) {
        this.servicos = servicos;
    }
}

