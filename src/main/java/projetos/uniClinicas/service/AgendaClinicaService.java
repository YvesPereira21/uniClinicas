package projetos.uniClinicas.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import projetos.uniClinicas.dto.auxiliares.HorarioDTO;
import projetos.uniClinicas.exception.*;
import projetos.uniClinicas.model.AgendaClinica;
import projetos.uniClinicas.model.Clinica;
import projetos.uniClinicas.model.Medico;
import projetos.uniClinicas.model.Usuario;
import projetos.uniClinicas.repository.AgendaClinicaRepository;
import projetos.uniClinicas.repository.ClinicaRepository;
import projetos.uniClinicas.repository.MedicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
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

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "medico_clinica", allEntries = true),
            @CacheEvict(value = "clinica_agenda", allEntries = true)
    })
    public List<AgendaClinica> adicionaAgenda(Medico medicoNovo, Usuario usuario, List<HorarioDTO> horarios) {
        Clinica clinica = clinicaRepository.findByUsuario(usuario);
        if (clinica == null) {
            throw new ObjetoNaoEncontradoException("Clínica não associada a este usuário.");
        }
        // Lógica para validar o médico por CRM e nome
        Medico medicoParaAgendar;
        Medico medicoExistente = medicoRepository.findByCrmMedico(medicoNovo.getCrmMedico());
        if (medicoExistente != null) {
            // verifica se há um médico com este mesmo CRM e nome.
            if (!medicoExistente.getNomeMedico().equalsIgnoreCase(medicoNovo.getNomeMedico())
                    || !medicoExistente.getEspecialidade().equalsIgnoreCase(medicoNovo.getEspecialidade())) {
                // O CRM é o mesmo, mas o nome é diferente. Lançamos um erro.
                throw new ObjetoJaAdicionadoException("Já existe um médico cadastrado com esse CRM " + medicoNovo.getCrmMedico() + " com o nome '" + medicoExistente.getNomeMedico() + "'. Verifique os dados.");
            }
            // Se o CRM e o nome são iguais, usamos o médico que já existe no banco.
            medicoParaAgendar = medicoExistente;
        } else {
            // Se o médico não existe de forma alguma, então é seguro salvar o novo.
            medicoParaAgendar = medicoRepository.save(medicoNovo);
        }

        List<AgendaClinica> novaAgenda = adicionaNaLista(medicoParaAgendar, clinica, horarios);

        return agendaClinicaRepository.saveAll(novaAgenda);
    }

    @Cacheable(value = "medico_clinica", key = "#medicoId + '-' + #clinicaId + '-' + #pageable.pageNumber")
    public Page<AgendaClinica> medicoTrabalhoClinica(Long medicoId, Long clinicaId, Pageable pageable) {
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Essa clínica não existe!"));
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Esse médico não existe!"));
        return agendaClinicaRepository.findAgendaClinicaByMedicoIdAndClinicaId(medicoId, clinicaId, pageable);
    }

    @Caching(evict = {
            @CacheEvict(value = "medico_clinica", allEntries = true),
            @CacheEvict(value = "clinica_agenda", allEntries = true)
    })
    public void removeAgenda(Long agendaId){
        AgendaClinica agendaClinica = agendaClinicaRepository.findById(agendaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Agenda clínica não encontrada!"));
        agendaClinicaRepository.deleteById(agendaId);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "medico_clinica", allEntries = true),
            @CacheEvict(value = "clinica_agenda", allEntries = true)
    })
    public void atualizaAgendaClinica(Usuario usuario, String crmMedicoAntigo, Medico medicoContratadoNovo, List<HorarioDTO> horarios) {
        Clinica clinica = clinicaRepository.findByUsuario(usuario);
        if (clinica == null) {
            throw new ObjetoNaoEncontradoException("Clínica não existente para o usuário logado.");
        }
        Medico medicoAntigo = medicoRepository.findByCrmMedico(crmMedicoAntigo);
        if (medicoAntigo == null) {
            throw new ObjetoNaoEncontradoException("Médico a ser substituído (CRM: " + crmMedicoAntigo + ") não encontrado.");
        }
        List<AgendaClinica> atendimentosAntigos = agendaClinicaRepository.findAgendaClinicaByMedicoIdAndClinicaId(medicoAntigo.getMedicoId(), clinica.getClinicaId());
        if (atendimentosAntigos.isEmpty()){
            throw new MedicoJaSubstituidoException("O médico antigo não possui agenda nesta clínica ou já foi substituído!");
        }
        // Valida e persiste o médico contratado (reutilizando a lógica de adicionar médico)
        Medico medicoContratadoParaAgendar;
        Medico medicoExistente = medicoRepository.findByCrmMedico(medicoContratadoNovo.getCrmMedico());
        if (medicoExistente != null) {
            if (!medicoExistente.getNomeMedico().equalsIgnoreCase(medicoContratadoNovo.getNomeMedico())) {
                throw new ObjetoJaAdicionadoException("Já existe um médico cadastrado com o CRM " + medicoContratadoNovo.getCrmMedico() + " com o nome '" + medicoExistente.getNomeMedico() + "'. Verifique os dados.");
            }
            medicoContratadoParaAgendar = medicoExistente;
        } else {
            medicoContratadoParaAgendar = medicoRepository.save(medicoContratadoNovo);
        }
        // Prepara e valida a nova agenda antes de fazer alterações
        List<AgendaClinica> novaAgenda = adicionaNaLista(medicoContratadoParaAgendar, clinica, horarios);
        // Se tudo estiver certo, deleta a agenda antiga e salva a nova
        agendaClinicaRepository.deleteAll(atendimentosAntigos);
        agendaClinicaRepository.saveAll(novaAgenda);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "medico_clinica", allEntries = true),
            @CacheEvict(value = "clinica_agenda", allEntries = true)
    })
    public void redefinirAgendaMedicoExistente(String crmMedico, Usuario usuario, List<HorarioDTO> novosHorarios) {
        Clinica clinica = clinicaRepository.findByUsuario(usuario);
        if (clinica == null) {
            throw new ObjetoNaoEncontradoException("Clínica não encontrada para o usuário logado.");
        }
        Medico medico = medicoRepository.findByCrmMedico(crmMedico);
        if (medico == null) {
            throw new ObjetoNaoEncontradoException("Nenhum médico encontrado com o CRM: " + crmMedico);
        }
        // Busca e remove a agenda antiga deste médico APENAS nesta clínica
        List<AgendaClinica> agendaAntiga = agendaClinicaRepository.findAgendaClinicaByMedicoIdAndClinicaId(medico.getMedicoId(), clinica.getClinicaId());
        if (agendaAntiga.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Este médico não possui uma agenda nesta clínica para ser atualizada.");
        }
        // Prepara e valida a nova agenda antes de fazer alterações
        List<AgendaClinica> novaAgenda = adicionaNaLista(medico, clinica, novosHorarios);
        // Se a validação passou, remove a agenda antiga e salva a nova
        agendaClinicaRepository.deleteAll(agendaAntiga);
        agendaClinicaRepository.saveAll(novaAgenda);
    }

    @Cacheable(value = "clinica_agenda", key = "#clinicaId + '-' + #pageable.pageNumber")
    public Page<AgendaClinica> listaAgendaClinica(Long clinicaId, Pageable pageable){
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada!"));
        return agendaClinicaRepository.findAllAgendaClinicaByClinicaId(clinicaId, pageable);
    }

    private List<AgendaClinica> adicionaNaLista(Medico medico, Clinica clinica, List<HorarioDTO> horarios) {
        List<AgendaClinica> novaAgenda = new ArrayList<>();
        adiciona(clinica, horarios, novaAgenda, medico);
        return novaAgenda;
    }

    private void adiciona(Clinica clinica, List<HorarioDTO> horarios, List<AgendaClinica> novaAgenda, Medico med) {
        for(HorarioDTO horario : horarios){
            verificaErro(med, clinica, horario);
            AgendaClinica novoAtendimento = new AgendaClinica();
            novoAtendimento.setMedico(med);
            novoAtendimento.setClinica(clinica);
            novoAtendimento.setDiaSemana(horario.getDiaSemana());
            novoAtendimento.setHorarioAtendimentoMedico(horario.getHorarioAtendimentoMedico());
            novoAtendimento.setHorarioSaida(horario.getHorarioSaida());
            novaAgenda.add(novoAtendimento);
        }
    }
    
    //junta todos os erros abaixo em apenas uma classe
    private void verificaErro(Medico medico, Clinica clinica, HorarioDTO horario) {
        boolean verificadorHorarioMedico = verificaHorario(clinica, horario);
        boolean agendaExistente = agendaExistente(clinica, medico, horario);
        boolean sobreposicaoHorario = verificaSobreposicaoDeHorario(medico, clinica, horario);
        if (verificadorHorarioMedico){
            throw new HorarioAtendimentoException("Esse horário de atendimento não está de acordo com o horário de funcionamento da clínica!");
        } else if (agendaExistente) {
            throw new ObjetoJaAdicionadoException("Essa agenda já existe!");
        } else if (sobreposicaoHorario) {
            throw new SobreposicaoHorarioException("Há uma sobreposição de horário aqui!");
        }
        verificaSobreposicaoGlobal(medico, horario);
    }

    //lógica para o horário de atendimento do médico não ser antes do horário de funcionamento
    // e nem depois do horário que fecha
    private boolean verificaHorario(Clinica clinica, HorarioDTO horario){
        boolean erro1 = clinica.getHorarioFuncionamento().isAfter(horario.getHorarioAtendimentoMedico());
        boolean erro2 = clinica.getHorarioFechamento().isBefore(horario.getHorarioSaida());
        boolean erro3 = !horario.getHorarioAtendimentoMedico().isBefore(horario.getHorarioSaida());

        return erro1 || erro2 || erro3;
    }

    //lógica para não permitir a duplicação de uma agenda em uma clínica
    private boolean agendaExistente(Clinica clinica, Medico medico, HorarioDTO horario){
        return agendaClinicaRepository
                .existsByMedico_CrmMedicoAndClinicaAndDiaSemanaAndHorarioAtendimentoMedico(
                        medico.getCrmMedico(), clinica, horario.getDiaSemana(), horario.getHorarioAtendimentoMedico()
                );
    }

    //lógica para não permitir a sobreposição de um horário
    //se o médico trabalha das 9 até às 13 na clínica pela segunda,
    // ele não pode ter outro horário de atendimento no mesmo dia entre esse horário
    private boolean verificaSobreposicaoDeHorario(Medico medico, Clinica clinica, HorarioDTO novoHorario) {
        List<AgendaClinica> agendamentosExistentes = agendaClinicaRepository.findAllByMedicoAndClinicaAndDiaSemana(
                medico,
                clinica,
                novoHorario.getDiaSemana()
        );
        LocalTime inicioNovo = novoHorario.getHorarioAtendimentoMedico();
        LocalTime fimNovo = novoHorario.getHorarioSaida();
        for (AgendaClinica agendamentoExistente : agendamentosExistentes) {
            LocalTime inicioExistente = agendamentoExistente.getHorarioAtendimentoMedico();
            LocalTime fimExistente = agendamentoExistente.getHorarioSaida();
            if (inicioNovo.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente)) {
                return true;
            }
        }
        return false;
    }

    //não permite que um médico tenha 2 horários de atendimento iguais
    //em clínicas diferentes
    private void verificaSobreposicaoGlobal(Medico medico, HorarioDTO novoHorario) {
        // Usa o novo metodo do repositório para buscar a agenda do médico em TODAS as clínicas
        List<AgendaClinica> agendamentosGlobais = agendaClinicaRepository
                .findByMedico_CrmMedicoAndDiaSemana(medico.getCrmMedico(), novoHorario.getDiaSemana());
        LocalTime inicioNovo = novoHorario.getHorarioAtendimentoMedico();
        LocalTime fimNovo = novoHorario.getHorarioSaida();
        for (AgendaClinica agendamentoExistente : agendamentosGlobais) {
            LocalTime inicioExistente = agendamentoExistente.getHorarioAtendimentoMedico();
            LocalTime fimExistente = agendamentoExistente.getHorarioSaida();
            // Lógica de sobreposição
            if (inicioNovo.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente)) {
                throw new SobreposicaoHorarioException(
                        "Conflito de agenda: O médico já possui um agendamento (" + inicioExistente + " - " + fimExistente +
                                ") em outra clínica neste mesmo dia e horário."
                );
            }
        }
    }
}