package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.AgendaClinicaDTO;
import com.projeto.uniClinicas.dto.HorarioDTO;
import com.projeto.uniClinicas.mapper.AgendaClinicaMapper;
import com.projeto.uniClinicas.model.AgendaClinica;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.AgendaClinicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
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
    @PostMapping(path = "/medicos/{medicoId}/clinicas/{clinicaId}/agendas")
    @Operation(summary = "Adiciona uma nova agenda", description = "Cria uma nova agenda para um médico, e seus horários de atendimento, em uma clínica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public List<AgendaClinicaDTO> adicionarAgenda(@PathVariable Long medicoId, @PathVariable Long clinicaId, @RequestBody List<HorarioDTO> horarios) {
        List<AgendaClinica> agendaClinica = agendaClinicaService.adicionaAgenda(medicoId, clinicaId, horarios);
        return agendaClinica.stream().map(agendaClinicaMapper::convertToDTO).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('CLINICA')")
    @DeleteMapping(path = "/agendas/{agendaId}")
    @Operation(summary = "Remove uma agenda clínica", description = "Deleta um agendamento existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    public void removerAgenda(@PathVariable Long agendaId){
        agendaClinicaService.removeAgenda(agendaId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/agendas")
    public List<AgendaClinicaDTO> listaAgendaDaClinica(@RequestParam Long clinicaId) {
        return agendaClinicaService.agendaDaClinica(clinicaId).stream()
                .map(agendaClinicaMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('CLINICA')")
    @PutMapping(path = "/clinica/{clinicaId}/medico_antigo/{medicoAntigoId}/medico_contratado/{medicoContratadoId}/agendas")
    @Operation(summary = "Atualiza o médico de uma clínica", description = "Substitui um médico antigo por um novo em uma clínica, atualizando os agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public void atualizaMedicoDaClinica(@PathVariable Long clinicaId,@PathVariable Long medicoAntigoId,@PathVariable Long medicoContratadoId, @RequestBody List<HorarioDTO> horarioDTOs){
        agendaClinicaService.atualizaMedicoDaClinica(clinicaId, medicoAntigoId, medicoContratadoId, horarioDTOs);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(path = "/medico/{medicoId}/clinica/{clinicaId}/agendas")
    @Operation(summary = "Lista a agenda de um médico em uma clínica", description = "Retorna uma lista de agenda para um médico específico em uma clínica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos encontrados"),
            @ApiResponse(responseCode = "404", description = "Agendamentos não encontrados")
    })
    public List<AgendaClinicaDTO> medicosTrabalhoClinica(@PathVariable Long medicoId, @PathVariable Long clinicaId) {
        List<AgendaClinica> agendaClinicas = agendaClinicaService.medicoTrabalhoClinica(medicoId, clinicaId);
        return agendaClinicas.stream().map(agendaClinicaMapper::convertToDTO).collect(Collectors.toList());
    }
}