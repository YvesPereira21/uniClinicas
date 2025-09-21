package com.projeto.uniClinicas.authentication;

public class AutenticacaoDTO {

    private String username;
    private String password;

    public AutenticacaoDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
