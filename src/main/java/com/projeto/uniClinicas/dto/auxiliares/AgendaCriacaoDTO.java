package com.projeto.uniClinicas.dto.auxiliares;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AgendaCriacaoDTO {
    @NotNull
    private Long medicoId;
    @NotEmpty
    @Valid
    private List<HorarioDTO> horarios;

    public AgendaCriacaoDTO(Long medicoId, List<HorarioDTO> horarios) {
        this.medicoId = medicoId;
        this.horarios = horarios;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public List<HorarioDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioDTO> horarios) {
        this.horarios = horarios;
    }
}
