package com.oficina.infrastructure.persistence.repository;

import com.oficina.domain.model.Fila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilaRepository extends JpaRepository<Fila, Long> {

    List<Fila> findByStatus (String status);
    List<Fila> findByStatusOrderByPrioridadeDescChegadaAsc(String status);

}
