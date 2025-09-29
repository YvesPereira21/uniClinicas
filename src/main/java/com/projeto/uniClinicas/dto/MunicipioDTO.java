package com.projeto.uniClinicas.dto;

import com.projeto.uniClinicas.validation.CidadeParaiba;
import com.projeto.uniClinicas.validation.Estado;
import jakarta.validation.constraints.NotBlank;

public class MunicipioDTO {

    @NotBlank
    @CidadeParaiba
    private String nomeMunicipio;
    @NotBlank
    @Estado
    private String estado;

    public MunicipioDTO() {
    }

    public MunicipioDTO(String nomeMunicipio, String estado) {
        this.nomeMunicipio = nomeMunicipio;
        this.estado = estado;
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
