package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.MedicoDTO;
import com.projeto.uniClinicas.mapper.MedicoMapper;
import com.projeto.uniClinicas.model.Medico;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api")
@Tag(name = "medico", description = "Controller para gerenciamento de médicos")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class MedicoController {

    private final MedicoService medicoService;
    private final MedicoMapper medicoMapper;

    public MedicoController(MedicoService medicoService, MedicoMapper medicoMapper) {
        this.medicoService = medicoService;
        this.medicoMapper = medicoMapper;
    }

    @PreAuthorize("hasRole('CLINICA')")
    @PostMapping("/medicos")
    @Operation(summary = "Adiciona um novo médico", description = "Cria um novo médico no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Médico criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public MedicoDTO adicionaMedico(@RequestBody MedicoDTO medicoDTO) {
        Medico medico = medicoMapper.convertToEntity(medicoDTO);
        Medico novoMedico = medicoService.adicionaMedico(medico);
        return medicoMapper.convertToDTO(novoMedico);
    }

    @PreAuthorize("hasRole('CLINICA')")
    @PutMapping("/medicos/{medicoId}")
    @Operation(summary = "Atualiza um médico existente", description = "Atualiza os dados de um médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    })
    public MedicoDTO atualizaMedico(@RequestBody MedicoDTO medicoDTO, @PathVariable Long medicoId) {
        Medico medico = medicoMapper.convertToEntity(medicoDTO);
        Medico atualizaMedico =  medicoService.atualizaMedico(medico, medicoId);
        return medicoMapper.convertToDTO(atualizaMedico);
    }

    @PreAuthorize("hasRole('CLINICA')")
    @DeleteMapping("/medicos/{medicoId}")
    @Operation(summary = "Deleta um médico", description = "Remove um médico do sistema pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Médico deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    })
    public void deletaMedico(@PathVariable("medicoId") Long medicoId){
        medicoService.deletaMedico(medicoId);
    }

    @PreAuthorize("hasAnyRole('CLINICA', 'USER')")
    @GetMapping("/medicos/{medicoId}")
    @Operation(summary = "Busca um médico", description = "Retorna os dados de um médico específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico encontrado"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    })
    public MedicoDTO retornaMedicoUnico(@PathVariable Long medicoId){
        Medico pegaMedico = medicoService.medicoUnico(medicoId);
        return medicoMapper.convertToDTO(pegaMedico);
    }
}