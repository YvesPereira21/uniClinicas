package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.AvaliacaoResponseDTO;
import com.projeto.uniClinicas.mapper.AvaliacaoMapper;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
@Validated
@Tag(name = "avaliacao", description = "Controller para gerenciamento de avaliações")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;
    private final AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoController(AvaliacaoService avaliacaoService, AvaliacaoMapper avaliacaoMapper) {
        this.avaliacaoService = avaliacaoService;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/avaliacoes/{avaliacaoId}")
    @Operation(summary = "Deleta uma avaliação", description = "Remove uma avaliação existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public ResponseEntity<Void> deletaAvaliacao(@Min(1) @PathVariable Long avaliacaoId) {
        avaliacaoService.deletaAvaliacao(avaliacaoId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios/{usuarioId}/avaliacoes")
    @Operation(summary = "Lista todas as avaliações de um usuário", description = "Retorna todas as avaliações feitas por um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliações encontradas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<List<AvaliacaoResponseDTO>> pegaTodasAvaliacoesDoUsuario(@Min(1) @PathVariable Long usuarioId){
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.avaliacoesUsuario(usuarioId).stream()
                .map(avaliacaoMapper::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(avaliacoes);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/clinicas/{clinicaId}/avaliacoes")
    @Operation(summary = "Lista avaliações para uma clínica", description = "Lista todas as avaliações feitas para uma clínica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista carregado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não há avaliações para essa clínica")
    })
    public ResponseEntity<List<AvaliacaoResponseDTO>> pegaTodasAvaliacoesDaClinica(@Min(1) @PathVariable Long clinicaId){
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.avaliacoesClinica(clinicaId).stream()
                .map(avaliacaoMapper::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(avaliacoes);
    }

    @PreAuthorize("hasAnyRole('USER', 'CLINICA')")
    @GetMapping("/clinicas/{clinicaId}/avaliacoes-media")
    @Operation(summary = "Calcula a avaliação média de uma clínica", description = "Retorna a média de todas as avaliações para uma clínica específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Média calculada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    public ResponseEntity<Double> avaliacaoMediaClinica(@Min(1) @PathVariable Long clinicaId){
        Double media = avaliacaoService.calculaAvaliacaoMedia(clinicaId);
        return ResponseEntity.ok(media);
    }
}