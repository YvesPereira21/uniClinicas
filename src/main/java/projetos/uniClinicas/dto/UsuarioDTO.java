package com.projeto.uniClinicas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class UsuarioDTO {

    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    private String password;

    public UsuarioDTO() {}

    public UsuarioDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
