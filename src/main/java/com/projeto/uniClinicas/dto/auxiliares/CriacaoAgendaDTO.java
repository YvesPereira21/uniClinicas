package com.projeto.uniClinicas.dto.auxiliares;

import com.projeto.uniClinicas.dto.MedicoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CriacaoAgendaDTO {

    @NotNull
    @Valid
    private MedicoDTO medico;
    @NotNull
    @Valid
    private List<HorarioDTO> horarios;

    public CriacaoAgendaDTO() {
    }

    public MedicoDTO getMedico() {
        return medico;
    }

    public void setMedico(MedicoDTO medico) {
        this.medico = medico;
    }

    public List<HorarioDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioDTO> horarios) {
        this.horarios = horarios;
    }
}
