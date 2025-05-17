package com.oficina.infrastructure.persistence.repository;

import com.oficina.domain.model.ConfiguracaoBancoDados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ConfiguracaoBancoDadosRepository extends JpaRepository<ConfiguracaoBancoDados, Long> {

    @Query("SELECT c FROM ConfiguracaoBancoDados c WHERE c.ativo = true ORDER BY c.id DESC")
    Optional<ConfiguracaoBancoDados> findAtiva();
}
