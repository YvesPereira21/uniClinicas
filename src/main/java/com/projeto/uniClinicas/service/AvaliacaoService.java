package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.exception.AvaliacoesInexistentesException;
import com.projeto.uniClinicas.model.Avaliacao;
import com.projeto.uniClinicas.model.Clinica;
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

    public void deletaAvaliacao(Long avaliacaoId) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Essa avaliação não existe"));
        avaliacaoRepository.delete(avaliacao);
    }

    public List<Avaliacao> avaliacoesUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Esse usuário não foi encontrado"));
        return avaliacaoRepository.findAllAvaliacaoByUsuarioId(usuario.getusuarioId());
    }

    public List<Avaliacao> avaliacoesClinica(Long clinicaId){
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada"));
        return avaliacaoRepository.findAllAvaliacaoByClinicaId(clinicaId);
    }

    public double calculaAvaliacaoMedia(Long clinicaId){
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAllAvaliacaoByClinicaId(clinicaId);
        if (avaliacoes.isEmpty()){
            throw new AvaliacoesInexistentesException("Não há avaliações para essa clínica");
        }
        double soma = avaliacoes.stream()
                .map(Avaliacao::getNota)
                .reduce(0.0, Double::sum);
        return soma /avaliacoes.size();
    }
}
