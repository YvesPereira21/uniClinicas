package com.projeto.uniClinicas.authentication;

public class RegistroDTO {

    private String username;
    private String password;

    public RegistroDTO(String username, String password) {
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

