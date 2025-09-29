package com.projeto.uniClinicas.repository;

import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
    @Query("SELECT c FROM Clinica c JOIN c.endereco e " +
            "JOIN e.municipio m WHERE m.nomeMunicipio = :nomeMunicipio")
    List<Clinica> todasClinicasCidade(@Param("nomeMunicipio") String nomeMunicipio);
}
