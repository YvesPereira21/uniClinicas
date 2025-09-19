package com.projeto.uniClinicas.dto;

import jakarta.validation.constraints.NotNull;


public class AgendaClinicaDTO {

    @NotNull
    private MedicoDTO medico;
    @NotNull
    private ClinicaDTO clinica;

    public AgendaClinicaDTO(MedicoDTO medico, ClinicaDTO clinica) {
        this.medico = medico;
        this.clinica = clinica;
    }

    public MedicoDTO getMedico() {
        return medico;
    }

    public void setMedico(MedicoDTO medico) {
        this.medico = medico;
    }

    public ClinicaDTO getClinica() {
        return clinica;
    }

    public void setClinica(ClinicaDTO clinica) {
        this.clinica = clinica;
    }

}

