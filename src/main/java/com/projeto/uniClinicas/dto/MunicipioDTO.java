package com.projeto.uniClinicas.dto;

import jakarta.validation.constraints.NotBlank;

public class MunicipioDTO {

    @NotBlank
    private String nomeMunicipio;

    public MunicipioDTO(String nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
    }

    public String getNomeMunicipio() {
        return nomeMunicipio;
    }

    public void setNomeMunicipio(String nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
    }
}
