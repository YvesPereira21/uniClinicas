package com.projeto.uniClinicas.repository;

import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
    Endereco findEnderecoByNomeMunicipio(String nomeMunicipio);
    @Query("SELECT c FROM Clinica c JOIN c.endereco e " +
            "WHERE e.nomeMunicipio = :nomeMunicipio")
    List<Clinica> todasClinicasCidade(@Param("nomeMunicipio") String nomeMunicipio);
}
