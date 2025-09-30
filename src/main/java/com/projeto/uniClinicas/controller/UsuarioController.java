package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.AvaliacaoRequestDTO;
import com.projeto.uniClinicas.dto.AvaliacaoResponseDTO;
import com.projeto.uniClinicas.dto.UsuarioRequestDTO;
import com.projeto.uniClinicas.dto.UsuarioResponseDTO;
import com.projeto.uniClinicas.mapper.AvaliacaoMapper;
import com.projeto.uniClinicas.mapper.UsuarioMapper;
import com.projeto.uniClinicas.model.Avaliacao;
import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.security.SecurityConfigurations;
import com.projeto.uniClinicas.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "usuario", description = "Controller de usuário")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final AvaliacaoMapper avaliacaoMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper, AvaliacaoMapper avaliacaoMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/usuarios")
    @Operation(summary = "Cadastra usuário", description = "Método para cadastrar dados do usuário")
    @ApiResponse(responseCode = "201", description = "Usuário salvado com sucesso!")
    @ApiResponse(responseCode = "400", description = "Erro ao tentar salvar o usuário.")
    public UsuarioResponseDTO cadastraUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuario = usuarioMapper.convertToEntity(usuarioRequestDTO);
        Usuario novoUsuario = usuarioService.adicionaUsuario(usuario);
        return usuarioMapper.convertToDTO(novoUsuario);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/usuarios/{usuarioId}")
    @Operation(summary = "Atualiza dados do usuário", description = "Método para atualizar dados do usuário")
    @ApiResponse(responseCode = "201", description = "Usuário atualizado com sucesso!")
    @ApiResponse(responseCode = "400", description = "Erro ao tentar atualizar o usuário.")
    public UsuarioResponseDTO atualizaUsuario(@Min(1) @PathVariable Long usuarioId, @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuario = usuarioMapper.convertToEntity(usuarioRequestDTO);
        Usuario novoUsuario = usuarioService.atualizaUsuario(usuarioId, usuario);
        return usuarioMapper.convertToDTO(novoUsuario);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/usuarios/{usuarioId}")
    public void deletaUsuario(@Min(1) @PathVariable Long usuarioId){
        usuarioService.deletaUsuario(usuarioId);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("avaliacao/{clinicaId}/clinica")
    @Operation(summary = "Usuário cria avaliação", description = "Método que permite um usuário criar uma avaliação")
    @ApiResponse(responseCode = "201", description = "Usuário criou avaliação com sucesso!")
    @ApiResponse(responseCode = "400", description = "Erro ao tentar criar avaliação.")
    public AvaliacaoResponseDTO criaAvaliacao(Authentication autentication, @Valid @RequestBody AvaliacaoRequestDTO avaliacaoRequestDTO, @Min(1) @PathVariable("clinicaId") Long clinicaId){
        Usuario usuarioAutenticado = (Usuario) autentication.getPrincipal();
        Avaliacao avaliacao = avaliacaoMapper.convertToEntity(avaliacaoRequestDTO);
        Avaliacao novaAvaliacao = usuarioService.criaAvaliacaoDoUsuarioAClinica(usuarioAutenticado, avaliacao, clinicaId);
        return avaliacaoMapper.convertToDTO(novaAvaliacao);
    }

}
