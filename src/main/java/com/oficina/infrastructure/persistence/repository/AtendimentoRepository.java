package com.oficina.infrastructure.persistence.repository;

import com.oficina.domain.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository <Atendimento, Long> {

    List<Atendimento>findByStatus(String status);

    List<Atendimento> findByStatusAndInicioBetween(String status, LocalDateTime dataInicio, LocalDateTime dataFim);

    long countByStatus(String status);

}
