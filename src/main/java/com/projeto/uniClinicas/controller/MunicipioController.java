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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
@Validated
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/municipio")
    @Operation(summary = "Adiciona um novo município", description = "Cria um novo município no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Município criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao tentar adicionar um município")
    })
    public MunicipioDTO adicionaMunicipio(@Valid @RequestBody MunicipioDTO municipioDTO) {
        Municipio municipio = municipioMapper.convertToEntity(municipioDTO);
        Municipio novoMunicipio = municipioService.adicionaMunicipio(municipio);
        return municipioMapper.convertToDTO(novoMunicipio);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/municipio")
    @Operation(summary = "Deleta município", description = "Deleta município pelo nome dele")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Município deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Erro ao deletar município. Município não encontrado")
    })
    public void deletaMunicipio(@NotBlank @RequestParam String nomeMunicipio) {
        municipioService.deletaMunicipio(nomeMunicipio);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(value = "/clinicas", params = "nomeMunicipio")
    @Operation(summary = "Listar clínicas de um município específico", description = "Lista as clínicas presentes em um munícipio pelo nome dele")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clínicas listadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao listar as clínicas do município")
    })
    public List<ClinicaDTO> listaClinicasMunicipio(@NotBlank @RequestParam String nomeMunicipio){
        return municipioService.clinicasCidade(nomeMunicipio).stream()
                .map(clinicaMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}