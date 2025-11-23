package projetos.uniClinicas.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import projetos.uniClinicas.enums.UserRole;
import projetos.uniClinicas.exception.*;
import projetos.uniClinicas.model.*;
import projetos.uniClinicas.repository.ClinicaRepository;
import projetos.uniClinicas.repository.MunicipioRepository;
import projetos.uniClinicas.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClinicaService {

    private final ClinicaRepository clinicaRepository;
    private final UsuarioRepository  usuarioRepository;
    private final MunicipioRepository municipioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ClinicaService(ClinicaRepository clinicaRepository, UsuarioRepository usuarioRepository, MunicipioRepository municipioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.clinicaRepository = clinicaRepository;
        this.usuarioRepository = usuarioRepository;
        this.municipioRepository = municipioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @CacheEvict(value = "lista_clinicas",  allEntries = true)
    public Clinica adicionaClinica(Clinica clinica) {
        Usuario usuario = clinica.getUsuario();
        Endereco endereco = clinica.getEndereco();
        String nomeMunicipio = endereco.getMunicipio().getNomeMunicipio();
        Municipio municipio = municipioRepository.findMunicipioByNomeMunicipio(nomeMunicipio)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Esse não é um município existente!"));
        boolean verifica = verificaExistencia(clinica);
        if (verifica) {
            throw new ObjetoJaAdicionadoException("Essa conta já foi cadastrada!");
        }
        endereco.setMunicipio(municipio);
        clinica.setEndereco(endereco);
        String encryptedPassword = bCryptPasswordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encryptedPassword);
        usuario.setRole(UserRole.CLINICA);
        clinica.setUsuario(usuario);

        return clinicaRepository.save(clinica);
    }

    @Caching(evict = {
            @CacheEvict(value = "clinica", key = "#clinicaId"),
            @CacheEvict(value = "lista_clinicas", allEntries = true),
            @CacheEvict(value = "nome_clinica", allEntries = true)
    })
    public Clinica atualizaClinica(Clinica clinicaAtualizar, Long clinicaId) {
        Clinica clinicaNova = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada"));

        clinicaNova.setNomeClinica(clinicaAtualizar.getNomeClinica());
        clinicaNova.setCpnjClinica(clinicaAtualizar.getCpnjClinica());
        clinicaNova.setEndereco(clinicaAtualizar.getEndereco());
        clinicaNova.setTelefone(clinicaAtualizar.getTelefone());
        clinicaNova.setHorarioFuncionamento(clinicaAtualizar.getHorarioFuncionamento());
        clinicaNova.setHorarioFechamento(clinicaAtualizar.getHorarioFechamento());

        return clinicaRepository.save(clinicaNova);
    }

    @Cacheable(value = "clinica", key = "#clinicaId")
    public Clinica pegaClinica(Long clinicaId) {
        return clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada!"));
    }

    @Caching(evict = {
            @CacheEvict(value = "clinica", key = "#clinicaId"),
            @CacheEvict(value = "lista_clinicas", allEntries = true),
            @CacheEvict(value = "nome_clinica", allEntries = true)
    })
    public void deletaClinica(Long clinicaId) {
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada!"));
        clinicaRepository.deleteById(clinicaId);
    }

    @Cacheable(value = "nome_clinica", key = "#nome + '-' + #pageable.pageNumber")
    public Page<Clinica> mostraClinicasComCertoNome(String nome, Pageable pageable) {
        return clinicaRepository.findByNomeClinicaContaining(nome, pageable);
    }

    @Cacheable(value = "medicos_clinica", key = "#clinicaId + '-' + #pageable.pageNumber")
    public Page<Medico> todosMedicosClinica(Long clinicaId, Pageable pageable) {
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada!"));
        return clinicaRepository.findMedicosByClinicaId(clinicaId, pageable);
    }

    private boolean verificaExistencia(Clinica clinica){
        Usuario usuario = clinica.getUsuario();
        boolean clinicaJaExistente = clinicaRepository.existsByCpnjClinica(clinica.getCpnjClinica());
        boolean telefoneExistente = clinicaRepository.existsByTelefone(clinica.getTelefone());
        return usuarioRepository.findByUsername(usuario.getUsername()).isPresent() || clinicaJaExistente || telefoneExistente;
    }
}