package com.oficina.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private long id;

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

    public Usuario() {}

    public Usuario(long id, String nome, String email, String perfil, String cargo, String username, String senha, String status) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
        this.cargo = cargo;
        this.username = username;
        this.senha = senha;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
                '}';
    }
}
