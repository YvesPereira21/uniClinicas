package com.projeto.uniClinicas.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public class UsuarioResponseDTO {

    @NotBlank
    private String nomeUsuario;
    @NotBlank
    @CPF
    private String cpf;
    @Max(125)
    private int idadeUsuario;
    @NotBlank
    private String sexo;
    @NotBlank
    @Email
    private String email;
    @NotNull
    @Valid
    private EnderecoDTO endereco;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(String nomeUsuario, String cpf, int idadeUsuario, String sexo, EnderecoDTO endereco, String email) {
        this.nomeUsuario = nomeUsuario;
        this.cpf = cpf;
        this.idadeUsuario = idadeUsuario;
        this.sexo = sexo;
        this.endereco = endereco;
        this.email = email;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdadeUsuario() {
        return idadeUsuario;
    }

    public void setIdadeUsuario(int idadeUsuario) {
        this.idadeUsuario = idadeUsuario;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnderecoDTO getEndereco() {
        return this.endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}
