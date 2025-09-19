package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.repository.ClinicaRepository;
import com.projeto.uniClinicas.validation.CPFDuplicadoException;
import com.projeto.uniClinicas.model.Avaliacao;
import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Endereco;
import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.repository.AvaliacaoRepository;
import com.projeto.uniClinicas.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private UsuarioRepository usuarioRepository;
    private AvaliacaoRepository avaliacaoRepository;
    private ClinicaRepository clinicaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, AvaliacaoRepository avaliacaoRepository, ClinicaRepository clinicaRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.clinicaRepository = clinicaRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Usuario adicionaUsuario(Usuario novoUsuario){
        boolean cpfUsuario = usuarioRepository.existsByCpf(novoUsuario.getCpf());
        if(cpfUsuario){
            throw new CPFDuplicadoException("Por favor, informe o seu CPF real!");
        }
        novoUsuario.setPassword(bCryptPasswordEncoder.encode(novoUsuario.getPassword()));
        return usuarioRepository.save(novoUsuario);
    }

    public void deletaUsuario(Usuario novoUsuario){
        usuarioRepository.delete(novoUsuario);
    }

    public Usuario atualizaUsuario(Usuario novoUsuario){
        return usuarioRepository.save(novoUsuario);
    }

    public Usuario atualizaEndereco(Long usuarioId, Endereco endereco){
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        usuario.setEndereco(endereco);
        return usuarioRepository.save(usuario);
    }

    public Avaliacao criaAvaliacaoDoUsuarioAClinica(String username, Avaliacao avaliacao, Long clinicaId) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RuntimeException("Não há uma clínica com esse id"));
        Avaliacao novaAvaliacao = new Avaliacao();
        novaAvaliacao.setComentario(avaliacao.getComentario());
        novaAvaliacao.setNota(avaliacao.getNota());
        novaAvaliacao.setClinica(clinica);
        novaAvaliacao.setUsuario(usuario);
        return avaliacaoRepository.save(novaAvaliacao);
    }
}

