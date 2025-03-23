package com.oficina.infrastructure.rest.dto;

public class AuthRequest {

    private String username;
    private String senha;

    public AuthRequest(String username, String senha) {
        this.username = username;
        this.senha = senha;
    }

    public AuthRequest() {
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
}
