package com.oficina.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios_permissoes")
public class UsuarioPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "permissao_id", nullable = false)
    private Permissao permissao;

    public UsuarioPermissao(){
    }

    public UsuarioPermissao(Usuario usuario, Permissao permissao){
        this.usuario = usuario;
        this.permissao = permissao;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    @Override
    public String toString() {
        return "UsuarioPermissao{" +
                "id=" + id +
                ", usuario=" + usuario.getId() +
                ", permissao=" + permissao.getNome() +
                '}';
    }
}
