package com.oficina.infrastructure.rest.dto;

import java.util.List;

/**
 * DTO para informações de estoque no dashboard
 */
public class EstoqueDTO {
    private int total;
    private int baixoEstoque;
    private List<ProdutoResumoDTO> itens;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getBaixoEstoque() {
        return baixoEstoque;
    }

    public void setBaixoEstoque(int baixoEstoque) {
        this.baixoEstoque = baixoEstoque;
    }

    public List<ProdutoResumoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ProdutoResumoDTO> itens) {
        this.itens = itens;
    }
}

