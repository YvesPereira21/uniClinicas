package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.exception.ObjetoJaAdicionado;
import com.projeto.uniClinicas.model.Medico;
import com.projeto.uniClinicas.repository.MedicoRepository;
import com.projeto.uniClinicas.exception.ObjetoNaoEncontradoException;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public Medico adicionaMedico(Medico medico){
        boolean medicoExistente = medicoRepository.existsMedicoByCrmMedico(medico.getCrmMedico());
        if(medicoExistente){
            throw new ObjetoJaAdicionado("Esse médico já foi adicionado!");
        }
        return medicoRepository.save(medico);
    }

    public Medico atualizaMedico(Medico dadosAtualizados, Long medicoId){
        Medico medicoExistente = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Médico não encontrado!"));

        medicoExistente.setNomeMedico(dadosAtualizados.getNomeMedico());
        medicoExistente.setEspecialidade(dadosAtualizados.getEspecialidade());

        return medicoRepository.save(medicoExistente);
    }

    public void deletaMedico(Long medicoId){
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Médico não encontrado!"));
        medicoRepository.deleteById(medicoId);
    }

    public Medico medicoUnico(Long medicoId){
        return medicoRepository.findById(medicoId).
                orElseThrow(() -> new ObjetoNaoEncontradoException("Médico não encontrado!"));
    }

}

