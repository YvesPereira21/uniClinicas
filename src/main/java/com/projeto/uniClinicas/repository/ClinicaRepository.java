package com.projeto.uniClinicas.repository;

import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Endereco;
import com.projeto.uniClinicas.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
    List<Clinica> findByNomeClinicaContaining(@Param("nome") String nome);
    Clinica findClinicaByEndereco(@Param("endereco") Endereco endereco);
}
