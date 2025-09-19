package com.projeto.uniClinicas.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class ClinicaDTO {

    @NotBlank
    private String nomeClinica;
    @NotNull
    private int telefone;
    @NotNull
    private LocalTime horarioFuncionamento;
    @NotNull
    private LocalTime horarioFechamento;
    @NotNull
    @Valid
    private EnderecoDTO endereco;

    public ClinicaDTO() {
    }

    public ClinicaDTO(String nomeClinica, int telefone, LocalTime horarioFuncionamento, LocalTime horarioFechamento, EnderecoDTO endereco) {
        this.nomeClinica = nomeClinica;
        this.telefone = telefone;
        this.horarioFuncionamento = horarioFuncionamento;
        this.horarioFechamento = horarioFechamento;
        this.endereco = endereco;
    }

    public String getNomeClinica() {
        return nomeClinica;
    }

    public void setNomeClinica(String nomeClinica) {
        this.nomeClinica = nomeClinica;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public LocalTime getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(LocalTime horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public LocalTime getHorarioFechamento() {
        return horarioFechamento;
    }

    public void setHorarioFechamento(LocalTime horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }

    public EnderecoDTO getEndereco() {
        return this.endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}

