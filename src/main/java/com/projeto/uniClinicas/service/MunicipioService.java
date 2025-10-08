package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.exception.*;
import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Municipio;
import com.projeto.uniClinicas.repository.MunicipioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Clinica> mostraTodasClinicasDaCidade(String nomeMunicipio) {
        return municipioRepository.findaAllClinicasByNomeMunicipio(nomeMunicipio);
    }
}
