package com.oficina.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RelatorioDTO {
    private String periodo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private ResumoVendasDTO resumoVendas;
    private List<ProdutoVendidoDTO> produtosMaisVendidos;
    private List<ServicoRealizadoDTO> servicosRealizados;
    private Map<String, BigDecimal> vendasPorDia;

    // Getters e Setters
    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public ResumoVendasDTO getResumoVendas() {
        return resumoVendas;
    }

    public void setResumoVendas(ResumoVendasDTO resumoVendas) {
        this.resumoVendas = resumoVendas;
    }

    public List<ProdutoVendidoDTO> getProdutosMaisVendidos() {
        return produtosMaisVendidos;
    }

    public void setProdutosMaisVendidos(List<ProdutoVendidoDTO> produtosMaisVendidos) {
        this.produtosMaisVendidos = produtosMaisVendidos;
    }

    public List<ServicoRealizadoDTO> getServicosRealizados() {
        return servicosRealizados;
    }

    public void setServicosRealizados(List<ServicoRealizadoDTO> servicosRealizados) {
        this.servicosRealizados = servicosRealizados;
    }

    public Map<String, BigDecimal> getVendasPorDia() {
        return vendasPorDia;
    }

    public void setVendasPorDia(Map<String, BigDecimal> vendasPorDia) {
        this.vendasPorDia = vendasPorDia;
    }
}

