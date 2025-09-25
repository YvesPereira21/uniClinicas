package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.AvaliacaoResponseDTO;
import com.projeto.uniClinicas.mapper.AvaliacaoMapper;
import com.projeto.uniClinicas.model.Avaliacao;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.AvaliacaoService;
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
@RequestMapping(path = "/api")
@Tag(name = "avaliacao", description = "Controller para gerenciamento de avaliações")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;
    private final AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoController(AvaliacaoService avaliacaoService, AvaliacaoMapper avaliacaoMapper) {
        this.avaliacaoService = avaliacaoService;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/avaliacoes/{avaliacaoId}")
    @Operation(summary = "Retorna uma avaliação única", description = "Busca e retorna uma avaliação específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação encontrada"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public AvaliacaoResponseDTO retornaAvaliacaoUnica(@PathVariable Long avaliacaoId) {
        Avaliacao a = avaliacaoService.pegaAvaliacaoUnica(avaliacaoId);
        return avaliacaoMapper.convertToDTO(a);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/avaliacoes/{avaliacaoId}")
    @Operation(summary = "Deleta uma avaliação", description = "Remove uma avaliação existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public void deletaAvaliacao(@PathVariable Long avaliacaoId) {
        avaliacaoService.deletaAvaliacao(avaliacaoId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/avaliacoes")
    @Operation(summary = "Lista todas as avaliações", description = "Retorna uma lista com todas as avaliações cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
    })
    public List<AvaliacaoResponseDTO> pegaTodasAvaliacoes(){
        return avaliacaoService.todasAvaliacoes().stream()
                .map(avaliacaoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("usuarios/{usuarioId}/avaliacoes")
    @Operation(summary = "Lista todas as avaliações de um usuário", description = "Retorna todas as avaliações feitas por um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliações encontradas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public List<AvaliacaoResponseDTO> pegaTodasAvaliacoesDoUsuario(@PathVariable Long usuarioId){
        return avaliacaoService.avaliacoesUsuario(usuarioId).stream()
                .map(avaliacaoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/clinicas/{clinicaId}/avaliacao-media")
    @Operation(summary = "Calcula a avaliação média de uma clínica", description = "Retorna a média de todas as avaliações para uma clínica específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Média calculada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    public Double avaliacaoMediaClinica(@PathVariable Long clinicaId){
        return avaliacaoService.calculaAvaliacaoMedia(clinicaId);
    }
}