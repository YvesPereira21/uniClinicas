package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.requests.AvaliacaoRequestDTO;
import com.projeto.uniClinicas.dto.responses.AvaliacaoResponseDTO;
import com.projeto.uniClinicas.dto.requests.UsuarioComumRequestDTO;
import com.projeto.uniClinicas.dto.responses.UsuarioComumResponseDTO;
import com.projeto.uniClinicas.mapper.AvaliacaoMapper;
import com.projeto.uniClinicas.mapper.UsuarioComumMapper;
import com.projeto.uniClinicas.model.Avaliacao;
import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.model.UsuarioComum;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.UsuarioComumService;
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
@RequestMapping("/api")
@Validated
@Tag(name = "Usuário", description = "Controller de usuário")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class UsuarioComumController {

    private final UsuarioComumService usuarioComumService;
    private final UsuarioComumMapper usuarioComumMapper;
    private final AvaliacaoMapper avaliacaoMapper;

    public UsuarioComumController(UsuarioComumService usuarioComumService, UsuarioComumMapper usuarioComumMapper, AvaliacaoMapper avaliacaoMapper) {
        this.usuarioComumService = usuarioComumService;
        this.usuarioComumMapper = usuarioComumMapper;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/usuarios")
    @Operation(summary = "Salva dados do usuário", description = "Método para salvar dados do usuário")
    @ApiResponse(responseCode = "201", description = "Usuário salvado com sucesso!")
    @ApiResponse(responseCode = "400", description = "Erro ao tentar salvar o usuário.")
    public ResponseEntity<UsuarioComumResponseDTO> cadastrarUsuario(@Valid @RequestBody UsuarioComumRequestDTO usuarioComumRequestDTO){
        UsuarioComum usuario = usuarioComumMapper.convertToEntity(usuarioComumRequestDTO);
        UsuarioComum novoUsuario = usuarioComumService.adicionaUsuario(usuario);
        return new ResponseEntity<>(usuarioComumMapper.convertToDTO(novoUsuario), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/usuarios")
    public ResponseEntity<UsuarioComumResponseDTO> atualizaUsuario(@Valid @RequestBody UsuarioComumRequestDTO usuarioComumRequestDTO){
        UsuarioComum usuario = usuarioComumMapper.convertToEntity(usuarioComumRequestDTO);
        UsuarioComum novoUsuario = usuarioComumService.atualizaUsuario(usuario);
        return new ResponseEntity<>(usuarioComumMapper.convertToDTO(novoUsuario), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/usuarios/{usuarioId}")
    @Operation(summary = "Deleta um usuário", description = "Remove um usuário existente pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrada")
    })
    public ResponseEntity<Void> deletaUsuario(@PathVariable Long usuarioId){
        usuarioComumService.deletaUsuario(usuarioId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("avaliacao/{clinicaId}/clinica")
    @Operation(summary = "Cria avaliação", description = "Cria avaliação a partir do usuário que está logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao tentar criar a avaliação!")
    })
    public ResponseEntity<AvaliacaoResponseDTO> avaliacao(Authentication autentication, @Valid @RequestBody AvaliacaoRequestDTO avaliacaoRequestDTO, @Min(1) @PathVariable Long clinicaId){
        Usuario usuario = (Usuario) autentication.getPrincipal();
        Avaliacao avaliacao = avaliacaoMapper.convertToEntity(avaliacaoRequestDTO);
        Avaliacao novaAvaliacao = usuarioComumService.criaAvaliacaoDoUsuarioAClinica(usuario, avaliacao, clinicaId);
        return new ResponseEntity<>(avaliacaoMapper.convertToDTO(novaAvaliacao), HttpStatus.CREATED);
    }

}