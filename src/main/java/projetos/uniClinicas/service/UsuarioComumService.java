package projetos.uniClinicas.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import projetos.uniClinicas.enums.UserRole;
import projetos.uniClinicas.exception.*;
import projetos.uniClinicas.model.*;
import projetos.uniClinicas.repository.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioComumService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsuarioComumRepository usuarioComumRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final ClinicaRepository clinicaRepository;
    private final MunicipioRepository municipioRepository;
    private final UsuarioRepository usuarioRepository;

    public UsuarioComumService(BCryptPasswordEncoder bCryptPasswordEncoder, UsuarioComumRepository usuarioComumRepository, AvaliacaoRepository avaliacaoRepository, ClinicaRepository clinicaRepository, MunicipioRepository municipioRepository, UsuarioRepository usuarioRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usuarioComumRepository = usuarioComumRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.clinicaRepository = clinicaRepository;
        this.municipioRepository = municipioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioComum adicionaUsuario(UsuarioComum novoUsuario){
        boolean usuarioExistente = verificaUsuarioExistente(novoUsuario);
        if(usuarioExistente){
            throw new CPFDuplicadoException("Usuário já existente!");
        }
        Endereco endereco = novoUsuario.getEndereco();
        String nomeMunicipio = endereco.getMunicipio().getNomeMunicipio();
        Municipio municipio = municipioRepository.findMunicipioByNomeMunicipio(nomeMunicipio)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Essa cidade não existe!"));
        Usuario usuario = novoUsuario.getUsuario();
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuario.setRole(UserRole.USER);
        endereco.setMunicipio(municipio);
        novoUsuario.setEndereco(endereco);
        novoUsuario.setUsuario(usuario);
        return usuarioComumRepository.save(novoUsuario);
    }

    @CacheEvict(value = "usuarios", key = "#usuarioId")
    public void deletaUsuario(Long usuarioId){
        UsuarioComum usuarioComum = usuarioComumRepository.findById(usuarioId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Usuário não encontrado"));
        usuarioComumRepository.deleteById(usuarioId);
    }

    @CachePut(value = "usuarios", key = "#novoUsuario.usuarioComumId")
    public UsuarioComum atualizaUsuario(UsuarioComum novoUsuario){
        return usuarioComumRepository.save(novoUsuario);
    }

    public UsuarioComum atualizaEndereco(Long usuarioId, Endereco endereco){
        UsuarioComum usuario = usuarioComumRepository.findById(usuarioId).
                orElseThrow(() -> new ObjetoNaoEncontradoException("Usuário não encontrado!"));
        usuario.setEndereco(endereco);
        return usuarioComumRepository.save(usuario);
    }

    @Caching(evict = {
            @CacheEvict(value = "avaliacoes_clinica", allEntries = true),
            @CacheEvict(value = "media_clinica", key = "#clinicaId")
    })
    public Avaliacao criaAvaliacaoDoUsuarioAClinica(Usuario user, Avaliacao avaliacao, Long clinicaId) {
        UsuarioComum usuario = usuarioComumRepository.findByUsuario(user);
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Não há uma clínica com esse id"));
        boolean usuarioJaPossuiAvaliacao = possuiAvaliacao(user, clinicaId);
        if(usuarioJaPossuiAvaliacao){
            throw new AvaliacaoExistenteException("Você só tem o limite de 1 avaliação por clínica!");
        }
        Avaliacao novaAvaliacao = new Avaliacao();
        novaAvaliacao.setComentario(avaliacao.getComentario());
        novaAvaliacao.setNota(avaliacao.getNota());
        novaAvaliacao.setClinica(clinica);
        novaAvaliacao.setUsuarioComum(usuario);
        return avaliacaoRepository.save(novaAvaliacao);
    }

    private boolean verificaUsuarioExistente(UsuarioComum usuarioExistente){
        Usuario usuario = usuarioExistente.getUsuario();
        boolean cpfUsuario = usuarioComumRepository.existsByCpf(usuarioExistente.getCpf());
        return cpfUsuario || usuarioRepository.findByUsername(usuario.getUsername()).isPresent();
    }

    private boolean possuiAvaliacao(Usuario usuario, Long clinicaId){
        UsuarioComum usuarioComum = usuarioComumRepository.findByUsuario(usuario);
        return avaliacaoRepository.existsByUsuarioComumAndClinica_ClinicaId(usuarioComum, clinicaId);
    }
}