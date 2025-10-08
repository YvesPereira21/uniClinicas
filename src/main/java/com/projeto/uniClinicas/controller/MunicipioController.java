package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.ClinicaResponseDTO;
import com.projeto.uniClinicas.dto.MunicipioDTO;
import com.projeto.uniClinicas.mapper.ClinicaMapper;
import com.projeto.uniClinicas.mapper.MunicipioMapper;
import com.projeto.uniClinicas.model.Municipio;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.MunicipioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
@Validated
@Tag(name = "Município", description = "Controller para gerenciamento de municípios")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class MunicipioController {

    private final MunicipioService municipioService;
    private final MunicipioMapper municipioMapper;
    private final ClinicaMapper clinicaMapper;

    public MunicipioController(MunicipioService municipioService, MunicipioMapper municipioMapper, ClinicaMapper clinicaMapper) {
        this.municipioService = municipioService;
        this.municipioMapper = municipioMapper;
        this.clinicaMapper = clinicaMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/municipio")
    @Operation(summary = "Adiciona um novo município", description = "Cria um novo município no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Município criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<MunicipioDTO> adicionaMunicipio(@Valid @RequestBody MunicipioDTO municipioDTO) {
        Municipio municipio = municipioMapper.convertToEntity(municipioDTO);
        Municipio novoMunicipio = municipioService.adicionaMunicipio(municipio);
        return new ResponseEntity<>(municipioMapper.convertToDTO(novoMunicipio), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/municipio")
    @Operation(summary = "Lista clínicas da cidade", description = "Lista todas as clínicas que estão em uma cidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clínicas listadas com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida!")
    })
    public ResponseEntity<List<ClinicaResponseDTO>> listaTodasClinicasDaCidade(@NotBlank @RequestParam String nomeMunicipio){
        List<ClinicaResponseDTO> clinicaResponseDTOS = municipioService.mostraTodasClinicasDaCidade(nomeMunicipio).stream()
                .map(clinicaMapper::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(clinicaResponseDTOS, HttpStatus.OK);
    }
}