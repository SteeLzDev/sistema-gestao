package com.oficina.infrastructure.persistence.repository;

import com.oficina.domain.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository <Venda, Long> {
    List<Venda> findByClienteContainingIgnoreCase(String cliente);
    List<Venda> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

}
