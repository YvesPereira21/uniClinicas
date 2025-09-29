package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.model.Medico;
import com.projeto.uniClinicas.repository.MedicoRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public Medico adicionaMedico(Medico medico){
        return medicoRepository.save(medico);
    }

    public Medico atualizaMedico(Medico medico){
        return medicoRepository.save(medico);
    }

    public void deletaMedico(Long medicoId){
        medicoRepository.deleteById(medicoId);
    }

    public Medico medicoUnico(Long medicoId){
        return medicoRepository.findById(medicoId).
                orElseThrow(() -> new RuntimeException("Médico não encontrado!"));
    }

}

