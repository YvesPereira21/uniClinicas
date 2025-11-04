package com.projeto.uniClinicas.dto.requests;

import com.projeto.uniClinicas.dto.EnderecoDTO;
import com.projeto.uniClinicas.dto.UsuarioDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public class UsuarioComumRequestDTO {

    @NotBlank
    private String nome;
    @NotBlank
    @CPF
    private String cpf;
    @NotNull
    private int idadeUsuario;
    @NotBlank
    private String sexo;
    @NotBlank
    @Email
    private String email;
    @NotNull
    @Valid
    private UsuarioDTO usuario;
    @NotNull
    @Valid
    private EnderecoDTO endereco;

    public UsuarioComumRequestDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public UsuarioDTO getUsuario() {
        return this.usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public EnderecoDTO getEndereco() {
        return this.endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}

