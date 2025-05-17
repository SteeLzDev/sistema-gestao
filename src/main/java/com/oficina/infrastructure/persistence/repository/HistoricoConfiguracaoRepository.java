package com.oficina.infrastructure.persistence.repository;

import com.oficina.domain.model.HistoricoConfiguracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoricoConfiguracaoRepository extends JpaRepository <HistoricoConfiguracao, Long> {

}
