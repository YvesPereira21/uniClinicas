package com.projeto.uniClinicas.repository;

import com.projeto.uniClinicas.model.AgendaClinica;
import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;

public interface AgendaClinicaRepository extends JpaRepository<AgendaClinica, Long> {
    @Query("SELECT a FROM AgendaClinica a JOIN a.medico med " +
            "JOIN a.clinica c WHERE med.medicoId = :medicoId AND c.clinicaId = :clinicaId")
    List<AgendaClinica> findAgendaClinicaByMedicoIdAndClinicaId(Long medicoId, Long clinicaId);
    @Query("SELECT a FROM AgendaClinica a JOIN a.clinica c WHERE c.clinicaId = :clinicaId")
    List<AgendaClinica> findAllAgendaClinicaByClinicaId(Long clinicaId);
    boolean existsByMedico_CrmMedicoAndClinicaAndDiaSemanaAndHorarioAtendimentoMedico(
            String crmMedico, Clinica clinica, String diaSemana, LocalTime horarioAtendimentoMedico
    );
    @Query("SELECT a FROM AgendaClinica a JOIN a.medico m " +
            "WHERE a.medico = :medico AND a.clinica = :clinica AND a.diaSemana = :diaSemana")
    List<AgendaClinica> findAllByMedicoAndClinicaAndDiaSemana(
            Medico medico, Clinica clinica, String diaSemana
    );
    List<AgendaClinica> findByMedico_CrmMedicoAndDiaSemana(String crmMedico, String diaSemana);
}
