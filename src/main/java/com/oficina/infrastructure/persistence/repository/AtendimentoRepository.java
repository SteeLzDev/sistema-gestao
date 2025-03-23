package com.oficina.infrastructure.persistence.repository;

import com.oficina.domain.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository <Atendimento, Long> {

    List<Atendimento>findByStatus(String status);
}
