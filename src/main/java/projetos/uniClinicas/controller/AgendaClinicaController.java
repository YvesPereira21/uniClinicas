package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.AgendaClinicaDTO;
import com.projeto.uniClinicas.dto.auxiliares.HorarioDTO;
import com.projeto.uniClinicas.dto.auxiliares.AtualizaAgendaClinicaDTO;
import com.projeto.uniClinicas.dto.auxiliares.CriacaoAgendaDTO;
import com.projeto.uniClinicas.mapper.AgendaClinicaMapper;
import com.projeto.uniClinicas.mapper.MedicoMapper;
import com.projeto.uniClinicas.model.AgendaClinica;
import com.projeto.uniClinicas.model.Medico;
import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.AgendaClinicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Agenda clínica", description = "Controller para gerenciamento de agenda clínica")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class AgendaClinicaController {

    private final AgendaClinicaService agendaClinicaService;
    private final AgendaClinicaMapper agendaClinicaMapper;
    private final MedicoMapper medicoMapper;

    public AgendaClinicaController(AgendaClinicaService agendaClinicaService, AgendaClinicaMapper agendaClinicaMapper, MedicoMapper medicoMapper) {
        this.agendaClinicaService = agendaClinicaService;
        this.agendaClinicaMapper = agendaClinicaMapper;
        this.medicoMapper = medicoMapper;
    }

    @PreAuthorize("hasRole('CLINICA')")
    @PostMapping(path = "/agendas")
    @Operation(summary = "Adiciona um novo agendamento", description = "Cria um novo agendamento para um médico em uma clínica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<List<AgendaClinicaDTO>> adicionarAgendamento(@Valid @RequestBody CriacaoAgendaDTO criacaoAgendaDTO, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Medico medico = medicoMapper.convertToEntity(criacaoAgendaDTO.getMedico());
        List<AgendaClinica> agendaClinica = agendaClinicaService.adicionaAgenda(medico, usuario, criacaoAgendaDTO.getHorarios());
        List<AgendaClinicaDTO> agendaClinicaDTO = agendaClinica.stream().map(agendaClinicaMapper::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(agendaClinicaDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('CLINICA', 'ADMIN', 'USER')")
    @GetMapping(path = "/medico/{medicoId}/clinica/{clinicaId}/agendas")
    @Operation(summary = "Lista os agendamentos de um médico em uma clínica", description = "Retorna uma lista de agendamentos para um médico específico em uma clínica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos encontrados"),
            @ApiResponse(responseCode = "404", description = "Agendamentos não encontrados")
    })
    public ResponseEntity<Page<AgendaClinicaDTO>> medicosTrabalhoClinica(@Min(1) @PathVariable Long medicoId, @Min(1) @PathVariable Long clinicaId, @PageableDefault(size = 10) Pageable pageable) {
        Page<AgendaClinica> agendaClinicas = agendaClinicaService.medicoTrabalhoClinica(medicoId, clinicaId, pageable);
        Page<AgendaClinicaDTO> agendaClinicaDTOs = agendaClinicas.map(agendaClinicaMapper::convertToDTO);
        return new ResponseEntity<>(agendaClinicaDTOs, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLINICA')")
    @DeleteMapping(path = "/agendas/{agendaId}")
    @Operation(summary = "Remove uma agenda", description = "Deleta uma agenda existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    public ResponseEntity<Void> removerAgendamento(@Min(1) @PathVariable Long agendaId){
        agendaClinicaService.removeAgenda(agendaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('CLINICA')")
    @PutMapping(path = "/agendas")
    @Operation(summary = "Atualiza o médico de uma clínica", description = "Substitui um médico antigo por um novo em uma clínica, colocando sua própria agenda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<Void> atualizaAgendaClinica(Authentication authentication, @Valid @RequestBody AtualizaAgendaClinicaDTO atualizaAgendaClinicaDTO){
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Medico medico = medicoMapper.convertToEntity(atualizaAgendaClinicaDTO.getMedicoContratado());
        agendaClinicaService.atualizaAgendaClinica(usuario, atualizaAgendaClinicaDTO.getCrmMedicoAntigo(),
                medico, atualizaAgendaClinicaDTO.getHorarios());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLINICA')")
    @PutMapping("/medicos/{crmMedico}/agendas")
    @Operation(summary = "Atualiza a agenda de uma clínica", description = "Redefine a agenda de um médico já existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<Void> redefineAgendaMedica(Authentication authentication, @PathVariable String crmMedico, @Valid @RequestBody List<HorarioDTO> novoHorario){
        Usuario usuario = (Usuario) authentication.getPrincipal();
        agendaClinicaService.redefinirAgendaMedicoExistente(crmMedico, usuario, novoHorario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/clinicas/{clinicaId}/agendas")
    @Operation(summary = "Lista agenda de uma clínica", description = "Listar toda a agenda de uma clínica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agenda da clínica exibida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<Page<AgendaClinicaDTO>> listaAgendaClinica(@PathVariable Long clinicaId, @PageableDefault(size = 10, sort = "agendaId") Pageable pageable) {
        Page<AgendaClinicaDTO> agendaClinicaDTOS = agendaClinicaService.listaAgendaClinica(clinicaId, pageable)
                .map(agendaClinicaMapper::convertToDTO);
        return new ResponseEntity<>(agendaClinicaDTOS, HttpStatus.OK);
    }
}