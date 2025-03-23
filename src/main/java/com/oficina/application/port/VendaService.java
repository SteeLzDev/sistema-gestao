package com.oficina.application.port;

import com.oficina.domain.model.Venda;
import com.oficina.infrastructure.rest.dto.VendaDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaService {

    List<Venda> listarTodasVendas();
    Venda buscarVendaPorId(Long id);
    List<Venda> buscarVendasPorCliente(String cliente);
    List<Venda> buscarVendasPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    Venda registrarVenda(VendaDTO vendaDTO);
    void cancelarVenda(Long id);
}
