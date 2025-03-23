package com.oficina.infrastructure.rest;

import com.oficina.application.port.VendaService;
import com.oficina.domain.exception.EstoqueInsuficienteException;
import com.oficina.domain.model.ItemVenda;
import com.oficina.domain.model.Venda;
import com.oficina.infrastructure.rest.dto.ItemVendaDTO;
import com.oficina.infrastructure.rest.dto.VendaDTO;
import org.aspectj.weaver.ast.Literal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaService vendaService;

    @Autowired
    public VendaController (VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @GetMapping
    public ResponseEntity<List<VendaDTO>> listarVendas() {
        List<Venda> vendas = vendaService.listarTodasVendas();
        List<VendaDTO> vendasDTO = new ArrayList<>();
        for (Venda venda : vendas) {
            vendasDTO.add(convertToDTO(venda));
        }
        return ResponseEntity.ok(vendasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDTO> buscarVenda(@PathVariable Long id) {
        Venda venda = vendaService.buscarVendaPorId(id);
        return ResponseEntity.ok(convertToDTO(venda));

    }

    @GetMapping("/cliente/{cliente}")
    public ResponseEntity<List<VendaDTO>> buscarVendasPorCliente(@PathVariable String cliente) {
        List<Venda> vendas = vendaService.buscarVendasPorCliente(cliente);
        List<VendaDTO> vendasDTO = new ArrayList<>();

        for (Venda venda : vendas) {
            vendasDTO.add(convertToDTO(venda));
        }
        return ResponseEntity.ok(vendasDTO);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<VendaDTO>> buscarVendasPorPeriodo (
        @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime inicio,
                @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME)
                LocalDateTime fim) {
        List<Venda> vendas = vendaService.buscarVendasPorPeriodo(inicio, fim);
        List<VendaDTO> vendasDTO = new ArrayList<>();

        for (Venda venda : vendas) {


        }
        return ResponseEntity.ok(vendasDTO);

    }

    @PostMapping
    public ResponseEntity<?> registrarVenda (@RequestBody VendaDTO vendaDTO) {
        try {
            Venda venda = vendaService.registrarVenda(vendaDTO);
            return new ResponseEntity<>(convertToDTO(venda), HttpStatus.CREATED);
        } catch (EstoqueInsuficienteException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarVenda (@PathVariable Long id) {
        vendaService.cancelarVenda(id);
        return ResponseEntity.noContent().build();
    }
    private VendaDTO convertToDTO(Venda venda) {
        VendaDTO dto = new VendaDTO();
        dto.setId(venda.getId());
        dto.setCliente(venda.getCliente());
        dto.setDataHora(venda.getDataHora());
        dto.setValorTotal(venda.getValorTotal());

        List<ItemVendaDTO> itensDTO = new ArrayList<>();
        for (ItemVenda item : venda.getItens()) {
            ItemVendaDTO itemDTO = new ItemVendaDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProdutoId(item.getProduto().getId());
            itemDTO.setProdutoNome(item.getProduto().getNome());
            itemDTO.setSubtotal(item.getSubtotal());
            itensDTO.add(itemDTO);

        }
        dto.setItens(itensDTO);
        return dto;
    }





}
