package com.projeto.uniClinicas.model;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "tb_clinica")
public class Clinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clinica_id")
    private Long clinicaId;
    @Column(name = "cnpj_clinica", unique = true)
    private Long cpnj_clinica;
    @Column(name = "nome_clinica")
    private String nomeClinica;
    @Column(name = "telefone", length = 12)
    private int telefone;
    @Column(name = "horario_funcionamento")
    private LocalTime horarioFuncionamento;
    @Column(name = "horario_fechamento")
    private LocalTime horarioFechamento;
    @OneToOne()
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    @OneToMany(mappedBy = "clinica")
    private List<Avaliacao> avaliacoesDaClinica;
    @OneToMany(mappedBy = "clinica")
    private List<AgendaClinica> agendaClinicas;


    public Clinica(){}


    public Long getClinicaId() {
        return this.clinicaId;
    }

    public Long getCpnj_clinica() {
        return this.cpnj_clinica;
    }

    public void setCpnj_clinica(Long cpnj_clinica) {
        this.cpnj_clinica = cpnj_clinica;
    }

    public String getNomeClinica() {
        return this.nomeClinica;
    }

    public void setNomeClinica(String nomeClinica) {
        this.nomeClinica = nomeClinica;
    }

    public int getTelefone() {
        return this.telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public LocalTime getHorarioFuncionamento() {
        return this.horarioFuncionamento;
    }

    public void setHorarioFuncionamento(LocalTime horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public LocalTime getHorarioFechamento() {
        return this.horarioFechamento;
    }

    public void setHorarioFechamento(LocalTime horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Avaliacao> getAvaliacoesDaClinica() {
        return avaliacoesDaClinica;
    }

    public void setAvaliacoesDaClinica(List<Avaliacao> avaliacoesDaClinica) {
        this.avaliacoesDaClinica = avaliacoesDaClinica;
    }

    public List<AgendaClinica> getDiasAtendimentoMedicos() {
        return this.agendaClinicas;
    }

    public void setDiasAtendimentoMedicos(List<AgendaClinica> agendaClinicas) {
        this.agendaClinicas = agendaClinicas;
    }
}

