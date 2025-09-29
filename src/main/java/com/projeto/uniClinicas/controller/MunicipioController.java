package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.ClinicaDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
@Tag(name = "municipio", description = "Controller para gerenciamento de municípios")
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

    @GetMapping(value = "/clinicas", params = "nomeMunicipio")
    @Operation(summary = "Listar clínicas de um município específico", description = "Lista as clínicas presentes em um munícipio pelo nome dele")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clínicas listadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public List<ClinicaDTO> listaClinicasMunicipio(@RequestParam String nomeMunicipio){
        return municipioService.clinicasCidade(nomeMunicipio).stream()
                .map(clinicaMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}