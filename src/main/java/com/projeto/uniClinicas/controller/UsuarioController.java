package com.projeto.uniClinicas.controller;

import com.projeto.uniClinicas.dto.AvaliacaoRequestDTO;
import com.projeto.uniClinicas.dto.AvaliacaoResponseDTO;
import com.projeto.uniClinicas.dto.UsuarioDTO;
import com.projeto.uniClinicas.mapper.AvaliacaoMapper;
import com.projeto.uniClinicas.mapper.UsuarioMapper;
import com.projeto.uniClinicas.model.Avaliacao;
import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.service.UsuarioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final AvaliacaoMapper avaliacaoMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper, AvaliacaoMapper avaliacaoMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    @PostMapping("/usuarios")
    public UsuarioDTO salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioMapper.convertToEntity(usuarioDTO);
        Usuario novoUsuario = usuarioService.adicionaUsuario(usuario);
        return usuarioMapper.convertToDTO(novoUsuario);
    }

    @PutMapping("/usuarios")
    public UsuarioDTO atualizaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioMapper.convertToEntity(usuarioDTO);
        Usuario novoUsuario = usuarioService.atualizaUsuario(usuario);
        return usuarioMapper.convertToDTO(novoUsuario);
    }

    @DeleteMapping("/usuarios")
    public void deletaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario =  usuarioMapper.convertToEntity(usuarioDTO);
        usuarioService.deletaUsuario(usuario);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("avaliacao/{clinicaId}/clinica")
    public AvaliacaoResponseDTO avaliacao(Authentication autentication, @RequestBody AvaliacaoRequestDTO avaliacaoRequestDTO, @PathVariable("clinicaId") Long clinicaId){
        Usuario usuarioAutenticado = (Usuario) autentication.getPrincipal();
        Avaliacao avaliacao = avaliacaoMapper.convertToEntity(avaliacaoRequestDTO);
        Avaliacao novaAvaliacao = usuarioService.criaAvaliacaoDoUsuarioAClinica(usuarioAutenticado, avaliacao, clinicaId);
        return avaliacaoMapper.convertToDTO(novaAvaliacao);
    }

}
