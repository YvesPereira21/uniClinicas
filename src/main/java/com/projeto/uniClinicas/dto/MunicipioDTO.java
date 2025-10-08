package com.projeto.uniClinicas.dto;

import com.projeto.uniClinicas.validation.Estado;
import com.projeto.uniClinicas.validation.MunicipiosParaiba;
import jakarta.validation.constraints.NotBlank;

public class MunicipioDTO {

    private String cep;
    @NotBlank
    @MunicipiosParaiba
    private String nomeMunicipio;
    @Estado
    private String estado;

    public MunicipioDTO() {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
