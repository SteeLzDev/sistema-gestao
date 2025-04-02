package com.oficina.application.service;

import com.oficina.application.port.FilaService;
import com.oficina.domain.exception.EntidadeNaoEncontradaException;
import com.oficina.domain.model.Atendimento;
import com.oficina.domain.model.Fila;
import com.oficina.infrastructure.persistence.repository.AtendimentoRepository;
import com.oficina.infrastructure.persistence.repository.FilaRepository;
import com.oficina.infrastructure.rest.dto.FilaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FilaServiceImpl implements FilaService {

    private final FilaRepository filaRepository;
    private final AtendimentoRepository atendimentoRepository;

    @Autowired
    public FilaServiceImpl(FilaRepository filaRepository, AtendimentoRepository atendimentoRepository) {
        this.filaRepository = filaRepository;
        this.atendimentoRepository = atendimentoRepository;
    }

    @Override
    public List<Fila> listarClientesNaFila() {
        return filaRepository.findByStatusOrderByPrioridadeDescChegadaAsc("Aguardando");
    }

    @Override
    public List <Atendimento> listarAtendimentosEmAndamento() {
        return atendimentoRepository.findByStatus("Em andamento");
    }

    @Override
    @Transactional
    public Fila adicionarClienteNaFila(FilaDTO filaDTO) {
        Fila fila = new Fila();
        fila.setNome(filaDTO.getNome());
        fila.setServico(filaDTO.getServico());
        fila.setTelefone(filaDTO.getTelefone());
        fila.setObservacoes(filaDTO.getObservacoes());
        fila.setPrioridade(filaDTO.getPrioridade());
        fila.setChegada(LocalDateTime.now());
        fila.setStatus("Aguardando");

        return filaRepository.save(fila);

    }

    @Override
    @Transactional
    public Atendimento iniciarAtendimento(Long filaId, String atendente) {
        Fila fila = filaRepository.findById(filaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente na fila", filaId));

        // Atualizar status do cliente na fila
        fila.setStatus("Em Atendimento");
        filaRepository.save(fila);

        //Criar Novo atendimento
        Atendimento atendimento = new Atendimento();
        atendimento.setNome(fila.getNome());
        atendimento.setServico(fila.getServico());
        atendimento.setInicio(LocalDateTime.now());
        atendimento.setAtendente(atendente);
        atendimento.setStatus("Em andamento");

        return atendimentoRepository.save(atendimento);
    }

    @Override
    @Transactional
    public void finalizarAtendimento(Long atendimentoId) {
        Atendimento atendimento = atendimentoRepository.findById(atendimentoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Atendimento", atendimentoId));

        atendimento.setStatus("Finalizado");
        atendimentoRepository.save(atendimento);

    }

    @Override
    @Transactional
    public void removerClienteDaFila(Long filaId) {
        Fila fila = filaRepository.findById(filaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente na fila ", filaId));

                filaRepository.delete(fila);
    }

}
