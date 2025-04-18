package com.oficina.infrastructure.persistence.repository;


import com.oficina.domain.model.Permissao;
import com.oficina.domain.model.Usuario;
import com.oficina.domain.model.UsuarioPermissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioPermissaoRepository extends JpaRepository <UsuarioPermissao, Long>{

    List<UsuarioPermissao> findByUsuario(Usuario usuario);
    List<UsuarioPermissao> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioAndPermissao(Usuario usuario, Permissao permissao);
    void deleteByUsuarioAndPermissao(Usuario usuario, Permissao permissao);
    void deleteByUsuarioId(Long usuarioId);


}
