package com.projeto.uniClinicas.repository;

import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Endereco;
import com.projeto.uniClinicas.model.Medico;
import com.projeto.uniClinicas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
    boolean existsByCpnjClinica(@Param("cnpj") String cnpj);
    boolean existsByTelefone(@Param("telefone") int telefone);
    List<Clinica> findByNomeClinicaContaining(String nome);
    List<Clinica> findByHorarioFuncionamento(LocalTime hora);
    Clinica findClinicaByEndereco(Endereco endereco);
    @Query("SELECT m FROM Medico m JOIN m.agendaClinicas a JOIN a.clinica c WHERE c.clinicaId = :clinicaId")
    List<Medico> findMedicosByClinicaId(@Param("clinicaId") Long clinicaId);
    @Query("SELECT m FROM Medico m JOIN m.agendaClinicas a JOIN a.clinica c " +
            "WHERE m.medicoId = :medicoId AND c.clinicaId = :clinicaId")
    Medico findMedicoInClinicaByMedicoId(@Param("medicoId") Long medicoId,@Param("clinicaId") Long clinicaId);
    Clinica findByUsuario(Usuario usuario);
}
