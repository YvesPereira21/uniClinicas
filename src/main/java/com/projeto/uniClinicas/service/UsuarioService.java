package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.enums.UserRole;
import com.projeto.uniClinicas.exception.ObjetoJaAdicionado;
import com.projeto.uniClinicas.repository.ClinicaRepository;
import com.projeto.uniClinicas.exception.CPFDuplicadoException;
import com.projeto.uniClinicas.model.Avaliacao;
import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Endereco;
import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.repository.AvaliacaoRepository;
import com.projeto.uniClinicas.repository.UsuarioRepository;
import com.projeto.uniClinicas.exception.ObjetoNaoEncontradoException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final ClinicaRepository clinicaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, AvaliacaoRepository avaliacaoRepository, ClinicaRepository clinicaRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.clinicaRepository = clinicaRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Usuario adicionaUsuario(Usuario novoUsuario){
        if(usuarioRepository.findByUsername(novoUsuario.getUsername()).isPresent()){
            throw new ObjetoJaAdicionado("Usuário já existente!");
        }
        boolean cpfUsuario = usuarioRepository.existsByCpf(novoUsuario.getCpf());
        if(cpfUsuario){
            throw new CPFDuplicadoException("Conta já cadastrada com esse CPF!");
        }
        novoUsuario.setPassword(bCryptPasswordEncoder.encode(novoUsuario.getPassword()));
        novoUsuario.setRole(UserRole.USER);
        return usuarioRepository.save(novoUsuario);
    }

    public void deletaUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Usuário ou senha inválidos!"));
        usuarioRepository.deleteById(usuarioId);
    }

    public Usuario atualizaUsuario(Long usuarioId, Usuario dadosAtualizados) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Usuário não encontrado!"));

        usuarioExistente.setNomeusuario(dadosAtualizados.getNomeusuario());
        usuarioExistente.setIdadeUsuario(dadosAtualizados.getIdadeUsuario());
        usuarioExistente.setSexo(dadosAtualizados.getSexo());
        usuarioExistente.setEmail(dadosAtualizados.getEmail());
        usuarioExistente.setEndereco(dadosAtualizados.getEndereco());

        return usuarioRepository.save(usuarioExistente);
    }

    public void mudarSenha(String username, String newPassword){
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Usuário ou senha inválidos!"));
        usuario.setPassword(bCryptPasswordEncoder.encode(newPassword));
    }

    public Avaliacao criaAvaliacaoDoUsuarioAClinica(Usuario usuario, Avaliacao avaliacao, Long clinicaId) {
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Não há uma clínica com esse id"));
        Avaliacao novaAvaliacao = new Avaliacao();
        novaAvaliacao.setComentario(avaliacao.getComentario());
        novaAvaliacao.setNota(avaliacao.getNota());
        novaAvaliacao.setClinica(clinica);
        novaAvaliacao.setUsuario(usuario);
        return avaliacaoRepository.save(novaAvaliacao);
    }
}

