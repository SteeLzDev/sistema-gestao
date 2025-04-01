package com.oficina.infrastructure.rest.dto;

import java.util.List;


public class VendasRecentesDTO {
    private int total;
    private List<VendaResumoDTO> itens;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<VendaResumoDTO> getItens() {
        return itens;
    }

    public void setItens(List<VendaResumoDTO> itens) {
        this.itens = itens;
    }
}

