package com.projeto.uniClinicas.dto.responses;

import com.projeto.uniClinicas.dto.EnderecoDTO;

import java.time.LocalTime;

public class ClinicaResponseDTO {

    private String cpnjClinica;
    private String nomeClinica;
    private int telefone;
    private LocalTime horarioFuncionamento;
    private LocalTime horarioFechamento;
    private EnderecoDTO endereco;

    public ClinicaResponseDTO() {
    }

    public String getCpnjClinica() {
        return cpnjClinica;
    }

    public void setCpnjClinica(String cpnjClinica) {
        this.cpnjClinica = cpnjClinica;
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
