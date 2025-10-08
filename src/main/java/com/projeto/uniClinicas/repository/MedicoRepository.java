package com.projeto.uniClinicas.repository;

import com.projeto.uniClinicas.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedicoRepository extends JpaRepository<Medico,Long> {
    boolean existsByCrmMedico(String crmMedico);
    Medico findByCrmMedico(String crmMedico);
}
