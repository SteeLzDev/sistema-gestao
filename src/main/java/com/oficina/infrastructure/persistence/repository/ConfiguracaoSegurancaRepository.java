package com.oficina.infrastructure.persistence.repository;

import com.oficina.domain.model.ConfiguracaoSeguranca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracaoSegurancaRepository extends JpaRepository <ConfiguracaoSeguranca, Long> {


    @Query("SELECT c FROM ConfiguracaoNotificacao c WHERE c.ativo = true ORDER BY c.id DESC")
    Optional<ConfiguracaoSeguranca> findAtiva();




}
