package com.projeto.uniClinicas.dto;

import jakarta.validation.constraints.NotBlank;

public class MunicipioDTO {

    @NotBlank
    private String cep;
    @NotBlank
    private String nomeMunicipio;

    public MunicipioDTO() {
    }

    public MunicipioDTO(String cep, String nomeMunicipio) {
        this.cep = cep;
        this.nomeMunicipio = nomeMunicipio;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNomeMunicipio() {
        return nomeMunicipio;
    }

    public void setNomeMunicipio(String nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
    }
}
