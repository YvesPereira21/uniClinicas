package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.AgendaClinicaDTO;
import com.projeto.uniClinicas.dto.auxiliares.AgendaCriacaoDTO;
import com.projeto.uniClinicas.dto.auxiliares.AtualizacaoAgendaDTO;
import com.projeto.uniClinicas.mapper.AgendaClinicaMapper;
import com.projeto.uniClinicas.model.AgendaClinica;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.AgendaClinicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "agenda clínica", description = "Controller para gerenciamento de agendas")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class AgendaClinicaController {

    private final AgendaClinicaService agendaClinicaService;
    private final AgendaClinicaMapper agendaClinicaMapper;

    public AgendaClinicaController(AgendaClinicaService agendaClinicaService, AgendaClinicaMapper agendaClinicaMapper) {
        this.agendaClinicaService = agendaClinicaService;
        this.agendaClinicaMapper = agendaClinicaMapper;
    }

    @PreAuthorize("hasRole('CLINICA')")
    @PostMapping(path = "/clinicas/{clinicaId}/agendas")
    @Operation(summary = "Adiciona uma nova agenda", description = "Cria uma nova agenda para um médico, e seus horários de atendimento, em uma clínica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public List<AgendaClinicaDTO> adicionarAgenda(@Min(1) @PathVariable Long clinicaId, @Valid @RequestBody AgendaCriacaoDTO criacao) {
        List<AgendaClinica> agendaClinica = agendaClinicaService.adicionaAgenda(clinicaId, criacao.getMedicoId(), criacao.getHorarios());
        return agendaClinica.stream().map(agendaClinicaMapper::convertToDTO).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('CLINICA')")
    @DeleteMapping(path = "/agendas/{agendaId}")
    @Operation(summary = "Remove uma agenda clínica", description = "Deleta um agendamento existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    public void removerAgenda(@Min(1) @PathVariable Long agendaId){
        agendaClinicaService.removeAgenda(agendaId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/agendas")
    @Operation(summary = "Lista a agenda de uma clínica", description = "Retorna a agenda de uma clínica específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agenda encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada ou não possui agenda")
    })
    public List<AgendaClinicaDTO> listaAgendaDaClinica(@Min(1) @RequestParam Long clinicaId) {
        return agendaClinicaService.agendaDaClinica(clinicaId).stream()
                .map(agendaClinicaMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('CLINICA')")
    @PutMapping(path = "/clinicas/{clinicaId}/agendas")
    @Operation(summary = "Atualiza o médico de uma clínica", description = "Substitui um médico antigo por um novo em uma clínica, atualizando os agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public void atualizaAgendaClinica(@Min(1) @PathVariable Long clinicaId, @Valid @RequestBody AtualizacaoAgendaDTO atualizaMedico) {
        agendaClinicaService.atualizaAgendaClinica(clinicaId, atualizaMedico);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(path = "/medicos/{medicoId}/clinicas/{clinicaId}/agendas")
    @Operation(summary = "Lista a agenda de um médico em uma clínica", description = "Retorna uma lista de agenda para um médico específico em uma clínica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos encontrados"),
            @ApiResponse(responseCode = "404", description = "Agendamentos não encontrados")
    })
    public List<AgendaClinicaDTO> medicosTrabalhoClinica(@Min(1) @PathVariable Long medicoId, @Min(1) @PathVariable Long clinicaId) {
        List<AgendaClinica> agendaClinicas = agendaClinicaService.medicoTrabalhoClinica(medicoId, clinicaId);
        return agendaClinicas.stream().map(agendaClinicaMapper::convertToDTO).collect(Collectors.toList());
    }
}