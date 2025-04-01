package com.oficina.infrastructure.persistence.repository;

import com.oficina.domain.model.FilaAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FilaAtendimentoRepository extends JpaRepository<FilaAtendimento, Long> {

    List<FilaAtendimento> findByStatus(String status);
    long countByStatus(String status);


}
