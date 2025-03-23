package com.oficina.application.service;


import com.oficina.application.port.EstoqueService;
import com.oficina.application.port.VendaService;
import com.oficina.domain.exception.EntidadeNaoEncontradaException;
import com.oficina.domain.exception.EstoqueInsuficienteException;
import com.oficina.domain.model.ItemVenda;
import com.oficina.domain.model.Produto;
import com.oficina.domain.model.Venda;
import com.oficina.infrastructure.persistence.repository.VendaRepository;
import com.oficina.infrastructure.rest.dto.ItemVendaDTO;
import com.oficina.infrastructure.rest.dto.VendaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class VendaServiceImpl implements VendaService {

    private final VendaRepository vendaRepository;
    private final EstoqueService estoqueService;

    @Autowired
    public VendaServiceImpl(VendaRepository vendaRepository, EstoqueService estoqueService) {
        this.vendaRepository = vendaRepository;
        this.estoqueService = estoqueService;
    }

    @Override
    public List<Venda> listarTodasVendas() {
        return vendaRepository.findAll();
    }

    @Override
    public Venda buscarVendaPorId(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Venda", id));
    }


    @Override
    public List<Venda> buscarVendasPorCliente(String cliente) {
        return vendaRepository.findByClienteContainingIgnoreCase(cliente);
    }

    @Override
    public List<Venda>buscarVendasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.findByDataHoraBetween(inicio, fim);
    }

    @Override
    @Transactional
    public Venda registrarVenda (VendaDTO vendaDTO) {
        // Criar a venda
        Venda venda = new Venda();
        venda.setCliente(vendaDTO.getCliente());
        venda.setDataHora(LocalDateTime.now());

        // Adicionar itens à venda
        for (ItemVendaDTO itemDTO : vendaDTO.getItens()) {
            Produto produto = estoqueService.buscarProdutoPorId(itemDTO.getProdutoId());

            // Verificar se há estoque suficiente
            if(produto.getQuantidade() < itemDTO.getQuantidade()) {
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para o produto" + produto.getNome() +
                                ". Disponível: " + produto.getQuantidade() +
                                ". Solicitado: " + itemDTO.getQuantidade()
                );
            }
            //Criar o item de venda
            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setProcoUnitario(produto.getPreco());
            item.setSubtotal(produto.getPreco().multiply(java.math.BigDecimal.valueOf(itemDTO.getQuantidade())));

            venda.adicionarItem(item);

            //Atualizar Estoque
            estoqueService.removerEstoque(produto.getId(), itemDTO.getQuantidade());

        }
        //Salvar Venda
        return vendaRepository.save(venda);
    }

    @Override
    @Transactional
    public void cancelarVenda(Long id) {
        Venda venda = buscarVendaPorId(id);

        //Devolver os produtos ao estoque
        for (ItemVenda item : venda.getItens()) {
            estoqueService.adicionarEstoque(item.getProduto().getId(), item.getQuantidade());
        }

        //Remover venda
        vendaRepository.delete(venda);
    }

}
