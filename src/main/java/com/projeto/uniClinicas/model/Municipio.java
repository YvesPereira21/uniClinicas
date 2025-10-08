package com.projeto.uniClinicas.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "tb_municipio")
@ToString
@EqualsAndHashCode
public class Municipio {

    @Id
    @Column(name = "municipio_cep", unique = true)
    private String cep;
    @Column(name = "nome_municipio", unique = true)
    private String nomeMunicipio;
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "municipio")
    private List<Endereco> enderecos;

    public Municipio(){}

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNomeMunicipio() {
        return nomeMunicipio;
    }

    public void setNomeMunicipio(String nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}