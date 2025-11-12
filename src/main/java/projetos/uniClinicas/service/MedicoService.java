package projetos.uniClinicas.service;

import projetos.uniClinicas.enums.UserRole;
import projetos.uniClinicas.exception.*;
import projetos.uniClinicas.model.Clinica;
import projetos.uniClinicas.model.Medico;
import projetos.uniClinicas.model.Usuario;
import projetos.uniClinicas.repository.AgendaClinicaRepository;
import projetos.uniClinicas.repository.ClinicaRepository;
import projetos.uniClinicas.repository.MedicoRepository;
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

