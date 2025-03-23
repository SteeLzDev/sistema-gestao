package com.oficina.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cliente;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenda> itens = new ArrayList<>();

    public Venda(Long id, String cliente, BigDecimal valorTotal, LocalDateTime dataHora, List<ItemVenda> itens) {
        this.id = id;
        this.cliente = cliente;
        this.valorTotal = valorTotal;
        this.dataHora = dataHora;
        this.itens = itens;
    }

    public Venda() {
        this.dataHora = LocalDateTime.now();
        this.valorTotal = BigDecimal.ZERO;

    }
    public Venda(String cliente) {
        this();
        this.cliente = cliente;
    }

    public void adicionarItem (ItemVenda item) {
        itens.add(item);
        item.setVenda(this);
        recalcularTotal();
    }

    public void removerItem(ItemVenda item){
        itens.remove(item);
        item.setVenda(null);
        recalcularTotal();
    }

    public void recalcularTotal() {
        this.valorTotal = itens.stream()
                .map(ItemVenda::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

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

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
        recalcularTotal();
    }
}
