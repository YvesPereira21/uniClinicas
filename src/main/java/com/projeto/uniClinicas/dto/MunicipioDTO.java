package com.projeto.uniClinicas.dto;

import com.projeto.uniClinicas.enums.CidadesParaiba;
import com.projeto.uniClinicas.validation.CidadeParaiba;
import jakarta.validation.constraints.NotBlank;

public class MunicipioDTO {

    @NotBlank
    @CidadeParaiba
    private CidadesParaiba nomeMunicipio;

    public MunicipioDTO() {
    }

    public MunicipioDTO(String cep, CidadesParaiba nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
    }

    public CidadesParaiba getNomeMunicipio() {
        return nomeMunicipio;
    }

    public void setNomeMunicipio(CidadesParaiba nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
    }
}
