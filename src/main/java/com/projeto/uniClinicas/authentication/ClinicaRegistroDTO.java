package com.projeto.uniClinicas.authentication;

import com.projeto.uniClinicas.dto.ClinicaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClinicaRegistroDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    @Valid
    private ClinicaDTO clinica;

    public ClinicaRegistroDTO(String username, String password, ClinicaDTO clinica) {
        this.username = username;
        this.password = password;
        this.clinica = clinica;
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

    public ClinicaDTO getClinica() {
        return clinica;
    }

    public void setClinica(ClinicaDTO clinica) {
        this.clinica = clinica;
    }
}
