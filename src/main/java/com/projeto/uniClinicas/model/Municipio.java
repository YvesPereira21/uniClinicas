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
    @Column(name = "nome_municipio")
    private String nomeMunicipio;
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

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}

