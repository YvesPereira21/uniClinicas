package com.projeto.uniClinicas.dto.auxiliares;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AtualizacaoAgendaDTO {
    @NotNull
    private Long medicoAntigoId;
    @NotNull
    private Long medicoContratadoId;
    @NotEmpty
    @Valid
    private List<HorarioDTO> horarios;

    public AtualizacaoAgendaDTO() {
    }

    public Long getMedicoAntigoId() {
        return medicoAntigoId;
    }

    public void setMedicoAntigoId(Long medicoAntigoId) {
        this.medicoAntigoId = medicoAntigoId;
    }

    public Long getMedicoContratadoId() {
        return medicoContratadoId;
    }

    public void setMedicoContratadoId(Long medicoContratadoId) {
        this.medicoContratadoId = medicoContratadoId;
    }

    public List<HorarioDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioDTO> horarios) {
        this.horarios = horarios;
    }
}
