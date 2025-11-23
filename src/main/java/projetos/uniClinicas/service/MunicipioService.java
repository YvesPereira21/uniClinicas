package projetos.uniClinicas.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import projetos.uniClinicas.exception.*;
import projetos.uniClinicas.mapper.ClinicaMapper;
import projetos.uniClinicas.model.Clinica;
import projetos.uniClinicas.model.Municipio;
import projetos.uniClinicas.repository.MunicipioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MunicipioService {

    private final MunicipioRepository municipioRepository;

    public MunicipioService(MunicipioRepository municipioRepository) {
        this.municipioRepository = municipioRepository;
    }

    public Municipio adicionaMunicipio(Municipio municipio) {
        boolean municipioExiste = municipioRepository.existsByNomeMunicipio(municipio.getNomeMunicipio());
        if (municipioExiste) {
            throw new ObjetoJaAdicionadoException("Esse município já foi adicionado!");
        }
        return municipioRepository.save(municipio);
    }

    @Cacheable(value = "lista_clinicas", key = "#nomeMunicipio + '-' + #pageable.pageNumber")
    public Page<Clinica> mostraTodasClinicasDaCidade(String nomeMunicipio, Pageable pageable) {
        return municipioRepository.findaAllClinicasByNomeMunicipio(nomeMunicipio, pageable);
    }
}
