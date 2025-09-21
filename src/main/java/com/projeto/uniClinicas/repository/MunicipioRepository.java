package com.projeto.uniClinicas.repository;

import com.projeto.uniClinicas.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
    Municipio findMunicipioByCep(String cep);
    Municipio findMunicipioByNomeMunicipioContaining(String nome);
}
