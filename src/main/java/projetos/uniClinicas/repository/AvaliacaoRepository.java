package projetos.uniClinicas.repository;

import projetos.uniClinicas.model.Avaliacao;
import projetos.uniClinicas.model.UsuarioComum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    @Query("SELECT a FROM Avaliacao a JOIN a.clinica c WHERE c.clinicaId = :clinicaId")
    Page<Avaliacao> findAllAvaliacaoByClinicaId(@Param("clinicaId") Long clinicaId, Pageable pageable);
    @Query("SELECT a FROM Avaliacao a JOIN a.clinica c WHERE c.clinicaId = :clinicaId")
    List<Avaliacao> findAllAvaliacaoByClinicaId(@Param("clinicaId") Long clinicaId);
    @Query("SELECT a FROM Avaliacao a JOIN a.usuarioComum u WHERE u.usuarioComumId = :usuarioId")
    Page<Avaliacao> findAllAvaliacaoByUsuarioId(@Param("usuarioId") Long usuarioId, Pageable pageable);
    boolean existsByUsuarioComumAndClinica_ClinicaId(UsuarioComum usuarioComum, Long clinicaId);
}