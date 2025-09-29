package com.projeto.uniClinicas.dto;

import com.projeto.uniClinicas.enums.DiaDaSemana;
import com.projeto.uniClinicas.validation.DiaSemana;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class HorarioDTO {

    @NotNull
    @DiaSemana
    private DiaDaSemana diaSemana;
    @NotNull
    private LocalTime horarioAtendimentoMedico;
    @NotNull
    private LocalTime horarioSaida;

    public HorarioDTO() {
    }

    public HorarioDTO(DiaDaSemana diaSemana, LocalTime horarioAtendimentoMedico, LocalTime horarioSaida) {
        this.diaSemana = diaSemana;
        this.horarioAtendimentoMedico = horarioAtendimentoMedico;
        this.horarioSaida = horarioSaida;
    }

    public DiaDaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaDaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHorarioAtendimentoMedico() {
        return horarioAtendimentoMedico;
    }

    public void setHorarioAtendimentoMedico(LocalTime horarioAtendimentoMedico) {
        this.horarioAtendimentoMedico = horarioAtendimentoMedico;
    }

    public LocalTime getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(LocalTime horarioSaida) {
        this.horarioSaida = horarioSaida;
    }
}

