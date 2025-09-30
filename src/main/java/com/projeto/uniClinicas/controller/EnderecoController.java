package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.ClinicaDTO;
import com.projeto.uniClinicas.mapper.ClinicaMapper;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
@Validated
@Tag(name = "endereço", description = "Controller para gerenciamento de endereços")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class EnderecoController {

    private final EnderecoService enderecoService;
    private final ClinicaMapper clinicaMapper;

    public EnderecoController(EnderecoService enderecoService, ClinicaMapper clinicaMapper) {
        this.enderecoService = enderecoService;
        this.clinicaMapper = clinicaMapper;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(value = "/enderecos/{nomeMunicipio}/clinicas")
    @Operation(summary = "Listar clínicas de um município específico", description = "Lista as clínicas presentes em um munícipio pelo nome dele")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clínicas listadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao listar as clínicas do município")
    })
    public List<ClinicaDTO> listaClinicasMunicipio(@NotBlank @PathVariable String nomeMunicipio){
        return enderecoService.clinicasCidade(nomeMunicipio).stream()
                .map(clinicaMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
