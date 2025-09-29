package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.model.Avaliacao;
import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.repository.AvaliacaoRepository;
import com.projeto.uniClinicas.repository.ClinicaRepository;
import com.projeto.uniClinicas.repository.UsuarioRepository;
import com.projeto.uniClinicas.exception.ObjetoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClinicaRepository clinicaRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, UsuarioRepository usuarioRepository, ClinicaRepository clinicaRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.clinicaRepository = clinicaRepository;
    }

    public Avaliacao adicionaAvaliacao(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public Avaliacao pegaAvaliacaoUnica(Long avaliacaoId) {
        return avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Essa avaliação não existe"));
    }

    public void deletaAvaliacao(Long avaliacaoId) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Essa avaliação não existe"));
        avaliacaoRepository.delete(avaliacao);
    }

    public List<Avaliacao> todasAvaliacoes(){
        return avaliacaoRepository.findAll();
    }

    public List<Avaliacao> avaliacoesUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Esse usuário não foi encontrado"));
        return avaliacaoRepository.findAllAvaliacaoByUsuarioId(usuario.getusuarioId());
    }

    public double calculaAvaliacaoMedia(Long clinicaId){
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAllAvaliacaoByClinicaId(clinicaId);
        double soma = avaliacoes.stream()
                .map(Avaliacao::getNota)
                .reduce(0.0, Double::sum);
        return soma /avaliacoes.size();
    }
}
