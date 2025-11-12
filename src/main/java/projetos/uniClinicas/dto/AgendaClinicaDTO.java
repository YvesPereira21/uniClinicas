package projetos.uniClinicas.dto;

import projetos.uniClinicas.dto.requests.ClinicaRequestDTO;
import projetos.uniClinicas.validation.DiaSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public class AgendaClinicaDTO {

    @NotBlank
    @DiaSemana
    private String diaSemana;
    private LocalTime horarioAtendimentoMedico;
    private LocalTime horarioSaida;
    @NotNull
    private MedicoDTO medico;
    @NotNull
    private ClinicaRequestDTO clinica;

    public AgendaClinicaDTO() {
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
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

    public ClinicaRequestDTO getClinica() {
        return clinica;
    }

    public void setClinica(ClinicaRequestDTO clinica) {
        this.clinica = clinica;
    }
}