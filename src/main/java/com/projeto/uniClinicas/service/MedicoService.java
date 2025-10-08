package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.enums.UserRole;
import com.projeto.uniClinicas.exception.*;
import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Medico;
import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.repository.AgendaClinicaRepository;
import com.projeto.uniClinicas.repository.ClinicaRepository;
import com.projeto.uniClinicas.repository.MedicoRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    private MedicoRepository medicoRepository;
    private ClinicaRepository clinicaRepository;
    private AgendaClinicaRepository agendaClinicaRepository;

    public MedicoService(MedicoRepository medicoRepository, ClinicaRepository clinicaRepository, AgendaClinicaRepository agendaClinicaRepository) {
        this.medicoRepository = medicoRepository;
        this.clinicaRepository = clinicaRepository;
        this.agendaClinicaRepository = agendaClinicaRepository;
    }

    public Medico atualizaMedico(Medico medico){
        Medico medico1 = medicoRepository.findByCrmMedico(medico.getCrmMedico());
        if (medico1 == null){
            throw new ObjetoNaoEncontradoException("Médico não encontrado!");
        }
        medico1.setNomeMedico(medico.getNomeMedico());
        medico1.setEspecialidade(medico.getEspecialidade());
        return medicoRepository.save(medico1);
    }

    public Medico medicoUnico(Long medicoId, Usuario usuarioLogado) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Médico não encontrado!"));

        if (usuarioLogado.getRole() == UserRole.CLINICA) {
            Medico verifica = verificaExistenciaMedico(usuarioLogado, medicoId);
            if (verifica == null) {
                throw new ObjetoNaoEncontradoException("Não há um médico cadastrado nessa clínica com essa informação!");
            }
            return verifica;
        }
        return medico;
    }

    public Medico verificaExistenciaMedico(Usuario usuarioLogado, Long medicoId){
        Clinica clinicaLogada = clinicaRepository.findByUsuario(usuarioLogado);
        return clinicaRepository.findMedicoInClinicaByMedicoId(medicoId, clinicaLogada.getClinicaId());
    }
}

