package projetos.uniClinicas.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    public MedicoService(MedicoRepository medicoRepository, ClinicaRepository clinicaRepository) {
        this.medicoRepository = medicoRepository;
        this.clinicaRepository = clinicaRepository;
    }

    @CachePut(value = "medico", key = "#medico.medicoId")
    public Medico atualizaMedico(Medico medico){
        Medico medico1 = medicoRepository.findByCrmMedico(medico.getCrmMedico());
        if (medico1 == null){
            throw new ObjetoNaoEncontradoException("Médico não encontrado!");
        }
        medico1.setNomeMedico(medico.getNomeMedico());
        medico1.setEspecialidade(medico.getEspecialidade());
        return medicoRepository.save(medico1);
    }

    @Cacheable(value = "medico", key = "#medicoId")
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