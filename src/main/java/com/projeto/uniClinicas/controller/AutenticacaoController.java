package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.authentication.AutenticacaoDTO;
import com.projeto.uniClinicas.authentication.LoginResponseDTO;
import com.projeto.uniClinicas.authentication.ChangePasswordDTO;
import com.projeto.uniClinicas.dto.UsuarioRequestDTO;
import com.projeto.uniClinicas.dto.UsuarioResponseDTO;
import com.projeto.uniClinicas.mapper.UsuarioMapper;
import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.service.UsuarioService;
import com.projeto.uniClinicas.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "autenticacao", description = "Controller para autenticação e registro de usuários")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping("/login")
    @Operation(summary = "Realiza o login do usuário", description = "Autentica um usuário e retorna um token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
            @ApiResponse(responseCode = "403", description = "Credenciais inválidas")
    })
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AutenticacaoDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Registra um novo usuário (USER)", description = "Cria uma nova conta de usuário com a role 'USER'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao tentar salvar o usuário.")
    })
    public ResponseEntity<UsuarioResponseDTO> cadastraUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuario = usuarioMapper.convertToEntity(usuarioRequestDTO);
        Usuario novoUsuario = usuarioService.adicionaUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.convertToDTO(novoUsuario));
    }

    @PostMapping("/change-password")
    @Operation(summary = "Registra um novo usuário (USER)", description = "Cria uma nova conta de usuário com a role 'USER'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao tentar salvar o usuário.")
    })
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        usuarioService.mudarSenha(changePasswordDTO.getUsername(), changePasswordDTO.getNewPassword());
        return ResponseEntity.ok("Senha alterada com sucesso");
    }
}