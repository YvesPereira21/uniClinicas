package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.AvaliacaoRequestDTO;
import com.projeto.uniClinicas.dto.AvaliacaoResponseDTO;
import com.projeto.uniClinicas.mapper.AvaliacaoMapper;
import com.projeto.uniClinicas.model.Avaliacao;
import com.projeto.uniClinicas.service.AvaliacaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;
    private final AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoController(AvaliacaoService avaliacaoService, AvaliacaoMapper avaliacaoMapper) {
        this.avaliacaoService = avaliacaoService;
        this.avaliacaoMapper = avaliacaoMapper;
    }


    @GetMapping("/avaliacoes/{avaliacaoId}")
    public AvaliacaoResponseDTO retornaAvaliacaoUnica(@PathVariable Long avaliacaoId) {
        Avaliacao a = avaliacaoService.pegaAvaliacaoUnica(avaliacaoId);
        return avaliacaoMapper.convertToDTO(a);
    }

    @DeleteMapping
    public void deletaAvaliacao(Long avaliacaoId) {
        avaliacaoService.deletaAvaliacao(avaliacaoId);
    }

    @GetMapping("/avaliacoes")
    public List<AvaliacaoResponseDTO> pegaTodasAvaliacoes(){
        return avaliacaoService.todasAvaliacoes().stream()
                .map(avaliacaoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/avaliacoes/usuarios/{usuarioId}")
    public List<AvaliacaoResponseDTO> pegaTodasAvaliacoesDoUsuario(@PathVariable Long usuarioId){
        return avaliacaoService.avaliacoesUsuario(usuarioId).stream()
                .map(avaliacaoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public Double avaliacaoMediaClinica(Long clinicaId){
        return avaliacaoService.calculaAvaliacaoMedia(clinicaId);
    }
}
