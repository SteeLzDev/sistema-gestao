package com.oficina.infrastructure.persistence.repository;

import com.oficina.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    Optional<Permissao> findByNome(String nome);
    boolean existsByNome(String nome);

}
