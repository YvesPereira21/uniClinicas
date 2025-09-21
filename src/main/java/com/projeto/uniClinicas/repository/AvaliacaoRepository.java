package com.projeto.uniClinicas.repository;

import com.projeto.uniClinicas.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    @Query("SELECT a FROM Avaliacao a JOIN a.clinica c WHERE c.clinicaId = :clinicaId")
    List<Avaliacao> findAllAvaliacaoByClinicaId(@Param("clinicaId") Long clinicaId);
    @Query("SELECT a FROM Avaliacao a JOIN a.usuario u WHERE u.usuarioId = :usuarioId")
    List<Avaliacao> findAllAvaliacaoByUsuarioId(@Param("usuarioId") Long usuarioId);
}
