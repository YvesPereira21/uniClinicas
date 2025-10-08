package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.responses.AvaliacaoResponseDTO;
import com.projeto.uniClinicas.mapper.AvaliacaoMapper;
import com.projeto.uniClinicas.model.Avaliacao;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
@Validated
@Tag(name = "Avaliação", description = "Controller para gerenciamento de avaliações")
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
    public ResponseEntity<AvaliacaoResponseDTO> retornaAvaliacaoUnica(@Min(1) @PathVariable Long avaliacaoId) {
        Avaliacao a = avaliacaoService.pegaAvaliacaoUnica(avaliacaoId);
        return new ResponseEntity<>(avaliacaoMapper.convertToDTO(a), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/avaliacoes/{avaliacaoId}")
    @Operation(summary = "Deleta uma avaliação", description = "Remove uma avaliação existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public ResponseEntity<Void> deletaAvaliacao(@Min(1) @PathVariable Long avaliacaoId) {
        avaliacaoService.deletaAvaliacao(avaliacaoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/avaliacoes")
    @Operation(summary = "Lista todas as avaliações", description = "Retorna uma lista com todas as avaliações cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
    })
    public ResponseEntity<Page<AvaliacaoResponseDTO>> pegaTodasAvaliacoes(@PageableDefault(size = 10, sort = "horarioComentario") Pageable pageable){
        Page<AvaliacaoResponseDTO> avaliacaoResponseDTOS = avaliacaoService.todasAvaliacoes(pageable)
                .map(avaliacaoMapper::convertToDTO);
        return new ResponseEntity<>(avaliacaoResponseDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("usuarios/{usuarioId}/avaliacoes")
    @Operation(summary = "Lista todas as avaliações de um usuário", description = "Retorna todas as avaliações feitas por um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliações encontradas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Page<AvaliacaoResponseDTO>> pegaTodasAvaliacoesDoUsuario(@Min(1) @PathVariable Long usuarioId, @PageableDefault(size = 5, sort = "horarioComentario") Pageable pageable){
        Page<AvaliacaoResponseDTO> avaliacaoResponseDTOS = avaliacaoService.avaliacoesUsuario(usuarioId, pageable)
                .map(avaliacaoMapper::convertToDTO);
        return new ResponseEntity<>(avaliacaoResponseDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'CLINICA')")
    @GetMapping("clinicas/{clinicaId}/avaliacoes")
    public ResponseEntity<Page<AvaliacaoResponseDTO>> pegaTodasAvaliacoesDaClinica(@PathVariable Long clinicaId, @PageableDefault(size = 5, sort = "horarioComentario") Pageable pageable) {
        Page<AvaliacaoResponseDTO> avaliacaoResponseDTOS = avaliacaoService.avaliacoesClinica(clinicaId, pageable)
                .map(avaliacaoMapper::convertToDTO);
        return new ResponseEntity<>(avaliacaoResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/clinicas/{clinicaId}/avaliacao-media")
    @Operation(summary = "Calcula a avaliação média de uma clínica", description = "Retorna a média de todas as avaliações para uma clínica específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Média calculada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    public ResponseEntity<Double> avaliacaoMediaClinica(@Min(1) @PathVariable Long clinicaId){
        return new ResponseEntity<>(avaliacaoService.calculaAvaliacaoMedia(clinicaId), HttpStatus.OK);
    }
}