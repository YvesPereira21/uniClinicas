package com.projeto.uniClinicas.model;

import com.projeto.uniClinicas.enums.CidadesParaiba;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "nome_municipio", unique = true)
    private CidadesParaiba nomeMunicipio;

    @OneToMany(mappedBy = "municipio")
    private List<Endereco> enderecos;

    public Municipio(){}

    public Long getMunicipioId() {
        return municipioId;
    }

    public CidadesParaiba getNomeMunicipio() {
        return nomeMunicipio;
    }

    public void setNomeMunicipio(CidadesParaiba nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}

