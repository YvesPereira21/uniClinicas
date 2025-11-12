package projetos.uniClinicas.controller;

import projetos.uniClinicas.dto.requests.ClinicaRequestDTO;
import projetos.uniClinicas.dto.responses.ClinicaResponseDTO;
import projetos.uniClinicas.dto.MedicoDTO;
import projetos.uniClinicas.mapper.ClinicaMapper;
import projetos.uniClinicas.mapper.EnderecoMapper;
import projetos.uniClinicas.mapper.MedicoMapper;
import projetos.uniClinicas.model.Clinica;
import projetos.uniClinicas.security.SecurityConfigurations;
import projetos.uniClinicas.service.ClinicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Clínica", description = "Controller para gerenciamento de clínicas")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class ClinicaController {

    private final ClinicaService clinicaService;
    private final ClinicaMapper clinicaMapper;
    private final MedicoMapper medicoMapper;
    private final EnderecoMapper enderecoMapper;

    public ClinicaController(ClinicaService clinicaService, ClinicaMapper clinicaMapper, MedicoMapper medicoMapper, EnderecoMapper enderecoMapper) {
        this.clinicaService = clinicaService;
        this.clinicaMapper = clinicaMapper;
        this.medicoMapper = medicoMapper;
        this.enderecoMapper = enderecoMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/clinicas")
    @Operation(summary = "Adiciona uma nova clínica", description = "Cria uma nova clínica no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clínica criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<ClinicaResponseDTO> adicionaClinica(@Valid @RequestBody ClinicaRequestDTO clinicaRequestDTO) {
        Clinica clinica = clinicaMapper.convertToEntity(clinicaRequestDTO);
        Clinica novaClinica = clinicaService.adicionaClinica(clinica);
        return new ResponseEntity<>(clinicaMapper.convertToDTO(novaClinica), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/clinicas/{clinicaId}")
    @Operation(summary = "Atualiza uma clínica existente", description = "Atualiza os dados de uma clínica pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clínica atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    public ResponseEntity<ClinicaResponseDTO> atualizaClinica(@Valid @RequestBody ClinicaRequestDTO clinicaRequestDTO, @Min(1) @PathVariable Long clinicaId) {
        Clinica clinica = clinicaMapper.convertToEntity(clinicaRequestDTO);
        Clinica clinicaAtualizada =  clinicaService.atualizaClinica(clinica, clinicaId);
        return new ResponseEntity<>(clinicaMapper.convertToDTO(clinicaAtualizada), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/clinicas/{clinicaId}")
    @Operation(summary = "Retorna uma clínica", description = "Busca e retorna os dados de uma clínica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clínica encontrada"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    public ResponseEntity<ClinicaResponseDTO> retornaClinica(@Min(1) @PathVariable Long clinicaId) {
        Clinica clinica = clinicaService.pegaClinica(clinicaId);
        return new ResponseEntity<>(clinicaMapper.convertToDTO(clinica), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/clinicas/{clinicaId}")
    @Operation(summary = "Remove uma clínica", description = "Deleta uma clínica do sistema pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Clínica removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    public ResponseEntity<Void> removeClinica(@Min(1) @PathVariable Long clinicaId) {
        clinicaService.deletaClinica(clinicaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(value = "/clinicas", params = "nomeClinica")
    @Operation(summary = "Lista clínicas por nome", description = "Busca e retorna clínicas que contenham o nome fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
    })
    public ResponseEntity<Page<ClinicaResponseDTO>> listaClinicasComCertoNome(@NotBlank @RequestParam String nomeClinica, @PageableDefault(size = 10, sort = "nomeClinica") Pageable pageable) {
        Page<ClinicaResponseDTO> clinicaResponseDTOS = clinicaService.mostraClinicasComCertoNome(nomeClinica, pageable)
                .map(clinicaMapper::convertToDTO);
        return new ResponseEntity<>(clinicaResponseDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/clinica/{clinicaId}/medicos")
    @Operation(summary = "Lista os médicos de uma clínica", description = "Retorna todos os médicos associados a uma clínica específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médicos encontrados"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    public ResponseEntity<Page<MedicoDTO>> medicosDaClinica(@Min(1) @PathVariable Long clinicaId, @PageableDefault(size = 10, sort = "nomeMedico") Pageable pageable) {
        Page<MedicoDTO> medicoDTOS = clinicaService.todosMedicosClinica(clinicaId, pageable)
                .map(medicoMapper::convertToDTO);
        return new ResponseEntity<>(medicoDTOS, HttpStatus.OK);
    }

}