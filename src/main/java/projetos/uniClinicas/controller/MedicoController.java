package projetos.uniClinicas.controller;

import projetos.uniClinicas.dto.MedicoDTO;
import projetos.uniClinicas.mapper.MedicoMapper;
import projetos.uniClinicas.model.Medico;
import projetos.uniClinicas.model.Usuario;
import projetos.uniClinicas.security.SecurityConfigurations;
import projetos.uniClinicas.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api")
@Validated
@Tag(name = "Médico", description = "Controller para gerenciamento de médicos")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class MedicoController {

    private final MedicoService medicoService;
    private final MedicoMapper medicoMapper;

    public MedicoController(MedicoService medicoService, MedicoMapper medicoMapper) {
        this.medicoService = medicoService;
        this.medicoMapper = medicoMapper;
    }

    @PreAuthorize("hasRole('CLINICA')")
    @PutMapping("/medicos")
    @Operation(summary = "Atualiza um médico existente", description = "Atualiza os dados de um médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    })
    public ResponseEntity<MedicoDTO> atualizaMedico(@Valid @RequestBody MedicoDTO medicoDTO) {
        Medico medico = medicoMapper.convertToEntity(medicoDTO);
        Medico atualizaMedico =  medicoService.atualizaMedico(medico);
        return new ResponseEntity<>(medicoMapper.convertToDTO(atualizaMedico), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CLINICA', 'USER')")
    @GetMapping("/medicos/{medicoId}")
    @Operation(summary = "Busca um médico", description = "Retorna os dados de um médico específico pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico encontrado"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    })
    public ResponseEntity<MedicoDTO> retornaMedico(@Min(1) @PathVariable Long medicoId, Authentication authentication){
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Medico pegaMedico = medicoService.medicoUnico(medicoId, usuario);
        return new ResponseEntity<>(medicoMapper.convertToDTO(pegaMedico), HttpStatus.OK);
    }
}