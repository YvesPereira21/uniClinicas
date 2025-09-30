package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.ClinicaDTO;
import com.projeto.uniClinicas.dto.EnderecoDTO;
import com.projeto.uniClinicas.mapper.ClinicaMapper;
import com.projeto.uniClinicas.mapper.EnderecoMapper;
import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Endereco;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.ClinicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "clinica", description = "Controller para gerenciamento de clínicas")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class ClinicaController {

    private final ClinicaService clinicaService;
    private final ClinicaMapper clinicaMapper;
    private final EnderecoMapper enderecoMapper;

    public ClinicaController(ClinicaService clinicaService, ClinicaMapper clinicaMapper, EnderecoMapper enderecoMapper) {
        this.clinicaService = clinicaService;
        this.clinicaMapper = clinicaMapper;
        this.enderecoMapper = enderecoMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/clinicas")
    @Operation(summary = "Adiciona uma nova clínica", description = "Cria uma nova clínica no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clínica criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ClinicaDTO adicionaClinica(@Valid @RequestBody ClinicaDTO clinicaDTO) {
        Clinica clinica = clinicaMapper.convertToEntity(clinicaDTO);
        Clinica novaClinica = clinicaService.adicionaClinica(clinica);
        return clinicaMapper.convertToDTO(novaClinica);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/clinicas/{clinicaId}")
    @Operation(summary = "Atualiza uma clínica existente", description = "Atualiza os dados de uma clínica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clínica atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    public ClinicaDTO atualizaClinica(@Valid @RequestBody ClinicaDTO clinicaDTO, @Min(1) @PathVariable Long clinicaId) {
        Clinica clinica = clinicaMapper.convertToEntity(clinicaDTO);
        Clinica clinicaAtualizada =  clinicaService.atualizaClinica(clinica, clinicaId);
        return clinicaMapper.convertToDTO(clinicaAtualizada);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLINICA', 'USER')")
    @GetMapping("/clinicas/{clinicaId}")
    @Operation(summary = "Retorna uma clínica", description = "Busca e retorna os dados de uma clínica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clínica encontrada"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    public ClinicaDTO retornaClinica(@Min(1) @PathVariable Long clinicaId) {
        Clinica clinica = clinicaService.pegaClinica(clinicaId);
        return clinicaMapper.convertToDTO(clinica);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/clinicas/{clinicaId}")
    @Operation(summary = "Remove uma clínica", description = "Deleta uma clínica do sistema pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Clínica removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    public void removeClinica(@Min(1) @PathVariable Long clinicaId) {
        clinicaService.deletaClinica(clinicaId);
    }

    @PreAuthorize("hasRole('CLINICA')")
    @PutMapping("/clinicas/{clinicaId}/enderecos")
    @Operation(summary = "Atualiza endereço da clínica", description = "Atualiza apenas o endereço da clínica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
    })
    public ClinicaDTO atualizarEndereco(@Min(1) @PathVariable Long clinicaId, @Valid @RequestBody EnderecoDTO endereco) {
        Endereco endereco1 = enderecoMapper.convertToEntity(endereco);
        Clinica enderecoAtualizado = clinicaService.atualizaEndereco(clinicaId, endereco1);
        return clinicaMapper.convertToDTO(enderecoAtualizado);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(value = "/clinicas", params = "nomeClinica")
    @Operation(summary = "Lista clínicas por nome", description = "Busca e retorna clínicas que contenham o nome fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
    })
    public List<ClinicaDTO> listaClinicasComCertoNome(@NotBlank @RequestParam  String nomeClinica) {
        return clinicaService.mostraClinicasComCertoNome(nomeClinica).stream()
                .map(clinicaMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/enderecos")
    @Operation(summary = "Encontra clínica pelo endereço", description = "Busca uma clínica com base nos dados de endereço fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clínica encontrada"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    public ClinicaDTO encontraClinicaPeloEndereco(@Valid @RequestBody EnderecoDTO endereco) {
        Endereco end = enderecoMapper.convertToEntity(endereco);
        Clinica clinica = clinicaService.encontraClinicaPeloEndereco(end);
        return clinicaMapper.convertToDTO(clinica);
    }

}