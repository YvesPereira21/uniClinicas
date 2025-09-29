package com.projeto.uniClinicas.repository;

import com.projeto.uniClinicas.model.AgendaClinica;
import com.projeto.uniClinicas.model.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgendaClinicaRepository extends JpaRepository<AgendaClinica, Long> {
    @Query("SELECT a FROM AgendaClinica a JOIN a.medico med " +
            "JOIN a.clinica c WHERE med.medicoId = :medicoId AND c.clinicaId = :clinicaId")
    List<AgendaClinica> findAgendaClinicaByMedicoIdAndClinicaId(Long medicoId, Long clinicaId);
    @Query("SELECT a FROM AgendaClinica a JOIN a.clinica c WHERE c.clinicaId = :clinicaId")
    List<Clinica> findAllAgendaClinicaByClinicaId(Long clinicaId);
    List<AgendaClinica> findAgendaClinicaByDiaSemana(DiaDaSemana diaSemana);
}
