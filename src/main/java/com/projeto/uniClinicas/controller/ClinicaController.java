package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.ClinicaDTO;
import com.projeto.uniClinicas.dto.EnderecoDTO;
import com.projeto.uniClinicas.dto.MedicoDTO;
import com.projeto.uniClinicas.mapper.ClinicaMapper;
import com.projeto.uniClinicas.mapper.EnderecoMapper;
import com.projeto.uniClinicas.mapper.MedicoMapper;
import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Endereco;
import com.projeto.uniClinicas.service.ClinicaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClinicaController {

    private final ClinicaService clinicaService;
    private final ClinicaMapper clinicaMapper;
    private  final MedicoMapper medicoMapper;
    private final EnderecoMapper enderecoMapper;

    public ClinicaController(ClinicaService clinicaService, ClinicaMapper clinicaMapper, MedicoMapper medicoMapper, EnderecoMapper enderecoMapper) {
        this.clinicaService = clinicaService;
        this.clinicaMapper = clinicaMapper;
        this.medicoMapper = medicoMapper;
        this.enderecoMapper = enderecoMapper;
    }

    @PostMapping("/clinicas")
    public ClinicaDTO adicionaClinica(@Valid @RequestBody ClinicaDTO clinicaDTO) {
        Clinica clinica = clinicaMapper.convertToEntity(clinicaDTO);
        Clinica novaClinica = clinicaService.adicionaClinica(clinica);
        return clinicaMapper.convertToDTO(novaClinica);
    }

    @PutMapping("/clinicas/{clinicaId}")
    public ClinicaDTO atualizaClinica(@Valid @RequestBody ClinicaDTO clinicaDTO, @PathVariable Long clinicaId) {
        Clinica clinica = clinicaMapper.convertToEntity(clinicaDTO);
        Clinica clinicaAtualizada =  clinicaService.atualizaClinica(clinica, clinicaId);
        return clinicaMapper.convertToDTO(clinicaAtualizada);
    }

    @GetMapping("/clinicas/{clinicaId}")
    public ClinicaDTO retornaClinica(@PathVariable Long clinicaId) {
        Clinica clinica = clinicaService.pegaClinica(clinicaId);
        return clinicaMapper.convertToDTO(clinica);
    }

    @DeleteMapping("/clinicas/{clinicaId}")
    public void removeClinica(@PathVariable Long clinicaId) {
        clinicaService.deletaClinica(clinicaId);
    }

    @GetMapping(value = "/clinicas", params = "cep")
    public List<ClinicaDTO> listaClinicasDaCidade(@RequestParam String cep) {
        return clinicaService.mostraTodasClinicasDaCidade(cep).stream()
                .map(clinicaMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/clinicas", params = "nomeClinica")
    public List<ClinicaDTO> listaClinicasComCertoNome(@RequestParam String nomeClinica) {
        return clinicaService.mostraClinicasComCertoNome(nomeClinica).stream()
                .map(clinicaMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/clinicas", params = "{hora, minuto}")
    public List<ClinicaDTO> listaClinicasPeloHorarioFuncionamento(@Min(0) @RequestParam int hora, @Min(0) @RequestParam int minuto) {
        return clinicaService.mostraPeloHorarioFuncionamento(hora, minuto).stream()
                .map(clinicaMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/clinicas/enderecos")
    public ClinicaDTO encontraClinicaPeloEndereco(@Valid @RequestBody EnderecoDTO endereco) {
        Endereco end = enderecoMapper.convertToEntity(endereco);
        Clinica clinica = clinicaService.encontraClinicaPeloEndereco(end);
        return clinicaMapper.convertToDTO(clinica);
    }

    @GetMapping("/clinica/{clinicaId}/medicos")
    public List<MedicoDTO> medicosDaClinica(@PathVariable Long clinicaId) {
        return clinicaService.todosMedicosClinica(clinicaId).stream()
                .map(medicoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

}
