package com.oficina.infrastructure.rest;


import com.oficina.application.port.FilaService;
import com.oficina.domain.model.Atendimento;
import com.oficina.domain.model.Fila;
import com.oficina.infrastructure.rest.dto.AtendimentoDTO;
import com.oficina.infrastructure.rest.dto.FilaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/fila")
public class FilaController {

    private final FilaService filaService;

    @Autowired
    public FilaController(FilaService filaService){
        this.filaService = filaService;
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<FilaDTO>> listarClientesnaFila() {
        List<Fila> clientes = filaService.listarClientesNaFila();
        List<FilaDTO> clientesDTO = new ArrayList<>();

        for (Fila cliente : clientes) {
            FilaDTO dto = convertToDTO(cliente);
            clientesDTO.add(dto);
        }
        return ResponseEntity.ok(clientesDTO);
    }

    @GetMapping("/atendimentos")
    public ResponseEntity<List<AtendimentoDTO>> listarAtendimentosEmAndamento() {
        List<Atendimento> atendimentos = filaService.listarAtendimentosEmAndamento();
        List<AtendimentoDTO> atendimentoDTO = new ArrayList<>();
        for (Atendimento atendimento: atendimentos) {
            AtendimentoDTO dto = convertToDTO(atendimento);
            atendimentoDTO.add(dto);
        }
        return ResponseEntity.ok(atendimentoDTO);
    }

    @PostMapping("/adicionar")
    public ResponseEntity<FilaDTO> adicionarClienteNaFila(@RequestBody FilaDTO filaDTO) {
        Fila fila = filaService.adicionarClienteNaFila(filaDTO);
        return new ResponseEntity<>(convertToDTO(fila), HttpStatus.CREATED);
    }

    @PostMapping("/atender/{id}")
    public ResponseEntity<AtendimentoDTO> iniciarAtendimento(
     @PathVariable Long id, @RequestParam String atendente) {
     Atendimento atendimento = filaService.iniciarAtendimento(id, atendente);
     return ResponseEntity.ok(convertToDTO(atendimento));
    }

    @PostMapping("finalizar/{id}/")
    public ResponseEntity<Void> finalizarAtendimento(@PathVariable Long id) {
        filaService.finalizarAtendimento(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerClienteDaFila(@PathVariable Long id) {
        filaService.removerClienteDaFila(id);
        return ResponseEntity.noContent().build();
    }





    private FilaDTO convertToDTO(Fila fila) {
        FilaDTO dto = new FilaDTO();
        dto.setId(fila.getId());
        dto.setNome(fila.getNome());
        dto.setServico(fila.getServico());
        dto.setTelefone(fila.getTelefone());
        dto.setPrioridade(fila.getPrioridade());
        dto.setChegada(LocalDateTime.now());
        dto.setStatus(fila.getStatus());

        //Calcular tempo de espera
        Duration duracao = Duration.between(fila.getChegada(), LocalDateTime.now());
        long minutos = duracao.toMinutes();
        dto.setEspera(minutos + "min");


        return dto;
    }

    private AtendimentoDTO convertToDTO(Atendimento atendimento) {
        AtendimentoDTO dto = new AtendimentoDTO();
        dto.setId(atendimento.getId());
        dto.setNome(atendimento.getNome());
        dto.setServico(atendimento.getServico());
        dto.setInicio(atendimento.getInicio());
        dto.setAtendente(atendimento.getAtendente());
        dto.setStatus(atendimento.getStatus());

        return dto;
    }



}
