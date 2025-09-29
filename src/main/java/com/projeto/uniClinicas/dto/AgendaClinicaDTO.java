package com.projeto.uniClinicas.dto;

import com.projeto.uniClinicas.enums.DiaDaSemana;
import com.projeto.uniClinicas.validation.DiaSemana;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public class AgendaClinicaDTO {
    @DiaSemana
    private DiaDaSemana diaSemana;
    @NotNull
    private LocalTime horarioAtendimentoMedico;
    @NotNull
    private LocalTime horarioSaida;

    @NotNull
    private MedicoDTO medico;
    @NotNull
    private ClinicaDTO clinica;

    public AgendaClinicaDTO() {
    }

    public AgendaClinicaDTO(MedicoDTO medico, ClinicaDTO clinica) {
        this.medico = medico;
        this.clinica = clinica;
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