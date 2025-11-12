package projetos.uniClinicas.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalTime;
@Entity
@Table(name = "tb_agenda_clinica")
@ToString
@EqualsAndHashCode
public class AgendaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agenda_id")
    private Long agendaId;

    @Column(name = "dia_semana")
    private String diaSemana;

    @Column(name = "horario_atendimento_medico")
    private LocalTime horarioAtendimentoMedico;

    @Column(name = "horario_saida")
    private LocalTime horarioSaida;

    @ManyToOne()
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;

    public AgendaClinica() {
    }

    public Long getAgendaId() {
        return agendaId;
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

    public Medico getMedico() {
        return this.medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Clinica getClinica() {
        return this.clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }
}

