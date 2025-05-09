package com.oficina.infrastructure.rest.dto;

import com.oficina.domain.model.Usuario;
import org.apache.catalina.User;

import java.util.List;

public class AuthResponse {


    private String token;
    private UserDTO user;

    public AuthResponse(String token, Usuario usuario) {
        this.token = token;
        this.user = new UserDTO(usuario);
    }

    public AuthResponse(String token, Usuario usuario, List<String> permissoes) {
        this.token = token;
        this.user = new UserDTO(usuario, permissoes);

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }


    public static class UserDTO{
        private long id;
        private String nome;
        private String username;
        private String email;
        private String cargo;
        private String perfil;
        private String status;
        private List<String> permissoes;

        public UserDTO(Usuario usuario) {
            this.id = usuario.getId();
            this.nome = usuario.getNome();
            this.username = usuario.getUsername();
            this.email = usuario.getEmail();
            this.cargo = usuario.getCargo();
            this.perfil = usuario.getPerfil();
            this.status = usuario.getStatus();
        }

        public UserDTO(Usuario usuario, List<String> permissoes) {
            this(usuario);
            this.permissoes = permissoes;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCargo() {
            return cargo;
        }

        public void setCargo(String cargo) {
            this.cargo = cargo;
        }

        public String getPerfil() {
            return perfil;
        }

        public void setPerfil(String perfil) {
            this.perfil = perfil;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<String> getPermissoes() {
            return permissoes;
        }

        public void setPermissoes(List<String> permissoes) {
            this.permissoes = permissoes;
        }
    }


}
