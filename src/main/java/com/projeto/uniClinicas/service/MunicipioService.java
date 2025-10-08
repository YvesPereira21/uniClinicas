package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.exception.*;
import com.projeto.uniClinicas.mapper.ClinicaMapper;
import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Municipio;
import com.projeto.uniClinicas.repository.MunicipioRepository;
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
