package com.projeto.uniClinicas.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_medico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medico_id")
    private Long medicoId;
    @Column(name = "crm_medico", unique = true, nullable = false)
    private String crmMedico;
    @Column(name = "nome_medico")
    private String nomeMedico;
    @Column(name = "especialidade")
    private String especialidade;
    @OneToMany(mappedBy = "medico")
    private List<AgendaClinica> agendaClinicas;

    public Medico() {}

    public Long getMedicoId() {
        return medicoId;
    }

    public String getCrmMedico() {
        return crmMedico;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public List<AgendaClinica> getDiasAtendimentoMedicos() {
        return agendaClinicas;
    }

    public void setDiasAtendimentoMedicos(List<AgendaClinica> agendaClinicas) {
        this.agendaClinicas = agendaClinicas;
    }

}

