package com.oficina.application.port;

import com.oficina.domain.model.Atendimento;
import com.oficina.domain.model.Fila;
import com.oficina.infrastructure.rest.dto.AtendimentoDTO;
import com.oficina.infrastructure.rest.dto.FilaDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilaService {
    List<Fila> listarClientesNaFila();
    List<Atendimento> listarAtendimentosEmAndamento();
    Fila adicionarClienteNaFila(FilaDTO filaDTO);
    Atendimento iniciarAtendimento(Long filaId, String atendente);
    void finalizarAtendimento(Long atendimentoId);
    void removerClienteDaFila(Long filaId);
}
