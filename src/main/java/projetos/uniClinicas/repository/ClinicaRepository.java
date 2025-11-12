package projetos.uniClinicas.repository;

import projetos.uniClinicas.model.Clinica;
import projetos.uniClinicas.model.Endereco;
import projetos.uniClinicas.model.Medico;
import projetos.uniClinicas.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
    boolean existsByCpnjClinica(@Param("cnpj") String cnpj);
    boolean existsByTelefone(@Param("telefone") int telefone);
    Page<Clinica> findByNomeClinicaContaining(String nome, Pageable pageable);
    @Query("SELECT m FROM Medico m JOIN m.agendaClinicas a JOIN a.clinica c WHERE c.clinicaId = :clinicaId")
    Page<Medico> findMedicosByClinicaId(@Param("clinicaId") Long clinicaId, Pageable pageable);
    @Query("SELECT m FROM Medico m JOIN m.agendaClinicas a JOIN a.clinica c " +
            "WHERE m.medicoId = :medicoId AND c.clinicaId = :clinicaId")
    Medico findMedicoInClinicaByMedicoId(@Param("medicoId") Long medicoId,@Param("clinicaId") Long clinicaId);
    Clinica findByUsuario(Usuario usuario);
}