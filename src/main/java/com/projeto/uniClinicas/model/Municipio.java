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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long municipioId;

    @Column(name = "nome_municipio", unique = true)
    private String nomeMunicipio;

    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "municipio")
    private List<Endereco> enderecos;

    public Municipio(){}

    public Long getMunicipioId() {
        return municipioId;
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

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}

