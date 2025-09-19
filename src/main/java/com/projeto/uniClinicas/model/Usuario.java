package com.projeto.uniClinicas.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;
    @Column(name = "nome_usuario")
    private String nomeusuario;
    @Column(name = "cpf_usuario", unique = true)
    private String cpf;
    @Column(name = "idade_usuario")
    private int idadeUsuario;
    @Column(name = "sexo_usuario")
    private String sexo;
    @Column(name = "email")
    private String email;
    @Column(name = "user_name", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    @OneToMany(mappedBy = "usuario")
    private List<Avaliacao> avaliacoesUsuario;

    public Usuario() {}

    public Long getusuarioId() {
        return usuarioId;
    }

    public String getNomeusuario() {
        return nomeusuario;
    }

    public void setNomeusuario(String nomeusuario) {
        this.nomeusuario = nomeusuario;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Avaliacao> getAvaliacoesUsuario() {
        return avaliacoesUsuario;
    }

    public void setAvaliacoesUsuario(List<Avaliacao> avaliacoesUsuario) {
        this.avaliacoesUsuario = avaliacoesUsuario;
    }
}

