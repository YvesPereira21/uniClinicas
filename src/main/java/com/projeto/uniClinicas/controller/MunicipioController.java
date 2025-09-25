package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.MunicipioDTO;
import com.projeto.uniClinicas.mapper.MunicipioMapper;
import com.projeto.uniClinicas.model.Municipio;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.MunicipioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
@Tag(name = "municipio", description = "Controller para gerenciamento de municípios")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class MunicipioController {

    private final MunicipioService municipioService;
    private final MunicipioMapper municipioMapper;

    public MunicipioController(MunicipioService municipioService, MunicipioMapper municipioMapper) {
        this.municipioService = municipioService;
        this.municipioMapper = municipioMapper;
    }

    @PostMapping("/municipio")
    @Operation(summary = "Adiciona um novo município", description = "Cria um novo município no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Município criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public MunicipioDTO adicionaMunicipio(@RequestBody MunicipioDTO municipioDTO) {
        Municipio municipio = municipioMapper.convertToEntity(municipioDTO);
        Municipio novoMunicipio = municipioService.adicionaMunicipio(municipio);
        return municipioMapper.convertToDTO(novoMunicipio);
    }
}