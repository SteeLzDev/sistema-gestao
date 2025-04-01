package com.oficina.infrastructure.persistence.repository;

import com.oficina.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigo (String codigo);
    List<Produto> findByCategoria (String categoria);
    List<Produto> findByQuantidadeLessThan (Integer quantidade);
    List<Produto> findByQuantidadeLessThanEqual(int limite);
    long countByQuantidadeLessThanEqual(int limite);





}
