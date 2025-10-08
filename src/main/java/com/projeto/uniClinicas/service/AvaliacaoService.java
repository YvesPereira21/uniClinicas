package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.exception.*;
import com.projeto.uniClinicas.model.Avaliacao;
import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.UsuarioComum;
import com.projeto.uniClinicas.repository.AvaliacaoRepository;
import com.projeto.uniClinicas.repository.ClinicaRepository;
import com.projeto.uniClinicas.repository.UsuarioComumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final UsuarioComumRepository usuarioComumRepository;
    private final ClinicaRepository clinicaRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, UsuarioComumRepository usuarioComumRepository, ClinicaRepository clinicaRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.usuarioComumRepository = usuarioComumRepository;
        this.clinicaRepository = clinicaRepository;
    }

    public Avaliacao adicionaAvaliacao(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public Avaliacao pegaAvaliacaoUnica(Long avaliacaoId) {
        return avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Essa avaliação não existe!"));
    }

    public void deletaAvaliacao(Long avaliacaoId) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Essa avaliação não existe!"));
        avaliacaoRepository.delete(avaliacao);
    }

    public List<Avaliacao> todasAvaliacoes(){
        return avaliacaoRepository.findAll();
    }

    public List<Avaliacao> avaliacoesUsuario(Long usuarioId){
        UsuarioComum usuario = usuarioComumRepository.findById(usuarioId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Esse usuário não existe!"));
        return avaliacaoRepository.findAllAvaliacaoByUsuarioId(usuarioId);
    }

    public List<Avaliacao> avaliacoesClinica(Long clinicaId){
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada"));
        return avaliacaoRepository.findAllAvaliacaoByClinicaId(clinicaId);
    }

    public double calculaAvaliacaoMedia(Long clinicaId){
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAllAvaliacaoByClinicaId(clinicaId);
        double soma = avaliacoes.stream()
                .map(Avaliacao::getNota)
                .reduce(0.0, Double::sum);
        return soma/avaliacoes.size();
    }
}
