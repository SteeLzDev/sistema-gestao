package com.oficina.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendaDTO  {

    private Long id;
    private String cliente;
    private LocalDateTime dataHora;
    private BigDecimal valorTotal;
    private List<ItemVendaDTO> itens = new ArrayList<>();

    public VendaDTO(Long id, String cliente, BigDecimal valorTotal, LocalDateTime dataHora, List<ItemVendaDTO> itens) {
        this.id = id;
        this.cliente = cliente;
        this.valorTotal = valorTotal;
        this.dataHora = dataHora;
        this.itens = itens;
    }

    public VendaDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<ItemVendaDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaDTO> itens) {
        this.itens = itens;
    }
}
