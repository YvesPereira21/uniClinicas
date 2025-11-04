package com.projeto.uniClinicas.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "tb_endereco")
@ToString
@EqualsAndHashCode
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "endereco_id")
    private Long enderecoId;
    @Column(name = "logradouro")
    private String logradouro;
    @Column(name = "numero")
    private String numero;
    @Column(name = "complemento")
    private String complemento;
    @Column(name = "bairro")
    private String bairro;
    @ManyToOne()
    @JoinColumn(name = "municipio_cep")
    private Municipio municipio;
    @OneToOne(mappedBy = "endereco")
    private Clinica clinica;
    @OneToOne(mappedBy = "endereco")
    private UsuarioComum usuario;

    public Endereco() {}

    public Long getEnderecoId() {
        return enderecoId;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
}

