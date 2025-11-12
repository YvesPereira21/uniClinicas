package projetos.uniClinicas.controller;

import projetos.uniClinicas.authentication.AutenticacaoDTO;
import projetos.uniClinicas.authentication.LoginResponseDTO;
import projetos.uniClinicas.dto.requests.UsuarioComumRequestDTO;
import projetos.uniClinicas.dto.responses.UsuarioComumResponseDTO;
import projetos.uniClinicas.mapper.ClinicaMapper;
import projetos.uniClinicas.mapper.UsuarioComumMapper;
import projetos.uniClinicas.model.Usuario;
import projetos.uniClinicas.model.UsuarioComum;
import projetos.uniClinicas.security.TokenService;
import projetos.uniClinicas.service.ClinicaService;
import projetos.uniClinicas.service.UsuarioComumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Tag(name = "Autenticação", description = "Controller para autenticação e registro de usuários")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioComumMapper usuarioComumMapper;
    private final ClinicaMapper clinicaMapper;
    private final UsuarioComumService usuarioComumService;
    private final ClinicaService clinicaService;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, UsuarioComumMapper usuarioComumMapper, ClinicaMapper clinicaMapper, UsuarioComumService usuarioComumService, ClinicaService clinicaService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.usuarioComumMapper = usuarioComumMapper;
        this.clinicaMapper = clinicaMapper;
        this.usuarioComumService = usuarioComumService;
        this.clinicaService = clinicaService;
        this.tokenService = tokenService;
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

    @PostMapping("/usuarios")
    @Operation(summary = "Salva dados do usuário", description = "Método para salvar dados do usuário")
    @ApiResponse(responseCode = "201", description = "Usuário salvado com sucesso!")
    @ApiResponse(responseCode = "400", description = "Erro ao tentar salvar o usuário.")
    public ResponseEntity<UsuarioComumResponseDTO> cadastrarUsuario(@Valid @RequestBody UsuarioComumRequestDTO usuarioComumRequestDTO){
        UsuarioComum usuario = usuarioComumMapper.convertToEntity(usuarioComumRequestDTO);
        UsuarioComum novoUsuario = usuarioComumService.adicionaUsuario(usuario);
        return ResponseEntity.ok(usuarioComumMapper.convertToDTO(novoUsuario));
    }

}