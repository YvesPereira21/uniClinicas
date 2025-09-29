package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Municipio;
import com.projeto.uniClinicas.repository.MunicipioRepository;
import com.projeto.uniClinicas.validation.MunicipioJaAdicionadoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipioService {

    private final MunicipioRepository municipioRepository;

    public MunicipioService(MunicipioRepository municipioRepository) {
        this.municipioRepository = municipioRepository;
    }

    public Municipio adicionaMunicipio(Municipio municipio) {
        boolean municipioExistente = municipioRepository.existsMunicipioByNomeMunicipio(municipio.getNomeMunicipio());
        if(municipioExistente){
            throw new MunicipioJaAdicionadoException("Município já foi adicionado");
        }
        return municipioRepository.save(municipio);
    }

    public List<Clinica> clinicasCidade(String nomeMunicipio) {
        return municipioRepository.todasClinicasCidade(nomeMunicipio);
    }
}
