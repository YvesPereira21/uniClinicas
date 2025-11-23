package projetos.uniClinicas.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import projetos.uniClinicas.exception.*;
import projetos.uniClinicas.model.Avaliacao;
import projetos.uniClinicas.model.Clinica;
import projetos.uniClinicas.model.UsuarioComum;
import projetos.uniClinicas.repository.AvaliacaoRepository;
import projetos.uniClinicas.repository.ClinicaRepository;
import projetos.uniClinicas.repository.UsuarioComumRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Cacheable(value = "avaliacao", key = "#avaliacaoId")
    public Avaliacao pegaAvaliacaoUnica(Long avaliacaoId) {
        return avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Essa avaliação não existe!"));
    }

    @Caching(evict = {
            @CacheEvict(value = "avaliacao", key = "#avaliacaoId"),
            @CacheEvict(value = "avaliacoes_usuario", allEntries = true),
            @CacheEvict(value = "todas_avaliacoes", allEntries = true),
            @CacheEvict(value = "avaliacoes_clinica", allEntries = true),
            @CacheEvict(value = "media_clinica", allEntries = true)
    })
    public void deletaAvaliacao(Long avaliacaoId) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Essa avaliação não existe!"));
        avaliacaoRepository.delete(avaliacao);
    }

    @Cacheable(value = "todas_avaliacoes", key = "#pageable.pageNumber")
    public Page<Avaliacao> todasAvaliacoes(Pageable pageable){
        return avaliacaoRepository.findAll(pageable);
    }

    @Cacheable(value = "avaliacoes_usuario", key = "#usuarioId + '-' + #pageable.pageNumber")
    public Page<Avaliacao> avaliacoesUsuario(Long usuarioId, Pageable pageable){
        UsuarioComum usuario = usuarioComumRepository.findById(usuarioId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Esse usuário não existe!"));
        return avaliacaoRepository.findAllAvaliacaoByUsuarioId(usuarioId, pageable);
    }

    @Cacheable(value = "avaliacoes_clinica", key = "#clinicaId + '-' + #pageable.pageNumber")
    public Page<Avaliacao> avaliacoesClinica(Long clinicaId, Pageable pageable){
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada"));
        return avaliacaoRepository.findAllAvaliacaoByClinicaId(clinicaId, pageable);
    }

    @Cacheable(value = "media_clinica", key = "#clinicaId")
    public double calculaAvaliacaoMedia(Long clinicaId){
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAllAvaliacaoByClinicaId(clinicaId);
        double soma = avaliacoes.stream()
                .map(Avaliacao::getNota)
                .reduce(0.0, Double::sum);
        return soma/avaliacoes.size();
    }
}
