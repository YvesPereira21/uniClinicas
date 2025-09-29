package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.dto.HorarioDTO;
import com.projeto.uniClinicas.model.AgendaClinica;
import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Medico;
import com.projeto.uniClinicas.repository.AgendaClinicaRepository;
import com.projeto.uniClinicas.repository.ClinicaRepository;
import com.projeto.uniClinicas.repository.MedicoRepository;
import com.projeto.uniClinicas.exception.MedicoJaRemovido;
import com.projeto.uniClinicas.exception.ObjetoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgendaClinicaService {

    private final AgendaClinicaRepository agendaClinicaRepository;
    private final MedicoRepository medicoRepository;
    private final ClinicaRepository clinicaRepository;

    public AgendaClinicaService(AgendaClinicaRepository agendaClinicaRepository, MedicoRepository medicoRepository, ClinicaRepository clinicaRepository) {
        this.agendaClinicaRepository = agendaClinicaRepository;
        this.medicoRepository = medicoRepository;
        this.clinicaRepository = clinicaRepository;
    }

    public List<AgendaClinica> adicionaAgenda(Long medicoId, Long clinicaId, List<HorarioDTO> horarios) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Não há um médico com essas informações"));
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Não há uma clínica com essas informações"));

        List<AgendaClinica> novaAgenda = new ArrayList<>();
        for(HorarioDTO horario : horarios){
            AgendaClinica novoAtendimento = new AgendaClinica();
            novoAtendimento.setMedico(medico);
            novoAtendimento.setClinica(clinica);
            novoAtendimento.setDiaSemana(horario.getDiaSemana());
            novoAtendimento.setHorarioAtendimentoMedico(horario.getHorarioAtendimentoMedico());
            novoAtendimento.setHorarioSaida(horario.getHorarioSaida());
            novaAgenda.add(novoAtendimento);
        }

        return agendaClinicaRepository.saveAll(novaAgenda);
    }

    public void removeAgenda(Long agendaId){
        AgendaClinica agendaClinica = agendaClinicaRepository.findById(agendaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Agenda não encontrada!"));
        agendaClinicaRepository.deleteById(agendaId);
    }

    public void atualizaMedicoDaClinica(Long medicoAntigoId, Long medicoContratadoId, Long clinicaId, List<HorarioDTO> horarioDTOs) {;
        List<AgendaClinica> atualizaAtendimento = agendaClinicaRepository.findAgendaClinicaByMedicoIdAndClinicaId(medicoAntigoId, clinicaId);
        if (atualizaAtendimento.isEmpty()){
            throw new MedicoJaRemovido("O médico antigo já foi substituído nessa clínica!");
        } else{
            agendaClinicaRepository.deleteAll(atualizaAtendimento);
        }

        List<AgendaClinica> novaAgenda = adicionaAgenda(medicoContratadoId, clinicaId, horarioDTOs);
        agendaClinicaRepository.saveAll(novaAgenda);
    }

    public List<AgendaClinica> medicoTrabalhoClinica(Long medicoId, Long clinicaId) {
        return agendaClinicaRepository.findAgendaClinicaByMedicoIdAndClinicaId(medicoId, clinicaId);
    }

}
