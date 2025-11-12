package projetos.uniClinicas.service;

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
    private final ClinicaMapper clinicaMapper;

    public MunicipioService(MunicipioRepository municipioRepository, ClinicaMapper clinicaMapper) {
        this.municipioRepository = municipioRepository;
        this.clinicaMapper = clinicaMapper;
    }

    public Municipio adicionaMunicipio(Municipio municipio) {
        boolean municipioExiste = municipioRepository.existsByNomeMunicipio(municipio.getNomeMunicipio());
        if (municipioExiste) {
            throw new ObjetoJaAdicionadoException("Esse município já foi adicionado!");
        }
        return municipioRepository.save(municipio);
    }

    public Page<Clinica> mostraTodasClinicasDaCidade(String nomeMunicipio, Pageable pageable) {
        return municipioRepository.findaAllClinicasByNomeMunicipio(nomeMunicipio, pageable);
    }
}
