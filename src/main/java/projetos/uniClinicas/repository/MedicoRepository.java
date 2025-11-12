package projetos.uniClinicas.repository;

import projetos.uniClinicas.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedicoRepository extends JpaRepository<Medico,Long> {
    boolean existsByCrmMedico(String crmMedico);
    Medico findByCrmMedico(String crmMedico);
}
