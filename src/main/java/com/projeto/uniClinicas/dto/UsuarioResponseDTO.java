package com.projeto.uniClinicas.dto;

public class UsuarioResponseDTO {

    private String nomeUsuario;
    private String cpf;
    private int idadeUsuario;
    private String sexo;
    private String email;
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
