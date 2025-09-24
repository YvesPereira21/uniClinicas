package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.authentication.AutenticacaoDTO;
import com.projeto.uniClinicas.authentication.LoginResponseDTO;
import com.projeto.uniClinicas.authentication.RegistroDTO;
import com.projeto.uniClinicas.enums.UserRole;
import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.repository.UsuarioRepository;
import com.projeto.uniClinicas.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

    private AuthenticationManager authenticationManager;
    private UsuarioRepository repository;
    private TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, UsuarioRepository repository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegistroDTO data) {
        if (this.repository.findByUsername(data.getUsername()).isPresent()) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        Usuario newUser = new Usuario(data.getUsername(), encryptedPassword, UserRole.USER);
        newUser.setNomeusuario(data.getUsername());
        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
