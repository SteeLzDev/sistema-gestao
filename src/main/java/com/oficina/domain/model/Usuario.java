package com.oficina.domain.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column (nullable = false)
    private String senha;

    @Column(nullable = false)
    private String perfil;

    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false)
    private String status = "ATIVO";

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_permissoes",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )

    private List<Permissao> permissoes = new ArrayList<>();

    public Usuario() {}

    public Usuario(Long id, String nome, String email, String perfil, String cargo, String username, String senha, String status) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
        this.cargo = cargo;
        this.username = username;
        this.senha = senha;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes != null ? new ArrayList<>(permissoes) : new ArrayList<>();
    }

    public void adicionarPermissao(Permissao permissao) {
        if (permissao != null && !this.permissoes.contains(permissao)) {
            this.permissoes.add(permissao);
        }
    }

    public void removerPermissao (Permissao permissao) {
        this.permissoes.remove(permissao);
    }

    public boolean temPermissao (String nomePermissao) {
        return this.permissoes.stream()
                .anyMatch(p -> p.getNome().equals(nomePermissao));
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", cargo='" + cargo + '\'' +
                ", perfil='" + perfil + '\'' +
                ", status='" + status + '\'' +
                ", permissoes=" + permissoes +
                '}';
    }
}
